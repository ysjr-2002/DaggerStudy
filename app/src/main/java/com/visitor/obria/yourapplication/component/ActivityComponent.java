package com.visitor.obria.yourapplication.component;

import com.visitor.obria.yourapplication.MainActivity;
import com.visitor.obria.yourapplication.StudentActivity;
import com.visitor.obria.yourapplication.UserActivity;
import com.visitor.obria.yourapplication.module.StudentModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * created by yangshaojie  on 2018/9/6
 * email: ysjr-2002@163.com
 */
@Singleton
@Component(modules = StudentModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(UserActivity activity);
}
