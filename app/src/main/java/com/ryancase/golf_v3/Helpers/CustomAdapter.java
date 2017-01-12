package com.ryancase.golf_v3.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ryancase.golf_v3.R;

import java.util.List;

/**
 * Created by ryancase on 1/11/17.
 */

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<String> names, dates;

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
    }

    public CustomAdapter(Context context, List<String> names, List<String> dates) {
        inflater = LayoutInflater.from(context);
        this.names = names;
        this.dates = dates;
    }

    public int getCount() {
        return names.size();
    }

    public String getItem(int position) {
        return names.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.history_list_item, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.names);
            holder.textView2 = (TextView) convertView.findViewById(R.id.dates);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView1.setText(names.get(position));
        holder.textView2.setText(dates.get(position));
        return convertView;
    }
}