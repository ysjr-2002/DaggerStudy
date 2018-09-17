package com.visitor.obria.yourapplication.component;

import com.visitor.obria.yourapplication.MainActivity;
import com.visitor.obria.yourapplication.StudentActivity;
import com.visitor.obria.yourapplication.UserActivity;
import com.visitor.obria.yourapplication.qinru.Step1Activity;
import com.visitor.obria.yourapplication.qinru.Step2Activity;
import com.visitor.obria.yourapplication.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * created by yangshaojie  on 2018/9/6
 * email: ysjr-2002@163.com
 */
@ActivityScope
@Component(dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(UserActivity activity);

    void inject(Step1Activity activity);

    void inject(Step2Activity activity);
}
