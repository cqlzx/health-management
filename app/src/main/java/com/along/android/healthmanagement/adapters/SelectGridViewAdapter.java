package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.List;
import com.squareup.picasso.Picasso;

import com.along.android.healthmanagement.R;

/**
 * Created by wilber on 4/26/17.
 */

public class SelectGridViewAdapter  extends BaseAdapter {
    private Context context;
    private List<String> mDatas;

    public SelectGridViewAdapter(Context context, List<String> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    //If image is selected, there will be a check mark on it
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.gridview_item_select, null);
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.id_item_image);
        }

        final String path = mDatas.get(position);

        Picasso.with(context).load(new File(path)).centerCrop().resize(150, 150).into(holder.imageView);
        convertView.setTag(holder);

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
