package com.jkxy.jikenotedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_EVENT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Add Note");
        menu.add("Help");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()){
            case "Add Note":
//                Toast.makeText(this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,AddNote.class);
                startActivityForResult(intent,ADD_EVENT_CODE);
                break;
            case "Help":
                Toast.makeText(this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_EVENT_CODE && resultCode==RESULT_OK){
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
