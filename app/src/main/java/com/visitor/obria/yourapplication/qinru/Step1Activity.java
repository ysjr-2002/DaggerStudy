package com.visitor.obria.yourapplication.qinru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.visitor.obria.yourapplication.MySurfaceView;
import com.visitor.obria.yourapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Step1Activity extends AppCompatActivity {

    @BindView(R.id.btn_a1)
    Button btnA1;
    @BindView(R.id.sfv)
    MySurfaceView sfv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
//        {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

//        Window window = this.getWindow();
//        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        int color = this.getResources().getColor(R.color.colorBlue);
        this.getWindow().setStatusBarColor(color);
    }

    @OnClick(R.id.btn_a1)
    public void onViewClicked() {

//        Step2Activity.StartStep2Activity(this);
////        this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
//        if (a) {
//            sfv.drawRed();
//            a = false;
//        } else {
//            sfv.drawBlue();
//            a = true;
//        }
        sfv.change();
    }

    boolean a = false;
}
