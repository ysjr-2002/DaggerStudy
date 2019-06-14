package com.visitor.tengli.webservicestudy;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

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

//        new Config().read();
        new ThreadStudy().test();
        new ThreadStudy().list();
    }

    BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
    SortedSet<Integer> list = new TreeSet<>();


    @OnClick(R.id.button)
    public void onViewClicked() {
        //this.finish();
        mThread.interrupt();
    }
}
