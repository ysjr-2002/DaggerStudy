package com.visitor.obria.yourapplication.activity;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.visitor.obria.yourapplication.component.AppComponent;
import com.visitor.obria.yourapplication.component.DaggerAppComponent;
import com.visitor.obria.yourapplication.dao.DaoMaster;
import com.visitor.obria.yourapplication.dao.DaoSession;
import com.visitor.obria.yourapplication.dao.PersonBean;
import com.visitor.obria.yourapplication.dao.PersonBeanDao;


public class MyApplication extends Application {

    private static MyApplication myApplication;

    private final String db_name = "ysj.db";

    private PersonBeanDao mPersonBeanDao;

    private AppComponent mAppComponent;

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

        PersonBean bean = new PersonBean(null, "123", "杨绍杰", "");
        mPersonBeanDao.insert(bean);

        bean = new PersonBean(null, "456", "杜高丽", "");
        mPersonBeanDao.insert(bean);

        bean = new PersonBean(null, "789", "杨林哲", "");
        mPersonBeanDao.insert(bean);
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
}
