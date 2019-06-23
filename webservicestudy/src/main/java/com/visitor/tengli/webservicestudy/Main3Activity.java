package com.visitor.tengli.webservicestudy;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main3Activity extends Activity {

    @BindView(R.id.llroot)
    LinearLayout llroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.APPEARING, getAppearing());
        llroot.setLayoutTransition(layoutTransition);
    }

    private Animator getAppearing() {

        AnimatorSet mSet = new AnimatorSet();

        ObjectAnimator objectAnimatorx = ObjectAnimator.ofFloat(null, "translationX", 500, 0);
        objectAnimatorx.setDuration(500);

        ObjectAnimator objectAnimatora = ObjectAnimator.ofFloat(null, "Alpha", 0, 1);
        objectAnimatora.setDuration(500);

//        mSet.playTogether(objectAnimatorx, objectAnimatora);
//        return mSet;


        return  objectAnimatorx;
    }

    @OnClick({R.id.button2})
    public void onViewClicked() {

        float scale = this.getResources().getDisplayMetrics().scaledDensity;

        View view = this.getLayoutInflater().inflate(R.layout.sign_view, null);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        layoutParams.setMargins(20, 20, 20, 20);
        view.setLayoutParams(layoutParams);
//        RelativeLayout rlroot = view.findViewById(R.id.rlroot);


        ImageView imageView = view.findViewById(R.id.imageView);
        TextView tvname = view.findViewById(R.id.tvname);
        TextView tvtitle = view.findViewById(R.id.tvtitle);

        imageView.setBackgroundResource(R.mipmap.fwy);
        tvname.setText("杨绍杰");
        tvtitle.setText("研发工程师");



        llroot.addView(view, 0);
    }

}
