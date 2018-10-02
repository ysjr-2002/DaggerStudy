package com.visitor.obria.yourapplication.component;

import com.visitor.obria.yourapplication.activity.MainActivity;
import com.visitor.obria.yourapplication.activity.OkHttpActivity;
import com.visitor.obria.yourapplication.activity.UserActivity;
import com.visitor.obria.yourapplication.qinru.Step1Activity;
import com.visitor.obria.yourapplication.qinru.Step2Activity;
import com.visitor.obria.yourapplication.scope.ActivityScope;

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

    void inject(OkHttpActivity activity);
}
