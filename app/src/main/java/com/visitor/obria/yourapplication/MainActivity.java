package com.visitor.obria.yourapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.visitor.obria.yourapplication.model.Student;

import javax.inject.Inject;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    void initInject() {

        getActivityComponent().inject(this);
    }

    @Inject
    Student student1;

    @Inject
    Student student2;

    @Inject
    Student student3;


    @Inject
    MainPresenter presenter;

    @Override
    void onCreate() {

        String name1 = student1.getName();
        String name2 = student2.getName();
        String name3 = student3.getName();

        Log.d("ysj", student1.toString());
        Log.d("ysj", student2.toString());
        if (student1 == student2 && student1 == student3) {
            String t = "";
        } else {
            String y = "";
        }
    }

    @Override
    int getViewId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {

//        Intent intent = new Intent(this, UserActivity.class);
//        startActivity(intent);
//        this.finish();

        presenter.Test();
    }
}
