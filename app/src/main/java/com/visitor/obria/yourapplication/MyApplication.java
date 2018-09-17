package com.visitor.obria.yourapplication;

import android.app.Application;

import com.visitor.obria.yourapplication.component.AppComponent;
import com.visitor.obria.yourapplication.component.DaggerAppComponent;

public class MyApplication extends Application {

    private static MyApplication myApplication;

    private  AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mAppComponent = DaggerAppComponent.create();
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
