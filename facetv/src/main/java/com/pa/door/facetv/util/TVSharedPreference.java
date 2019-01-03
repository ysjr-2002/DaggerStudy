package com.pa.door.facetv.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * created by yangshaojie  on 2019/1/3
 * email: ysjr-2002@163.com
 */
public class TVSharedPreference {

    private static final String welcome = "welcome";
    private static final String camera = "camera";

    private SharedPreferences mSharedPreferences;

    public TVSharedPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences("tv", Context.MODE_PRIVATE);
    }

    public String getWelcome() {

        String temp = mSharedPreferences.getString(welcome, "");
        return temp;
    }

    public String getCamera() {

        String temp = mSharedPreferences.getString(camera, "");
        return temp;
    }

    public void saveWelcome(String val) {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(welcome, val);
        editor.commit();
    }

    public void saveCamera(String val) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(camera, val);
        editor.commit();
    }
}
