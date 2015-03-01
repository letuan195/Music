package com.example.tuanlvk57.music.MyMusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanlvk57.music.R;

import java.util.ArrayList;

/**
 * Created by tuanlv.k57 on 31/01/2015.
 */
public class AdapterOfflineOnline extends ArrayAdapter<ItemOfflineOnline> {

    private Context mContext;
    private int mLayout;
    private ArrayList<ItemOfflineOnline> mArrayList;

    public AdapterOfflineOnline(Context context, int layout, ArrayList<ItemOfflineOnline> list) {
        super(context, layout,list);
        mContext = context;
        mLayout = layout;
        mArrayList = list;
    }

    @Override
    public int getPosition(ItemOfflineOnline item) {
        return super.getPosition(item);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public ItemOfflineOnline getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mLayout, parent, false);

        ItemOfflineOnline item = mArrayList.get(position);

        ImageView iv = (ImageView) view.findViewById(R.id.imageViewIcon);
        TextView tvType = (TextView) view.findViewById(R.id.textViewName);
        TextView tvNumber = (TextView) view.findViewById(R.id.textviewNumber);
        tvNumber.setVisibility(View.VISIBLE);

        iv.setImageResource(item.getImage());
        tvType.setText(item.getType());

        return view;
    }
}
