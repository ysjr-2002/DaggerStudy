package com.visitor.obria.yourapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.visitor.obria.yourapplication.component.ActivityComponent;
import com.visitor.obria.yourapplication.component.DaggerActivityComponent;
import com.visitor.obria.yourapplication.module.StudentModule;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        ButterKnife.bind(this);

        initInject();

        onCreate();
    }

    abstract @LayoutRes int getViewId();

    abstract void onCreate();

    abstract void initInject();

    protected ActivityComponent getActivityComponent() {

        return DaggerActivityComponent.builder().studentModule(new StudentModule()).build();
    }
}
