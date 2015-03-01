package com.example.tuanlvk57.music.PlayMusic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuanlvk57.music.R;

/**
 * Created by tuanlv.k57 on 25/01/2015.
 */
public class ViewSong extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_song_playmusic,container,false);
        return view;
    }
}
