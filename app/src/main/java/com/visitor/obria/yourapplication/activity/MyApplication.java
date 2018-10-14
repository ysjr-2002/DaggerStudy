package com.visitor.obria.yourapplication.activity;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.visitor.obria.yourapplication.component.AppComponent;
import com.visitor.obria.yourapplication.component.DaggerAppComponent;
import com.visitor.obria.yourapplication.core.CrashHandler;
import com.visitor.obria.yourapplication.dao.DaoMaster;
import com.visitor.obria.yourapplication.dao.DaoSession;
import com.visitor.obria.yourapplication.dao.PersonBean;
import com.visitor.obria.yourapplication.dao.PersonBeanDao;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {

    private static MyApplication myApplication;

    private final String db_name = "ysj.db";

    private PersonBeanDao mPersonBeanDao;

    private AppComponent mAppComponent;

    private int activeAcount = 0;
    private final List<Activity> allActivies = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mAppComponent = DaggerAppComponent.create();

        DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(this, db_name);

        SQLiteDatabase database = openHelper.getWritableDatabase();

        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mPersonBeanDao = daoSession.getPersonBeanDao();

        mPersonBeanDao.deleteAll();

        PersonBean bean = new PersonBean(1l, "123", "杨绍杰", "");
        mPersonBeanDao.insert(bean);

        bean = new PersonBean(1l, "456", "杜高丽", "");
        mPersonBeanDao.insert(bean);

        bean = new PersonBean(1l, "789", "杨林哲", "");
        mPersonBeanDao.insert(bean);

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                Log.d("cao", "create->" + activity.getClass().getName());
                allActivies.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

                activeAcount++;
                Log.d("cao", "start->" + activity.getClass().getName());
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d("cao", "pause->" + activity.getClass().getName());
            }

            @Override
            public void onActivityStopped(Activity activity) {

                activeAcount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

                Log.d("cao", "destroyed->" + activity.getClass().getName());
                allActivies.remove(activity);
            }
        });
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public PersonBeanDao getPersonBeanDao() {
        return mPersonBeanDao;
    }

    public void exitApp() {

        finishAllActivites();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void finishAllActivites() {

        synchronized (allActivies) {

            for (Activity activity :
                    allActivies) {

                activity.finish();
            }
            allActivies.clear();
        }
    }
}
