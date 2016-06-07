package com.jkxy.jikenotedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyu on 6/7/2016.
 */
public class NotificationAdapter extends BaseAdapter {
    private List<NotificationBean> notificationBeanList = new ArrayList<>();
    private Context context;

    public NotificationAdapter(Context context, List<NotificationBean> notificationBeanList) {
        this.context = context;
        this.notificationBeanList = notificationBeanList;
    }


    @Override
    public int getCount() {
        return notificationBeanList.size();
    }

    @Override
    public NotificationBean getItem(int position) {
        return notificationBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (notificationBeanList.size() > 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.event, null);
                holder = new ViewHolder();
                holder.tvHour = (TextView) convertView.findViewById(R.id.tvHour);
                holder.tvEvent = (TextView) convertView.findViewById(R.id.tvEvent);
                holder.tvHour.setText("定时时间:" + notificationBeanList.get(position).getHour() + "");
                holder.tvEvent.setText("事件内容:" + notificationBeanList.get(position).getEvent());
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.tvHour.setText("定时时间:" + notificationBeanList.get(position).getHour() + "");
                holder.tvEvent.setText("事件内容:" + notificationBeanList.get(position).getEvent());
            }
            return convertView;
        } else {
            return null;
        }
    }

    private static class ViewHolder {
        TextView tvHour;
        TextView tvEvent;
    }

}
