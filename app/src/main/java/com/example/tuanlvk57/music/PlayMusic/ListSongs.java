package com.example.tuanlvk57.music.PlayMusic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuanlvk57.music.R;
import com.mobeta.android.dslv.DragSortListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuanlv.k57 on 25/01/2015.
 */
public class ListSongs extends Fragment {

    public static final String TAG = "List Songs Play Music";

    private ListSongsAdapter mAdapter;

    private DragSortListView.DropListener dropListener = new DragSortListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            Songs item = mAdapter.getItem(from);

            mAdapter.notifyDataSetChanged();
            mAdapter.remove(item);
            mAdapter.insert(item, to);
        }
    };
    private DragSortListView.RemoveListener mRemoveListener =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    mAdapter.remove(mAdapter.getItem(which));
                }
            };
    private DragSortListView.DragScrollProfile mDragScrollProfile =
            new DragSortListView.DragScrollProfile() {
                @Override
                public float getSpeed(float w, long t) {
                    if (w > 0.8f) {
                        // Traverse all views in a millisecond
                        return ((float) mAdapter.getCount()) / 0.001f;
                    } else {
                        return 10.0f * w;
                    }
                }
            };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_songs_playmusic,container,false);

        DragSortListView dragSortListView = (DragSortListView)view.findViewById(R.id.dragsortListView);
        dragSortListView.setDropListener(dropListener);
        dragSortListView.setRemoveListener(mRemoveListener);
        dragSortListView.setDragScrollProfile(mDragScrollProfile);

        List<Songs> mArrayList = new ArrayList<>();
        Songs item1 = new Songs("android","1");
        Songs item2 = new Songs("android","2");
        Songs item3 = new Songs("android","3");
        Songs item4 = new Songs("android","4");
        Songs item5 = new Songs("android","5");
        mArrayList.add(item1);
        mArrayList.add(item2);
        mArrayList.add(item3);
        mArrayList.add(item4);
        mArrayList.add(item5);

        mAdapter = new ListSongsAdapter(getActivity(),R.layout.item_songs_dragsort,mArrayList);
        dragSortListView.setAdapter(mAdapter);

        return view;
    }

}
