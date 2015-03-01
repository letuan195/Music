package com.example.tuanlvk57.music;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tuanlvk57.music.MusicUtils.Utils;
import com.example.tuanlvk57.music.MyMusic.MyMusic;
import com.example.tuanlvk57.music.Navigation.NavigationDrawerFragment;
import com.example.tuanlvk57.music.PagerMyMusic.PagerMyMusic;
import com.example.tuanlvk57.music.PlayMusic.ListSongs;
import com.example.tuanlvk57.music.PlayMusic.ViewLyrics;
import com.example.tuanlvk57.music.PlayMusic.ViewSong;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Stack;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
                                                    , Utils.Defs{

    public static final String TAG = "MainActivity";
    public static final int NUM_PAGER = 3;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    // used to store app title
    private CharSequence mTitle;

    private SlidingUpPanelLayout mSlidingUpPanel;
    private RelativeLayout relativeLayoutMiniPlayMusic;

    private Utils.ServiceToken mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for proper titles
        mTitle = getTitle();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            selectItem(0);
        }

        mSlidingUpPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mSlidingUpPanel.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "1");
                relativeLayoutMiniPlayMusic.setVisibility(View.GONE);
                getActionBar().show();
            }

            @Override
            public void onPanelCollapsed(View panel) {
                relativeLayoutMiniPlayMusic.setVisibility(View.VISIBLE);
                mNavigationDrawerFragment.setGone(DrawerLayout.LOCK_MODE_UNLOCKED);
                getActionBar().hide();
                Log.i(TAG, "2");
            }

            @Override
            public void onPanelExpanded(View panel) {
                mNavigationDrawerFragment.setGone(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                Log.i(TAG, "3");
            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "4");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "5");
            }
        });

        setMiniPlayMusic();
        setLayoutPlayMusic();
        getActionBar().hide();

        String shuf = getIntent().getStringExtra("autoshuffle");
        if ("true".equals(shuf)) {
            mToken = Utils.bindToService(this, autoShuffle);
        }
    }

    private void setMiniPlayMusic(){
        Utils.updateNowPlaying(this);
    }

    private void setLayoutPlayMusic(){
        ImageButton mPrev = (ImageButton) findViewById(R.id.btn_prev);
        ImageButton mPlay = (ImageButton) findViewById(R.id.btn_play);
        ImageButton mNext = (ImageButton) findViewById(R.id.btn_next);
        ImageButton mShuffle = (ImageButton) findViewById(R.id.btn_shuffle);
        ImageButton mRepeat = (ImageButton) findViewById(R.id.btn_repeat);


        ViewPager mPager = (ViewPager) findViewById(R.id.pagerPlayMusic);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ListSongs();
                case 1:
                    return new ViewSong();
                case 2:
                    return new ViewLyrics();
                default:
                    break;
            }
            return new ViewSong();
        }

        @Override
        public int getCount() {
            return NUM_PAGER;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, selectItem(position))
                .commit();
    }

    private Fragment selectItem(int position) {

        // update the main content by replacing fragments

        Fragment fragment = null;

        switch (position) {
            case 0:
                mTitle = getString(R.string.mymusic);
                fragment = new MyMusic();
                break;
            case 1:
                mTitle = getString(R.string.albums);
                fragment = new MyMusic();
                break;
            case 2:
                mTitle = getString(R.string.playlists);
                fragment = new MyMusic();
                break;
            case 3:
                mTitle = getString(R.string.artists);
                fragment = new MyMusic();
                break;
            case 4:
                mTitle = getString(R.string.settings);
                fragment = new MyMusic();
                break;
            case 5:
                mTitle = getString(R.string.exit);
                fragment = new MyMusic();
                break;
            case 6:
                mTitle = getString(R.string.exit);
                fragment = new PagerMyMusic();
                break;
            default:
                mTitle = getString(R.string.mymusic);
                fragment = new MyMusic();
                break;
        }
        return fragment;
    }

    private ServiceConnection autoShuffle = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // we need to be able to bind again, so unbind
            try {
                unbindService(this);
            } catch (IllegalArgumentException e) {
            }
            IMusicService serv = IMusicService.Stub.asInterface(service);
            if (serv != null) {
                try {
                    serv.setShuffleMode(MusicService.SHUFFLE_AUTO);
                } catch (RemoteException ex) {
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {

        if (mToken != null) {
            Utils.unbindFromService(mToken);
            Stack s = new Stack();
            s.add("1");
        }

        super.onDestroy();
    }
}
