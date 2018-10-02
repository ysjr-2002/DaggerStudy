package com.visitor.obria.yourapplication.activity;

import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.bean.HSFaceBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.chrono.MinguoChronology;
import java.util.Calendar;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tw.com.prolific.driver.pl2303.PL2303Driver;

public class SerialActivity extends AppCompatActivity {

    private static final String ACTION_USB_PERMISSION = "com.visitor.obria.yourapplication.USB_PERMISSION";
    @BindView(R.id.et_log)
    TextView etLog;
    private PL2303Driver mSerial = null;

    final static String TAG = "kaka";
    private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B9600;

    private static final String connect_error = "设备连接失败";

    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);
        ButterKnife.bind(this);

        //hsRemoteTest();
    }

    @OnClick({R.id.btn_find, R.id.btn_open, R.id.btn_get_serialnumber, R.id.btn_read, R.id.btn_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_find:
                find();
                break;
            case R.id.btn_open:
                open();
                break;
            case R.id.btn_get_serialnumber:
                getSerialnumber();
                break;
            case R.id.btn_read:
                read();
                break;
            case R.id.btn_write:
                write();
                break;
        }
    }

    private void find() {
        UsbManager usbManager = null;
        Object temp = getSystemService(Context.USB_SERVICE);
        if (temp instanceof UsbManager) {
            usbManager = (UsbManager) temp;
        }
        mSerial = new PL2303Driver(usbManager, this, ACTION_USB_PERMISSION);

        if (!mSerial.PL2303USBFeatureSupported()) {
            myToast("不支持");
            return;
        } else {
            myToast("PL2303USBFeatureSupported is ok");
        }

        if (!mSerial.enumerate()) {
            //Found a PL2303HXD device , the default VID_PID is 067B_2303
            myToast("查找失败");
        } else {
            myToast("enumerate is ok");
        }
    }

    private void open() {
        boolean bInit = false;
        bInit = mSerial.isConnected();
        if (bInit) {
            mBaudrate = PL2303Driver.BaudRate.B9600;
            bInit = mSerial.InitByBaudRate(mBaudrate, 1500);
            if (!bInit) {
                myToast("设备打开成功");
            } else {
                myToast("设备打开失败");
            }

            if (!mSerial.PL2303Device_IsHasPermission()) {
                myToast("没有权限");
            } else {
                myToast("已授权");
            }

            if (!mSerial.PL2303Device_IsSupportChip()) {
                myToast("不支持芯片");
            }
        }
    }

    private void write() {

        if (mSerial.isConnected()) {
            byte[] data = "123".getBytes();
            int len = mSerial.write(data, data.length);
            if (len == data.length) {
                myToast("输出成功");

                byte[] receive = new byte[16];
                len = mSerial.read(receive);
                if (len > 0) {
                    myToast("测试成功");
                } else {
                    myToast("测试失败");
                }
            } else {
                myToast("输出失败");
            }
        } else {

            myToast(connect_error);
        }
    }

    private void read() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (mSerial.isConnected()) {
                        byte[] data = new byte[4];
                        int len = mSerial.read(data);
                        if (len == 0) {
                            //myToast("read empty");
                            Log.d(TAG, "emtpy");
                        } else {
                            Log.d(TAG, "ok");
                        }
                        if (data[2] > 0) {
                            String shit = "";
                        }
                    } else {
                        //myToast(connect_error);
                        Log.d(TAG, "error");
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void getSerialnumber() {

        if (mSerial.isConnected() == false) {
            myToast("未连接");
            return;
        }

        String number = mSerial.PL2303Device_GetSerialNumber();
        myToast(String.format("序列号: %s", number));
    }

    private void hsRemoteTest() {

        Gson gson = new Gson();
        HSFaceBean bean = new HSFaceBean(1, "ysj", "123", "remark");
        String json = gson.toJson(bean);

        String url = "http://192.168.3.54:10001/api/wg/check/";
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        final Request request = new Request.Builder().url(url).post(requestBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful() && response.code() == 200) {

                    final String content = response.body().string();

                    SerialActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            myToast(content);
                        }
                    });
                }
            }
        });
    }

    private void myToast(String msg) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(Calendar.getInstance().getTime());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        text = etLog.getText().toString();
        text += time + ":" + msg + "\n";
        etLog.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mSerial != null) {
//            mSerial.end();
//            mSerial = null;
//        }
    }
}
