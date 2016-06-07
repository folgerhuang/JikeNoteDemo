package com.jkxy.jikenotedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shiyu on 6/7/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "event.db";
    private static final int DATABASE_VERSION = 1;

    public static String TABLE_NAME = "tbEvent";
    public static String COL_ID = "_id";
    public static String COL_HOUR = "hour";
    public static String COL_EVENT = "event";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COL_ID + " Long PRIMARY KEY," +
                COL_HOUR + " Integer not null," +
                COL_EVENT + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
