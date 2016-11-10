package com.example.user.workshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by computer on 5/11/2559.
 */
public class CustomAdapter extends BaseAdapter{
    Context mContext;
    String[] NewsName;
    String[] DateName;
    int[] resId;

    public CustomAdapter(Context context, String[] NewsName,String[] DateName, int[] resID) {
        this.mContext = context;
        this.NewsName = NewsName;
        this.DateName = DateName;
        this.resId = resID;
    }

    @Override
    public int getCount() {
        return NewsName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder myHolder = null;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.activity_row_list, null);

            myHolder = new ViewHolder();
            myHolder.tvNews = (TextView) convertView.findViewById(R.id.tvNews);
            myHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            myHolder.imgAnd = (ImageView) convertView.findViewById(R.id.imgAnd);
            convertView.setTag(myHolder);
        } else {

            myHolder = (ViewHolder) convertView.getTag();
        }

        myHolder.tvNews.setText(NewsName[position]);
        myHolder.tvDate.setText(DateName[position]);
        myHolder.imgAnd.setImageResource(resId[position]);



        return convertView;
    }

    public class ViewHolder {

        TextView tvNews;
        TextView tvDate;
        ImageView imgAnd;

    }

}