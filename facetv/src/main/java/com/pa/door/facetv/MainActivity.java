package com.pa.door.facetv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.body.UrlEncodedFormBody;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.pa.door.facetv.bean.AccessRecordBp;
import com.pa.door.facetv.bean.HXFaceBean;
import com.pa.door.facetv.util.IPUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int POPUP_DELAY = 10000;
    @BindView(R.id.flcontainer)
    FrameLayout flcontainer;
//    @BindView(R.id.ivFace)
//    CircleImageView ivFace;
//    @BindView(R.id.rlroot)
//    RelativeLayout rlroot;

    PopupWindow window = null;
    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.rlsetting)
    RelativeLayout rlsetting;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.btnsetting)
    Button btnsetting;
    @BindView(R.id.btnregister)
    Button btnregister;

    String host = "";
    @BindView(R.id.ivtest)
    ImageView ivtest;

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
//            rlsetting.setFocusable(true);
            rlsetting.setVisibility(View.VISIBLE);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (window != null) {
//                window.dismiss();
//            }
            rlsetting.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int w = metrics.widthPixels;
        int h = metrics.heightPixels;
        float s = metrics.scaledDensity;
//        String tip = String.format("w:%s h:%s density:%f", w, h, s);
//        Toast.makeText(this, tip, Toast.LENGTH_LONG).show();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            show();
//                        }
//                    });
//
//                    try {
//                        Thread.sleep(4 * 1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();
    }

    private void show() {

        View view = this.getLayoutInflater().inflate(R.layout.recognize, null, false);
        flcontainer.removeAllViews();
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

    @OnClick({R.id.btnsetting, R.id.btnregister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnsetting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnregister:
                Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        host = IPUtil.getIP(this);
        Toast.makeText(this, host, Toast.LENGTH_SHORT).show();

        AsyncHttpServer server = new AsyncHttpServer();

        server.post("/api/postface", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {

                String contentType = request.getBody().getContentType();
                int length = request.getBody().length();
                String uri = request.getPath();

                Object body = request.getBody();
                Gson gson = new Gson();

                if (body instanceof UrlEncodedFormBody) {

                    UrlEncodedFormBody formBody = (UrlEncodedFormBody) body;
                    Multimap multimap = formBody.get();

                    String postdata = multimap.getString("accessRecord");
                    final AccessRecordBp face = gson.fromJson(postdata, AccessRecordBp.class);
                    if (face != null) {

                        String faceid = face.faceId;
                        queryface(faceid);
//                        MainActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Bitmap bitmap = base64ToBitmap(face.image);
//                                imageView2.setImageBitmap(bitmap);
//                                imageView2.invalidate();
//                                bitmap = null;
//                            }
//                        });
                    }
                }
            }
        });

        server.listen(5000);

        String netUrl = "http://192.168.0.90/linmu/server/url";
        String url = "http://" + host + ":5000/api/postface";

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("url", url);
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(netUrl).post(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String body = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(MainActivity.this, body, Toast.LENGTH_SHORT).show();
                    }
                });
                String debug = "";
            }
        });

        read();
    }

    private void read() {

        try {
            InputStream inputStream = getAssets().open("pic.txt");
            String base64 = getString(inputStream);
            inputStream.close();

            Bitmap bitmap = base64ToBitmap(base64);
            ivtest.setImageBitmap(bitmap);
            ivtest.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    void queryface(String faceid) {

        if (TextUtils.isEmpty(faceid)) {
            return;
        }

        String netUrl = "http://192.168.0.90/person/query";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("faceId", faceid);
        FormBody formBody = builder.build();

        Request request = new Request.Builder().url(netUrl).post(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String body = response.body().string();
                Gson gson = new Gson();
                final HXFaceBean bean = gson.fromJson(body, HXFaceBean.class);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, bean.data.username, Toast.LENGTH_SHORT).show();
                    }
                });
                String debug = "";
            }
        });
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        try {
            int pos = base64Data.indexOf(",");
            if (pos > -1) {
                base64Data = base64Data.substring(pos + 1);
            }
            byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } catch (Exception ex) {
            String error = ex.getMessage();
            ex.printStackTrace();
            return null;
        }
    }
}
