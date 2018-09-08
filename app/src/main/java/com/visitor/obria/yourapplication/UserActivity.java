package com.visitor.obria.yourapplication;

import android.widget.TextView;

import com.visitor.obria.yourapplication.model.Student;

import javax.inject.Inject;

import butterknife.BindView;

public class UserActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    int getViewId() {
        return R.layout.activity_user;
    }


    @Inject
    Student student1;

    @Inject
    Student student2;

    @Override
    void onCreate() {

        String name1= student1.getName();
        String name2 = student2.getName();

        if (student1.equals(student2)) {
            String x = "";
        }

        if (student1 == student2) {
            String x = "";
        }
    }

    @Override
    void initInject() {

        getActivityComponent().inject(this);
    }
}
