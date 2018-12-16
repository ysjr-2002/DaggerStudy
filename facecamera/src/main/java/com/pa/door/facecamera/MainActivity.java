package com.pa.door.facecamera;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ice_ipcsdk.SDK;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etPwd)
    EditText editText2;
    @BindView(R.id.etName)
    EditText editText1;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.etSN)
    EditText etSN;

    private final static String LOG_TAG = "sdkTest";
    private final static String[] OPEN_RET = new String[]{"sdk连接失败", "连接成功", "端口少于5个", "账号验证失败", "p2p启动失败"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private SDK mSDK;

    @OnClick({R.id.button1, R.id.button2})
    public void onViewClicked(View view) {

        if (view.getId() == R.id.button1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mSDK == null) {
                        mSDK = new SDK();
                    }

                    String name = editText1.getText().toString();
                    String pwd = editText2.getText().toString();
                    String sn = etSN.getText().toString();

                    try {
                        int nRet = mSDK.ICE_IPCSDK_Open_P2P(name, pwd, sn, new MyPlateCallback());
                        Log.i(LOG_TAG, "SN" + sn + "open" + nRet);

                        Looper.prepare();

                        String error = OPEN_RET[nRet];
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } catch (Exception ex) {
                        String error = ex.getMessage();
                        ex.printStackTrace();
                    }
                }
            }).start();
        }


        if (view.getId() == R.id.button2) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "you is shit", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }).start();
        }
    }


    class MyPlateCallback implements SDK.IPlateCallback {

        @Override
        public void ICE_IPCSDK_Plate(String s, String s1, String s2, byte[] bytes, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, float v, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15) {

        }
    }
}
