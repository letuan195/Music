package com.example.tuanlvk57.music.MyMusic;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tuanlvk57.music.Navigation.NavigationDrawerFragment;
import com.example.tuanlvk57.music.R;

import java.util.ArrayList;

public class OfflineFragment extends ScrollTabHolderFragment implements OnScrollListener {

	private static final String ARG_POSITION = "position";
    private static final String TAG = "Offline Fragment";

	private ListView mListView;
	private ArrayList<ItemOfflineOnline> mListItems;

	private int mPosition;

	public static Fragment newInstance(int position) {
		OfflineFragment f = new OfflineFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = getArguments().getInt(ARG_POSITION);

		mListItems = new ArrayList<ItemOfflineOnline>();

        ItemOfflineOnline item1 = new ItemOfflineOnline(R.drawable.ic_mm_song, getString(R.string.songs));
        ItemOfflineOnline item2 = new ItemOfflineOnline(R.drawable.ic_video, getString(R.string.video));
        ItemOfflineOnline item3 = new ItemOfflineOnline(R.drawable.ic_album, getString(R.string.albums));
        ItemOfflineOnline item4 = new ItemOfflineOnline(R.drawable.ic_playlist, getString(R.string.playlists));
        ItemOfflineOnline item5 = new ItemOfflineOnline(R.drawable.ic_artist, getString(R.string.artists));
        ItemOfflineOnline item6 = new ItemOfflineOnline(R.drawable.ic_artist, getString(R.string.artists));
        mListItems.add(item1);
        mListItems.add(item2);
        mListItems.add(item3);
        mListItems.add(item4);
        mListItems.add(item5);
        mListItems.add(item6);

        mCallbacks = (NavigationDrawerFragment.NavigationDrawerCallbacks) getActivity();
	}

    private NavigationDrawerFragment.NavigationDrawerCallbacks mCallbacks;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_music, null);

		mListView = (ListView) v.findViewById(R.id.listView);

		View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
		mListView.addHeaderView(placeHolderView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallbacks.onNavigationDrawerItemSelected(5 + position);
                Log.e(TAG, ""+position);
            }
        });

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListView.setOnScrollListener(this);
		mListView.setAdapter(new AdapterOfflineOnline(getActivity(), R.layout.listview_item_row, mListItems));
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
			return;
		}

		mListView.setSelectionFromTop(1, scrollHeight);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mScrollTabHolder != null)
			mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// nothing
	}

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}