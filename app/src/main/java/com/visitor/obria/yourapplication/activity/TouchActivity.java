package com.visitor.obria.yourapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.visitor.obria.yourapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TouchActivity extends AppCompatActivity {

    @BindView(R.id.tv_info)
    ImageView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        ButterKnife.bind(this);
    }

    private void myToast(String content) {

        Toast toast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,);
                AnimationSet set = (AnimationSet) AnimationUtils.loadAnimation(TouchActivity.this, R.anim.translate_moveout);
                tvInfo.startAnimation(set);
            }
        }, 3000);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
//                String info = String.format("down-> w=%s, h=%s", tvInfo.getWidth(), tvInfo.getHeight());
//                myToast(info);
                Animation shit = AnimationUtils.loadAnimation(this, R.anim.translate_move_to_center);
                tvInfo.startAnimation(shit);
                break;
            case MotionEvent.ACTION_UP:
//                myToast("up");
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
