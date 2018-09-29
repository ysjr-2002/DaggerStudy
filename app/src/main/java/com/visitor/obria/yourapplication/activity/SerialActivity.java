package com.visitor.obria.yourapplication.activity;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.visitor.obria.yourapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tw.com.prolific.driver.pl2303.PL2303Driver;

public class SerialActivity extends AppCompatActivity {

    private static final String ACTION_USB_PERMISSION = "com.visitor.obria.yourapplication.USB_PERMISSION";
    @BindView(R.id.et_log)
    TextView etLog;
    private PL2303Driver mSerial = null;

    private static final String connect_error = "设备连接失败";

    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);
        ButterKnife.bind(this);
    }

    private void myToast(String msg) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(Calendar.getInstance().getTime());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        text = etLog.getText().toString();
        text += time + ":" + msg + "\n";
        etLog.setText(text);
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
        }

        if (!mSerial.enumerate()) {
            //Found a PL2303HXD device , the default VID_PID is 067B_2303
            myToast("查找失败");
            return;
        }
    }

    private void open() {

        boolean bInit = mSerial.InitByBaudRate(PL2303Driver.BaudRate.B9600, 700);
        if (!bInit) {
            myToast("设备打开失败");
            return;
        }

        if (!mSerial.PL2303Device_IsHasPermission()) {
            myToast("没有权限");
            return;
        }

        if (!mSerial.PL2303Device_IsSupportChip()) {
            myToast("不支持芯片");
            return;
        }
    }

    private void write() {

        if (mSerial.isConnected()) {
            byte[] data = "123".getBytes();
            int len = mSerial.write(data, data.length);
            if (len == data.length) {
                myToast("输出成功");
            } else {
                myToast("输出失败");
            }
        } else {

            myToast(connect_error);
        }
    }

    private void read() {

        if (mSerial.isConnected()) {
            byte[] data = new byte[1024];
            int len = mSerial.read(data);
            if (len == 0) {
                myToast("read empty");
            }
        } else {
            myToast(connect_error);
        }
    }

    private void getSerialnumber() {

        if (mSerial.isConnected() == false) {
            myToast("未连接");
            return;
        }

        String number = mSerial.PL2303Device_GetSerialNumber();
        myToast(String.format("序列号: %s", number));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSerial != null) {
            mSerial.end();
            mSerial = null;
        }
    }
}
