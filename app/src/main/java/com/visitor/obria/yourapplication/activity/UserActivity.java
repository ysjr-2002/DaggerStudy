package com.visitor.obria.yourapplication.activity;

import android.widget.TextView;

import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.activity.BaseActivity;

import butterknife.BindView;

public class UserActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected int getViewId() {
        return R.layout.activity_user;
    }


    @Override
    protected void create() {
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }
}
