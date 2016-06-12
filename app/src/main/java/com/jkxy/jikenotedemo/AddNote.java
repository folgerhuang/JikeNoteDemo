package com.jkxy.jikenotedemo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.format.*;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddNote extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private EditText etEvent;
    private EditText etHour;
    private final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etHour = (EditText) findViewById(R.id.etHour);
        etEvent = (EditText) findViewById(R.id.etEvent);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                createNotification();
                break;
        }
    }

    private void createNotification() {
        if (etHour.getText().toString().trim().compareTo("") != 0 && etEvent.getText().toString().trim().compareTo("") != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (Integer.parseInt(etHour.getText().toString().trim()) >= 0 && Integer.parseInt(etHour.getText().toString().trim()) <= 23) {
                Intent i = new Intent();
                long id = System.currentTimeMillis();
                int hour = Integer.parseInt(etHour.getText().toString().trim());

                String event = etEvent.getText().toString();
                i.putExtra("data", new NotificationBean(id, hour, event));

               Intent intent = new Intent(this, AlertReceiver.class);
                intent.putExtra("data",new NotificationBean(id,hour,event));
                PendingIntent sender = PendingIntent.getBroadcast(this,hour, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                // Schedule the alarm!
                AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, 3);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Log.e("alarm time",calendar.getTime()+"");
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        DateUtils.MINUTE_IN_MILLIS*1, sender);


                setResult(RESULT_OK, i);

                finish();
            } else {
                Toast.makeText(this, "请输入0-23之间的整数", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "事件时间或者内容不能为空！", Toast.LENGTH_SHORT).show();
        }
    }


}
