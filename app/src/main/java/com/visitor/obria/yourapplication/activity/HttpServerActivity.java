package com.visitor.obria.yourapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.async.http.body.UrlEncodedFormBody;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.bean.AccessRecordBp;
import com.visitor.obria.yourapplication.bean.HXFaceBean;
import com.visitor.obria.yourapplication.bean.StudenBean;
import com.visitor.obria.yourapplication.util.IPUtil;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpServerActivity extends AppCompatActivity {

    @BindView(R.id.imageView2)
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_server);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String ip = IPUtil.getIP(this);
        Toast.makeText(this, ip, Toast.LENGTH_SHORT).show();

        AsyncHttpServer server = new AsyncHttpServer();

        server.get("/shit", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {

                String name = request.getQuery().getString("name");
                response.send("Hello from android->" + name);
            }
        });

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
                        HttpServerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = base64ToBitmap(face.image);
                                imageView2.setImageBitmap(bitmap);
                                imageView2.invalidate();
                                bitmap = null;
                            }
                        });


                    }
                    String debug = "";
                }
                if (body instanceof JSONObjectBody) {
                    JSONObjectBody jsonObjectBody = (JSONObjectBody) body;
                    String json = jsonObjectBody.get().toString();
//                    json = json.substring(13);
                    final AccessRecordBp face = gson.fromJson(json, AccessRecordBp.class);
                    if (face != null) {

                        String faceid = face.faceId;
                        HttpServerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = base64ToBitmap(face.image);
                                imageView2.setImageBitmap(bitmap);
                                imageView2.invalidate();
                                bitmap = null;
                            }
                        });
                    }
                }
                StudenBean bean = new StudenBean();
                bean.setName("are you ok");
                String input = gson.toJson(bean);
                response.send("application/json", input);
            }
        });

        server.post("/api/postform", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {

                String contenttype = request.getBody().getContentType();
                Multimap multimap = request.getHeaders().getMultiMap();

                Object body = request.getBody();
                AsyncHttpRequestBody<Multimap> requestBody = (AsyncHttpRequestBody<Multimap>) body;
                if (requestBody != null) {

                    Multimap param = requestBody.get();
                    if (param != null) {

                        String name = param.getString("username");
                        String pwd = param.getString("password");
                    }
                }

                response.send("are you ok");
            }
        });

        server.listen(5000);


        String netUrl = "http://192.168.0.90/linmu/server/url";
        String url = "http://192.168.0.13:5000/api/postface";

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

                String body = response.body().string();
                String debug = "";
            }
        });
    }

    void queryface(String faceid) {
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
                HttpServerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HttpServerActivity.this, bean.data.username, Toast.LENGTH_SHORT).show();
                    }
                });
                String debug = "";
            }
        });
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        try {
//            if (base64Data.startsWith("data:image/jpg;base64,") == false) {
//                base64Data = "data:image/jpg;base64,"+base64Data;
//            }
//            base64Data = base64Data.substring(4);
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
