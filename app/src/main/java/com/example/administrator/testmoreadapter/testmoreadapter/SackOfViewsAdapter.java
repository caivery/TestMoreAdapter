package com.example.administrator.testmoreadapter.testmoreadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class SackOfViewsAdapter extends BaseAdapter {

    private List<View> listView ;

    public SackOfViewsAdapter(List<View> list){
       this.listView = list;
    }


    @Override
    public int getViewTypeCount() {
        return listView.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return listView.size();
    }

    @Override
    public Object getItem(int position) {
        return listView.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return listView.get(position);
    }
}





















