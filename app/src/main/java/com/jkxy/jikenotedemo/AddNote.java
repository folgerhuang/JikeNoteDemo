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
import android.text.TextUtils;
import android.text.format.*;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        String strHour = etHour.getText().toString().trim();
        String strEvent=etEvent.getText().toString().trim();
        if (!TextUtils.isEmpty(strHour) && !TextUtils.isEmpty(strEvent)) {
            if (Integer.parseInt(strHour) >= 0 && Integer.parseInt(strHour) <= 23) {
                Intent i = new Intent();
                long id = System.currentTimeMillis();
                int hour = Integer.parseInt(strHour);

                String event = etEvent.getText().toString();
                i.putExtra("data", new NotificationBean(id, hour, event));

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
