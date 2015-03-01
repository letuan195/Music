package com.example.tuanlvk57.music.PlayMusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tuanlvk57.music.R;

import java.util.List;

/**
 * Created by tuanlv.k57 on 27/01/2015.
 */
public class ListSongsAdapter extends ArrayAdapter<Songs>{
    private Context mContext;
    private int mLayout;
    private List<Songs> mList;
    public ListSongsAdapter(Context context, int layout, List<Songs> list){
        super(context, layout, list);
        mContext = context;
        mLayout = layout;
        mList = list;

    }

    public ListSongsAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if(view == null){
            view  = inflater.inflate(mLayout,null);
        }

        Songs item = mList.get(position);

        TextView tvNameSong = (TextView) view.findViewById(R.id.tvNameSong);
        TextView tvArtist = (TextView) view.findViewById(R.id.tvNameArtist);

        tvNameSong.setText(item.getTitle());
        tvArtist.setText(item.getArtist());

        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Songs getItem(int position) {
        return mList.get(position);
    }
}
