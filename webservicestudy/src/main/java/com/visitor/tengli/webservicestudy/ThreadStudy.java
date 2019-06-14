package com.visitor.tengli.webservicestudy;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * created by yangshaojie  on 2019/6/14
 * email: ysjr-2002@163.com
 */
public class ThreadStudy {

    public void test() {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                fun1();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                fun1();
            }
        });
        thread1.setName("thread1");
        thread2.setName("thread2");

        thread1.start();
        thread2.start();
    }

    private final Object sync = new Object();

    private void fun1() {

        synchronized (sync) {
            int i = 1;
            while (i <= 10) {
                Log.d("thread", Thread.currentThread().getName() + ":" + i);
                i++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void list() {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        if (list.isEmpty()) {

        }

        Iterator<Integer> test = list.iterator();
        while (test.hasNext()) {
            int val = test.next();
        }
    }
}
