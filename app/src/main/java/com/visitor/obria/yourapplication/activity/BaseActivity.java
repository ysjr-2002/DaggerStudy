package com.visitor.obria.yourapplication.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.visitor.obria.yourapplication.component.ActivityComponent;
import com.visitor.obria.yourapplication.component.AppComponent;
import com.visitor.obria.yourapplication.component.DaggerActivityComponent;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        ButterKnife.bind(this);

        initInject();

        create();
    }

    abstract @LayoutRes
    protected int getViewId();

    protected abstract void create();

    protected abstract void initInject();

    protected AppComponent getAppcomponent() {

        return MyApplication.getInstance().getAppComponent();
    }

    protected ActivityComponent getActivityComponent() {

        return DaggerActivityComponent.builder().appComponent(getAppcomponent()).build();
    }
}
