package com.pa.door.facetv;

import android.app.Application;

import com.pa.door.facetv.util.TVSharedPreference;

/**
 * created by yangshaojie  on 2019/1/3
 * email: ysjr-2002@163.com
 */
public class AppTV extends Application {

    private static TVSharedPreference mshare;

    @Override
    public void onCreate() {
        super.onCreate();
        mshare = new TVSharedPreference(this);
    }

    public static TVSharedPreference getSharedInstance() {

        return mshare;
    }
}
