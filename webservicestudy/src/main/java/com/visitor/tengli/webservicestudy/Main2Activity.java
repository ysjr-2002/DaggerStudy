package com.visitor.tengli.webservicestudy;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.visitor.tengli.webservicestudy.R;

public class Main2Activity extends AppCompatActivity implements Runnable {

    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
    }

    private Thread mThread;

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(this, 1000);
    }


    private int index = 0;
    private int images[] = {R.mipmap.t1, R.mipmap.t2, R.mipmap.t3};
    private Handler mHandler = new Handler();

    @OnClick(R.id.button)
    public void onViewClicked() {
        //this.finish();
        mHandler.removeCallbacks(this);
    }

    @Override
    public void run() {

        if (index == 3) {
            index = 0;
        }

        imageView.setBackgroundResource(images[index]);
        index++;
        mHandler.postDelayed(this, 1000);
    }
}
