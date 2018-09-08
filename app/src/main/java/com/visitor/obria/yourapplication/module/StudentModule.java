package com.visitor.obria.yourapplication.module;

import com.visitor.obria.yourapplication.MainPresenter;
import com.visitor.obria.yourapplication.model.Student;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * created by yangshaojie  on 2018/9/6
 * email: ysjr-2002@163.com
 */

@Module
public class StudentModule {

    Student student;

    public StudentModule() {
        student = new Student();
    }

    @Singleton
    @Provides
    public Student provideStudent() {
        return student;
    }

    @Provides
    public MainPresenter providerTest() {
        return new MainPresenter();
    }
}
