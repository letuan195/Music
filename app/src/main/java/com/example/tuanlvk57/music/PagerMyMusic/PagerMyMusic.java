package com.example.tuanlvk57.music.PagerMyMusic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuanlvk57.music.MyMusic.PagerSlidingTabStrip;
import com.example.tuanlvk57.music.R;

/**
 * Created by tuanlv.k57 on 01/02/2015.
 */
public class PagerMyMusic extends Fragment implements ViewPager.OnPageChangeListener {

    public static final String TAG = "Pager My Music";

    private ViewPager mPager;
    private PagerSlidingTabStrip mTabs;
    private PagerAdapter mPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_mymusic,container,false);

        mPager = (ViewPager) view.findViewById(R.id.pagerMyMusic);
        mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabsPagerMyMusic);
        mPagerAdapter = new PagerAdapter(getActivity().getFragmentManager());

        mPager.setAdapter(mPagerAdapter);
        mTabs.setViewPager(mPager);
        mTabs.setOnPageChangeListener(this);

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class PagerAdapter extends FragmentStatePagerAdapter{

        private String [] mTitle = {getString(R.string.playlists),getString(R.string.albums),getString(R.string.songs),
                getString(R.string.artists)};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new Playlists();
                    break;
                case 1:
                    fragment = new Albums();
                    break;
                case 2:
                    fragment = new AllSongs();
                    break;
                case 3:
                    fragment = new Artists();
                    break;
                default:
                    fragment = new AllSongs();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }
}
