package com.visitor.obria.yourapplication.qinru;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.visitor.obria.yourapplication.BaseActivity;
import com.visitor.obria.yourapplication.MySurfaceView;
import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.api.ApiService;
import com.visitor.obria.yourapplication.presenter.Step1Presenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Step1Activity extends BaseActivity {

    @BindView(R.id.btn_a1)
    Button btnA1;
    @BindView(R.id.sfv)
    MySurfaceView sfv;

    private void init() {

        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int color = this.getResources().getColor(R.color.colorBlue);
        window.setStatusBarColor(color);
    }

    @Inject
    Step1Presenter mPresenter;

    @Override
    protected int getViewId() {
        return R.layout.activity_step1;
    }

    @Override
    protected void create() {

        init();
        mPresenter.attachView(this);
    }


    private void retrofittest() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.baidu.com/").build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.get();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String body = response.body().toString();
                String str = "";
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                String error = "";
            }
        });
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @OnClick(R.id.btn_a1)
    public void onViewClicked() {

        //Step2Activity.Start(this);
        retrofittest();
    }
}
