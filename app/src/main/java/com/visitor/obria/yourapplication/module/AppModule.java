package com.visitor.obria.yourapplication.module;

import com.visitor.obria.yourapplication.bean.StudenBean;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * created by yangshaojie  on 2018/9/6
 * email: ysjr-2002@163.com
 */

@Module
public class AppModule {

    public AppModule() {

    }

    @Singleton
    @Provides
    public StudenBean provideStudent() {
        return new StudenBean();
    }
}
