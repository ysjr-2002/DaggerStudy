package com.visitor.obria.yourapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.fragment.FaceFragment;
import com.visitor.obria.yourapplication.fragment.MainFragment;

public class FaceActivity extends AppCompatActivity {

    FragmentManager fm;

    MainFragment mf;
    FaceFragment ff;

    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        initFragment();
        init();

        transformThread.start();
    }

    private void initFragment() {
        mf = new MainFragment();
        ff = new FaceFragment();
    }

    private void init() {

        fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.fragmentcontainer, mf);
        ft.add(R.id.fragmentcontainer, ff);

        ft.hide(ff);
        mCurrentFragment = mf;
        ft.commit();
    }

    private void transformFragment(boolean flag) {

        if (flag) {

            FragmentTransaction transaction = fm.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.show(ff);
            transaction.hide(mf);
            transaction.commit();
        } else {

            FragmentTransaction transaction = fm.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.hide(ff);
            transaction.show(mf);
            transaction.commit();
        }
    }

    boolean flag = true;
    private Thread transformThread = new Thread(new Runnable() {
        @Override
        public void run() {


            while (true) {

                FaceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        transformFragment(flag);
                        flag = !flag;
                    }
                });

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    });
}
