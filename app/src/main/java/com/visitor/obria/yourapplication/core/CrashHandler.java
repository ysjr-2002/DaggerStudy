package com.visitor.obria.yourapplication.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.visitor.obria.yourapplication.activity.ListViewActivity;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;

    public CrashHandler(Context context) {

        this.mContext = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (e != null) {
            e.printStackTrace();
        }

        Intent intent = new Intent(mContext.getApplicationContext(), ListViewActivity.class);
        intent.putExtra("crash", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);


    }
}
