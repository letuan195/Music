package com.example.tuanlvk57.music.PagerMyMusic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tuanlvk57.music.PlayMusic.ListSongsAdapter;
import com.example.tuanlvk57.music.PlayMusic.Songs;
import com.example.tuanlvk57.music.R;

import java.util.ArrayList;

/**
 * Created by tuanlv.k57 on 01/02/2015.
 */
public class Playlists extends Fragment {


    private ListView mListView;

    private ArrayList<Songs> mArrayList;
    private ListSongsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Songs item1 = new Songs("android", "android");

        mArrayList = new ArrayList<>();
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);
        mArrayList.add(item1);

        mAdapter = new ListSongsAdapter(getActivity(), R.layout.item_songs_dragsort, mArrayList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.list_music, container, false);

        mListView = (ListView) view.findViewById(R.id.listView);

        mListView.setAdapter(mAdapter);

        return view;
    }
}
