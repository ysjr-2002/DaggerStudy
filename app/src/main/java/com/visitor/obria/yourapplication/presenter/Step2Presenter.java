package com.visitor.obria.yourapplication.presenter;

import android.util.Log;

import com.visitor.obria.yourapplication.Config;
import com.visitor.obria.yourapplication.bean.StudenBean;
import com.visitor.obria.yourapplication.qinru.Step2Activity;

import javax.inject.Inject;

public class Step2Presenter {

    private StudenBean mStudenBean;
    private Step2Activity mActivity;

    @Inject
    public Step2Presenter(StudenBean studenBean) {
        this.mStudenBean = studenBean;
    }

    public void attachView(Step2Activity activity) {

        String user = mStudenBean.getName();
        this.mActivity = mActivity;
        Log.d(Config.tag, mStudenBean + "");
    }
}
