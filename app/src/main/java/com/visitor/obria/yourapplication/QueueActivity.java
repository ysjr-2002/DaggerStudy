package com.visitor.obria.yourapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueueActivity extends AppCompatActivity {

    BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        boolean add = false;
        add = queue.offer(1);
        add = queue.offer(2);
        add = queue.offer(3);
        add = queue.offer(4);
    }

    boolean xy = false;

    @OnClick({R.id.btn_test, R.id.btn_send_message, R.id.btn_clear_message})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_test:
                queueTest();
                break;
            case R.id.btn_send_message:
                if (xy == false) {
                    myHandler.sendEmptyMessageDelayed(101, 3000);
                    xy = true;
                } else {
                    xy = false;
                    myHandler.sendEmptyMessageDelayed(102, 3000);
                }
                break;
            case R.id.btn_clear_message:
                // myHandler.removeMessages(101);
                sort();
                break;
        }
    }

    private void sort() {
        List<String> list = new ArrayList<>();
        list.add("b");
        list.add("c");
        list.add("a");
        list.add("d");
        list.add("test");

        //Collections.sort(list);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                if (o1.length() > o2.length()) {
                    return 0;
                } else
                    return 1;
            }
        });

        for (String s :
                list) {
            Log.d("kk", s);
        }

        System.out.print(100 + 100);
        System.out.print("100 +100");
    }

    private Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 101) {
                Log.d("kk", "祝福");
                return true;
            }
            if (msg.what == 102) {
                Log.d("kk", "抱怨");
                return true;
            }
            return false;
        }
    });

    private void queueTest() {
        try {
            int a = queue.take().intValue();
            int b = queue.take().intValue();
            int c = queue.take().intValue();
            int d = queue.take().intValue();
            int e = queue.take().intValue();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
