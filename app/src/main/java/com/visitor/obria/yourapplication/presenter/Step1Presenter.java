package com.visitor.obria.yourapplication.presenter;

import android.util.Log;

import com.visitor.obria.yourapplication.Config;
import com.visitor.obria.yourapplication.bean.StudenBean;
import com.visitor.obria.yourapplication.qinru.Step1Activity;

import javax.inject.Inject;

public class Step1Presenter {

    StudenBean mStudenBean;
    Step1Activity mActivity;

    @Inject
    Step1Presenter(StudenBean studenBean) {

        this.mStudenBean = studenBean;
    }

    public void attachView(Step1Activity activity) {

        String user = this.mStudenBean.getName();
        mActivity = activity;
        Log.d(Config.tag, this.mStudenBean + "");
    }
}
