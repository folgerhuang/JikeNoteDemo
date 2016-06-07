package com.jkxy.jikenotedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private EditText etEvent;
    private EditText etHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etHour= (EditText) findViewById(R.id.etHour);
        etEvent= (EditText) findViewById(R.id.etEvent);

        btnSave= (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                createNotification();
                break;
        }
    }

    private void createNotification() {
        if (etHour.getText().toString().trim().compareTo("")!=0 && etEvent.getText().toString().trim().compareTo("")!=0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if(Integer.parseInt(etHour.getText().toString().trim())>=0 && Integer.parseInt(etHour.getText().toString().trim())<=23 ) {
                Intent i = new Intent();
                long id = System.currentTimeMillis();
                int hour = Integer.parseInt(etHour.getText().toString().trim());

                String event = etEvent.getText().toString();
                i.putExtra("data", new NotificationBean(id, hour, event));

             /*   Intent intent=new Intent(this,MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this,(int)id,intent,0);
                AlarmManager am= (AlarmManager) getSystemService(ALARM_SERVICE);
              am.setAlarmClock(AlarmManager.ELAPSED_REALTIME_WAKEUP,);*/





                //提醒
            /*Intent intent=new Intent(this,MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this,(int)System.currentTimeMillis(),intent,0);
            notification=new Notification.Builder(this)
                    .setContentTitle("Notification")
                    .setContentText(etHour.getText().toString()+","+etEvent.getText().toString())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setContentIntent(pIntent)
                    .addAction(R.mipmap.ic_launcher,"More",pIntent).build();
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notification.flags |=Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(0,notification);*/

                setResult(RESULT_OK, i);

                finish();
            }else
            {
                Toast.makeText(this,"请输入0-23之间的整数",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"事件时间或者内容不能为空！",Toast.LENGTH_SHORT).show();
        }
    }

    private NotificationManager mNotificationManager;
    private Notification notification;
    private NotificationBean myNotificationBean;
}
