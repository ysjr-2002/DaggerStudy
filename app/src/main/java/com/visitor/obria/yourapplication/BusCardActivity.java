package com.visitor.obria.yourapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusCardActivity extends AppCompatActivity {

    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;


    int[] array_image = {R.mipmap.face, R.mipmap.face1, R.mipmap.face2};

    Animation animation_scale;
    Animation animation_translate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //给状态栏设置颜色。我设置透明。
        window.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_bus_card);
        ButterKnife.bind(this);
        int width = this.getResources().getDisplayMetrics().widthPixels;

        animation_scale = AnimationUtils.loadAnimation(BusCardActivity.this, R.anim.scale);
        animation_translate = AnimationUtils.loadAnimation(BusCardActivity.this, R.anim.translate);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ImageSwitcher test = new ImageSwitcher();
        new Thread(test).start();

//        Message message = new Message();
//        message.arg1 = array_image[0];
//        message.arg2 = array_image[1];
//        myHandler.sendMessageDelayed(message, 2000);
    }

    boolean bInit= true;
    private Handler myHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

//            if (bInit) {
                img1.setImageResource(msg.arg2);
                img1.startAnimation(animation_scale);

                img2.setImageResource(msg.arg1);
                img2.startAnimation(animation_translate);
//                bInit = false;
//            }
//            else
//            {
//                img2.setImageResource(msg.arg2);
//                img2.startAnimation(animation_scale);
//
//                img1.setImageResource(msg.arg1);
//                img1.startAnimation(animation_translate);
//                bInit = true;
//            }

            return false;
        }
    });


    class ImageSwitcher implements Runnable {
        @Override
        public void run() {

            while (true) {
                for (int i = 0; i < array_image.length; i++) {

                    int a = i + 1;
                    if (a >= array_image.length) {
                        a = 0;
                    }
                    Message message = new Message();
                    message.arg1 = array_image[i];
                    message.arg2 = array_image[a];
                    myHandler.sendMessage(message);
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
