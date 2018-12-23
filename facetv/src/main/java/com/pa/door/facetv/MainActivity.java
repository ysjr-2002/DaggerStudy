package com.pa.door.facetv;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int POPUP_DELAY = 6000;
    @BindView(R.id.flcontainer)
    FrameLayout flcontainer;
//    @BindView(R.id.ivFace)
//    CircleImageView ivFace;
//    @BindView(R.id.rlroot)
//    RelativeLayout rlroot;

    PopupWindow window = null;
    @BindView(R.id.root)
    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        initPopup();
    }

    private void initPopup() {

        View view = this.getLayoutInflater().inflate(R.layout.bottommenu, null, false);
        window = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setContentView(view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //window.showAtLocation(root, Gravity.BOTTOM, 0, 0);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (window != null) {
                window.dismiss();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int w = metrics.widthPixels;
        int h = metrics.heightPixels;
        float s = metrics.scaledDensity;
        String tip = String.format("w:%s h:%s density:%f", w, h, s);
        Toast.makeText(this, tip, Toast.LENGTH_LONG).show();


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            show();
                        }
                    });

                    try {
                        Thread.sleep(4 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }

    private void show() {

        View view = this.getLayoutInflater().inflate(R.layout.recognize, null, false);
        flcontainer.addView(view);

        flcontainer.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //发送清屏消息
                mDelayHandler.removeMessages(0);
                mDelayHandler.sendEmptyMessageDelayed(0, POPUP_DELAY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        flcontainer.startAnimation(animation);
    }

    private Handler mDelayHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            Animation animation_tranlsate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
            flcontainer.startAnimation(animation_tranlsate);
            return true;
        }
    });
}