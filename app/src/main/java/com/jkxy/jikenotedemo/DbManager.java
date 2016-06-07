package com.jkxy.jikenotedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyu on 6/7/2016.
 */
public class DbManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add NotificationBeans
     *
     * @param persons
     */
    public void add(List<NotificationBean> lists) {
        db.beginTransaction();  //开始事务
        try {
            for (NotificationBean notificationBean : lists) {
                db.execSQL("INSERT INTO " + DBHelper.TABLE_NAME + "(" + DBHelper.COL_ID + "(" + DBHelper.COL_HOUR + "," + DBHelper.COL_EVENT + ") VALUES(?,?,?)", new Object[]{notificationBean.getId(), notificationBean.getHour(), notificationBean.getEvent()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    public void insert(NotificationBean notificationBean) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO " + DBHelper.TABLE_NAME + "(" + DBHelper.COL_ID + "," + DBHelper.COL_HOUR + "," + DBHelper.COL_EVENT + ") VALUES(?,?,?)", new Object[]{notificationBean.getId(), notificationBean.getHour(), notificationBean.getEvent()});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }


    public void updateNotification(NotificationBean notificationBean) {
        ContentValues cv = new ContentValues();
        cv.put("hour", notificationBean.getHour());
        cv.put("event", notificationBean.getEvent());
        db.update(DBHelper.TABLE_NAME, cv, "_id = ?", new String[]{notificationBean.getId() + ""});
    }


    public void deleteNotification(NotificationBean notificationBean) {
        db.delete(DBHelper.TABLE_NAME, "_id = ?", new String[]{notificationBean.getId() + ""});
    }

    public List<NotificationBean> query() {
        ArrayList<NotificationBean> lists = new ArrayList<NotificationBean>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            NotificationBean notificationBean = new NotificationBean();
            notificationBean.setId(c.getLong(c.getColumnIndex(DBHelper.COL_ID)));
            notificationBean.setHour(c.getInt(c.getColumnIndex(DBHelper.COL_HOUR)));
            notificationBean.setEvent(c.getString(c.getColumnIndex(DBHelper.COL_EVENT)));
            lists.add(notificationBean);
        }
        c.close();
        return lists;
    }

    /**
     * @return
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
