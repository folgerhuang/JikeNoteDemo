package com.jkxy.jikenotedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by shiyu on 6/7/2016.
 */
public class ActionBroadCast extends BroadcastReceiver{
    private static int num = 0;
    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.e("ActionBroadCast", "New Message !" + num++);
    }
}
