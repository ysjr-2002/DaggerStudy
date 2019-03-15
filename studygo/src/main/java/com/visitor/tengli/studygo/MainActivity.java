package com.visitor.tengli.studygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    BlockingQueue<Integer> list = new ArrayBlockingQueue<Integer>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run();
    }

    private void run() {
        doGet();
    }

    private void doGet() {

        String url = "http://192.168.3.54:10001/api/card/heart/?no=1";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                String shit = "";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String content = response.body().string();
            }
        });
    }
}
