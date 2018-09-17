package com.visitor.obria.yourapplication.qinru;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.visitor.obria.yourapplication.BaseActivity;
import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.presenter.Step2Presenter;

import javax.inject.Inject;

public class Step2Activity extends BaseActivity {


    @Inject
    Step2Presenter mPresenter;

    private void init() {

//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
//        {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        int color = this.getResources().getColor(R.color.colorRed);
        window.setStatusBarColor(color);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_step2;
    }

    @Override
    protected void create() {

        init();
        mPresenter.attachView(this);
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }


    public static void Start(Context context) {

        Intent intent = new Intent(context, Step2Activity.class);
        context.startActivity(intent);
    }
}
