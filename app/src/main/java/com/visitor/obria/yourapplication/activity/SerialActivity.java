package com.visitor.obria.yourapplication.activity;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.cameraEx.CameraEx;

import java.lang.reflect.Array;

import butterknife.ButterKnife;
import butterknife.OnClick;
import tw.com.prolific.driver.pl2303.PL2303Driver;

public class SerialActivity extends AppCompatActivity {

    private static final String ACTION_USB_PERMISSION = "com.prolific.pl2303hxdsimpletest.USB_PERMISSION";
    private PL2303Driver mDriver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);
        ButterKnife.bind(this);
    }

    private void myToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_open, R.id.btn_get_serialnumber, R.id.btn_read, R.id.btn_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

    private void write() {
        byte[] data = "123".getBytes();
        int len = mDriver.write(data, data.length);
        if (len == data.length) {
            myToast("输出成功");
        } else {
            myToast("输出失败");
        }
    }

    private void read() {

        byte[] data = new byte[1024];
        int len = mDriver.read(data);
    }

    private void getSerialnumber() {

        if (mDriver.isConnected() == false) {

            myToast("未连接");
            return;
        }

        String number = mDriver.PL2303Device_GetSerialNumber();
        myToast(String.format("序列号: %s", number));
    }

    private void open() {

        UsbManager usbManager = null;
        Object temp = getSystemService(Context.USB_SERVICE);
        if (temp instanceof UsbManager) {
            usbManager = (UsbManager) temp;
        }
        mDriver = new PL2303Driver(usbManager, this, ACTION_USB_PERMISSION);
        if (!mDriver.PL2303USBFeatureSupported()) {
            myToast("不支持");
            return;
        }

        boolean bInit = mDriver.InitByBaudRate(PL2303Driver.BaudRate.B9600, 700);
        if (!bInit) {
            myToast("初始化失败");
            return;
        }
    }
}
