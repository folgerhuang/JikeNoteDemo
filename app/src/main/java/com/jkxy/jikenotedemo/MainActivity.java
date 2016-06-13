package com.jkxy.jikenotedemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  List<NotificationBean> notificationBeanList=new ArrayList<>();
    private static final int ADD_EVENT_CODE = 0;

    private ListView lvEvents;
    private NotificationAdapter myAdapter;

    private  DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvEvents= (ListView) findViewById(R.id.lvEvents);
        dbManager =new DbManager(this);
        notificationBeanList=dbManager.query();
        myAdapter=new NotificationAdapter(this,notificationBeanList);

        lvEvents.setAdapter(myAdapter);
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("系统提示")
                        .setMessage("确定要删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteAlarm(notificationBeanList.get(position));
                                dbManager.deleteNotification(notificationBeanList.get(position));
                                notificationBeanList=dbManager.query();
                                myAdapter=new NotificationAdapter(MainActivity.this,notificationBeanList);

                                lvEvents.setAdapter(myAdapter);
                            }
                        })
                        .setNegativeButton("取消",null).show();
            }
        });


    }

    private void deleteAlarm(NotificationBean notificationBean) {
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("data", new NotificationBean(notificationBean.getId(), notificationBean.getHour(), notificationBean.getEvent()));
        PendingIntent sender = PendingIntent.getBroadcast(this, notificationBean.getHour()+notificationBean.getEvent().length(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* menu.add("Add Note");
        menu.add("Help");*/
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.newEvent:
                newEvent();
                break;

       /* switch (item.getTitle().toString()){
            case "Add Note":
//                Toast.makeText(this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,AddNote.class);
                startActivityForResult(intent,ADD_EVENT_CODE);
                break;
            case "Help":
                Toast.makeText(this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                break;*/
        }

        return super.onOptionsItemSelected(item);
    }

    private void newEvent() {
        Intent intent=new Intent(MainActivity.this,AddNote.class);
        startActivityForResult(intent,ADD_EVENT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_EVENT_CODE && resultCode==RESULT_OK){
            NotificationBean notification = (NotificationBean)data.getSerializableExtra("data");
            notificationBeanList.add(notification);
            dbManager.insert(notification);

            myAdapter.notifyDataSetChanged();
            setAlarm(notification);


            Toast.makeText(this,"Add note successfully.",Toast.LENGTH_SHORT).show();
        }
    }



    private void setAlarm(NotificationBean notificationBean){
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("data", new NotificationBean(notificationBean.getId(), notificationBean.getHour(), notificationBean.getEvent()));
        PendingIntent sender = PendingIntent.getBroadcast(this, notificationBean.getHour()+notificationBean.getEvent().length(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.e("current hour",currentHour+"");
        calendar.set(Calendar.HOUR_OF_DAY, notificationBean.getHour());
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.e("alarm time", calendar.getTime() + "");

        if (currentHour <= notificationBean.getHour()) {
            //为了测试测试设置周期为4分钟
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                DateUtils.MINUTE_IN_MILLIS * 4, sender);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    DateUtils.DAY_IN_MILLIS, sender);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+DateUtils.DAY_IN_MILLIS,
                    DateUtils.DAY_IN_MILLIS, sender);

        }

    }


    private AlertDialog menuDialog;
}
