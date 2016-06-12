package com.jkxy.jikenotedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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



            Toast.makeText(this,"Add note successfully.",Toast.LENGTH_SHORT).show();
        }
    }

    /*    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(menuDialog==null) {
            menuDialog = new AlertDialog.Builder(this).setView(MenuView).show();
        }else
        {
            menuDialog.show();
        }
        return false;
    }*/


    private AlertDialog menuDialog;
}
