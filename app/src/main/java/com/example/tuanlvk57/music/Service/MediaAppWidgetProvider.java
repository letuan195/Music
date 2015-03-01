/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tuanlvk57.music.Service;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.view.View;
import android.widget.RemoteViews;

import com.example.tuanlvk57.music.MainActivity;
import com.example.tuanlvk57.music.R;
import com.example.tuanlvk57.music.MusicService;

/**
 * Simple widget to show currently playing album art along
 * with play/pause and next track buttons.  
 */
public class MediaAppWidgetProvider extends AppWidgetProvider {
    static final String TAG = "MusicAppWidgetProvider";
    
    public static final String CMDAPPWIDGETUPDATE = "appwidgetupdate";

    private static MediaAppWidgetProvider sInstance;
    
    public static synchronized MediaAppWidgetProvider getInstance() {
        if (sInstance == null) {
            sInstance = new MediaAppWidgetProvider();
        }
        return sInstance;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        defaultAppWidget(context, appWidgetIds);
        
        // Send broadcast intent to any running MusicService so it can
        // wrap around with an immediate update.
        Intent updateIntent = new Intent(MusicService.SERVICECMD);
        updateIntent.putExtra(MusicService.CMDNAME,
                MediaAppWidgetProvider.CMDAPPWIDGETUPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        updateIntent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
        context.sendBroadcast(updateIntent);
    }
    
    /**
     * Initialize given widgets to default state, where we launch Music on default click
     * and hide actions if service not running.
     */
    private void defaultAppWidget(Context context, int[] appWidgetIds) {
        final Resources res = context.getResources();
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_default);
        
        views.setViewVisibility(R.id.tvArtistWidget, View.GONE);
        views.setTextViewText(R.id.tvTitleWidget, res.getText(R.string.widget_initial_text));

        linkButtons(context, views, false /* not playing */);
        pushUpdate(context, appWidgetIds, views);
    }
    
    private void pushUpdate(Context context, int[] appWidgetIds, RemoteViews views) {
        // Update specific list of appWidgetIds if given, otherwise default to all
        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        if (appWidgetIds != null) {
            gm.updateAppWidget(appWidgetIds, views);
        } else {
            gm.updateAppWidget(new ComponentName(context, this.getClass()), views);
        }
    }
    
    /**
     * Check against {@link android.appwidget.AppWidgetManager} if there are any instances of this widget.
     */
    private boolean hasInstances(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, this.getClass()));
        return (appWidgetIds.length > 0);
    }

    /**
     * Handle a change notification coming over from {@link MusicService}
     */
    public void notifyChange(MusicService service, String what) {
        if (hasInstances(service)) {
            if (MusicService.META_CHANGED.equals(what) ||
                    MusicService.PLAYSTATE_CHANGED.equals(what)) {
                performUpdate(service, null);
            }
        }
    }

    /**
     * Update all active widget instances by pushing changes
     */
    public void performUpdate(MusicService service, int[] appWidgetIds) {
        final Resources res = service.getResources();
        final RemoteViews views = new RemoteViews(service.getPackageName(), R.layout.widget_default);

        CharSequence titleName = service.getTrackName();
        CharSequence artistName = service.getArtistName();
        CharSequence errorState = null;

        // Format title string with track number, or show SD card message
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_SHARED) ||
                status.equals(Environment.MEDIA_UNMOUNTED)) {
            if (android.os.Environment.isExternalStorageRemovable()) {
                errorState = res.getText(R.string.sdcard_busy_title);
            } else {
                errorState = res.getText(R.string.sdcard_busy_title_nosdcard);
            }
        } else if (status.equals(Environment.MEDIA_REMOVED)) {
            if (android.os.Environment.isExternalStorageRemovable()) {
                errorState = res.getText(R.string.sdcard_missing_title);
            } else {
                errorState = res.getText(R.string.sdcard_missing_title_nosdcard);
            }
        } else if (titleName == null) {
            errorState = res.getText(R.string.empty_playlist);
        }
        
        if (errorState != null) {
            // Show error state to user
            views.setViewVisibility(R.id.tvArtistWidget, View.GONE);
            views.setTextViewText(R.id.tvTitleWidget, errorState);
            
        } else {
            // No error, so show normal titles
            views.setViewVisibility(R.id.tvArtistWidget, View.VISIBLE);
            views.setTextViewText(R.id.tvTitleWidget, titleName);
            views.setTextViewText(R.id.tvArtistWidget, artistName);
        }
        
        // Set correct drawable for pause state
        final boolean playing = service.isPlaying();
        if (playing) {
            views.setImageViewResource(R.id.ibPlayWidget, R.drawable.widget_pause);
        } else {
            views.setImageViewResource(R.id.ibPlayWidget, R.drawable.widget_play);
        }

        final int shuffle = service.getShuffleMode();
        switch (shuffle){
            case MusicService.SHUFFLE_NONE:
                views.setImageViewResource(R.id.ibShuffleWidget, R.drawable.ic_player_shuffle_off);
                break;
            case MusicService.SHUFFLE_NORMAL:
                views.setImageViewResource(R.id.ibShuffleWidget, R.drawable.ic_player_shuffle);
                break;
            case MusicService.SHUFFLE_AUTO:
                views.setImageViewResource(R.id.ibShuffleWidget, R.drawable.ic_player_shuffle);
                break;
            default:
                views.setImageViewResource(R.id.ibShuffleWidget, R.drawable.ic_player_shuffle_off);
                break;
        }

        final int repeat = service.getRepeatMode();
        switch (repeat){
            case MusicService.REPEAT_NONE:
                views.setImageViewResource(R.id.ibRepeatWidget, R.drawable.ic_player_repeat_off);
                break;
            case MusicService.REPEAT_CURRENT:
                views.setImageViewResource(R.id.ibRepeatWidget, R.drawable.ic_player_repeat_one);
                break;
            case MusicService.REPEAT_ALL:
                views.setImageViewResource(R.id.ibRepeatWidget, R.drawable.ic_player_repeat_all);
                break;
            default:
                views.setImageViewResource(R.id.ibRepeatWidget, R.drawable.ic_player_repeat_off);
                break;
        }

        // Link actions buttons to intents
        linkButtons(service, views, playing);
        
        pushUpdate(service, appWidgetIds, views);
    }

    /**
     * Link up various button actions using {@link android.app.PendingIntent}.
     * 
     * @param playerActive True if player is active in background, which means
     *            widget click will launch {@link com.example.tuanlvk57.music.MainActivity},
     *            otherwise we launch.
     */
    private void linkButtons(Context context, RemoteViews views, boolean playerActive) {
        // Connect up various buttons and touch events
        Intent intent;

        PendingIntent pendingIntent;
        
        final ComponentName serviceName = new ComponentName(context, MusicService.class);
        
        if (playerActive) {
            intent = new Intent(context, MainActivity.class);
            pendingIntent = PendingIntent.getActivity(context,
                    0 /* no requestCode */, intent, 0 /* no flags */);
            views.setOnClickPendingIntent(R.id.relativeWidget, pendingIntent);
        } else {
            intent = new Intent(context, MainActivity.class);
            pendingIntent = PendingIntent.getActivity(context,
                    0 /* no requestCode */, intent, 0 /* no flags */);
            views.setOnClickPendingIntent(R.id.relativeWidget, pendingIntent);
        }
        
        intent = new Intent(MusicService.TOGGLEPAUSE_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.ibPlayWidget, pendingIntent);
        
        intent = new Intent(MusicService.NEXT_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.ibNextWidget, pendingIntent);

        intent = new Intent(MusicService.PREVIOUS_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.ibPrevWidget, pendingIntent);

        intent = new Intent(MusicService.SHUFFLE_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.ibShuffleWidget, pendingIntent);

        intent = new Intent(MusicService.REPEAT_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flags */);
        views.setOnClickPendingIntent(R.id.ibRepeatWidget, pendingIntent);
    }
}
