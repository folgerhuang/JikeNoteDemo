package com.jkxy.jikenotedemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by yushi on 16/6/8.
 */
public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationBean notification = (NotificationBean) intent.getSerializableExtra("data");
        Log.e("data",notification.toString());

        creatNotification(context,"Times Up",notification.getHour()+":"+notification.getEvent(),"Alert");
    }

    private void creatNotification(Context context, String title, String content, String alert) {
        PendingIntent notificIntent=PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setTicker(alert)
                .setContentText(content);
        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());
    }
}
