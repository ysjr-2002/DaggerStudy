package com.visitor.tengli.socketapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    final String tag = "ysj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button})
    public void onViewClicked(View view) {

        if (view.getId() == R.id.button1) {
            mThread.interrupt();
            Log.d(tag, "interrupt->" + mThread.isAlive());
            Log.d(tag, "isalive->" + mThread.isInterrupted());
        } else {
            boolean isalive = mThread.isAlive();
            boolean isinterrupt = mThread.isInterrupted();

            Log.d(tag, "interrupt->" + isinterrupt);
            Log.d(tag, "isalive->" + isalive);
        }
    }

    MyThread mThread;

    @Override
    protected void onResume() {
        super.onResume();


        String ip = IPUtil.getIP2(this);

        mThread = new MyThread();
        mThread.start();
    }

    class MyThread extends Thread {
        @Override
        public void interrupt() {
            super.interrupt();
            Log.d(tag, "interrupt");
        }

        @Override
        public void run() {
            super.run();
            Log.d(tag, "run");
            while (true) {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
