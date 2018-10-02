package com.visitor.obria.yourapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pa.door.myusbserial.driver.UsbSerialDriver;
import com.pa.door.myusbserial.driver.UsbSerialPort;
import com.pa.door.myusbserial.driver.UsbSerialProber;
import com.pa.door.myusbserial.util.HexDump;
import com.pa.door.myusbserial.util.SerialInputOutputManager;
import com.visitor.obria.yourapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyUsbSerialActivity extends AppCompatActivity {

    private static final String TAG = "kaka";
    @BindView(R.id.consoleText)
    TextView consoleText;
    @BindView(R.id.demoScroller)
    ScrollView demoScroller;
    @BindView(R.id.et_data)
    EditText etData;

    private UsbManager mUsbManager;
    private SerialInputOutputManager mSerialIoManager = null;
    private List<UsbSerialPort> mPorts = null;

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_usb_serial);
        ButterKnife.bind(this);

        byte val = 10;
        StringBuilder sb = new StringBuilder();
        int a = (val >>> 4) & 0x0F;
        int b = val & 0x0F;
        char c1 = HEX_DIGITS[a];
        char c2 = HEX_DIGITS[b];
        sb.append(c1);
        sb.append(c2);
        String str = sb.toString();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        this.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() == UsbManager.ACTION_USB_DEVICE_ATTACHED) {

                find();
                open(mPorts.get(0));
            }

            if (intent.getAction() == UsbManager.ACTION_USB_DEVICE_DETACHED) {

                stopIoManager();
            }
        }
    };

    @OnClick({R.id.btn_find, R.id.btn_open, R.id.btn_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_find:
                find();
                break;
            case R.id.btn_open:
                open(mPorts.get(0));
                break;
            case R.id.btn_write:
                write();
                break;
        }
    }

    private void write() {

        String cardno = etData.getText().toString();
        mSerialIoManager.writeAsync(cardno.getBytes());
    }

    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d(TAG, "Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    MyUsbSerialActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyUsbSerialActivity.this.updateReceivedData(data);
                        }
                    });
                }
            };

    private void find() {

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> drivers = UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);
        mPorts = new ArrayList<>();
        for (UsbSerialDriver driver :
                drivers) {

            if (driver.getDevice().getProductId() == 8963) {
                List<UsbSerialPort> ports = driver.getPorts();
                mPorts.addAll(ports);
            }
        }
    }

    private void open(UsbSerialPort port) {

        UsbDeviceConnection connection = mUsbManager.openDevice(port.getDriver().getDevice());
        try {
            port.open(connection);
            port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            startIoManager(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startIoManager(UsbSerialPort port) {
        if (port != null) {
            Log.i(TAG, "Starting io manager ..");
            mSerialIoManager = new SerialInputOutputManager(port, mListener);
            mExecutor.submit(mSerialIoManager);
        }
    }

    private void stopIoManager() {
        if (mSerialIoManager != null) {
            Log.i(TAG, "Stopping io manager ..");
            mSerialIoManager.stop();
            mSerialIoManager = null;
        }
    }

    int len = 0;
    //List<byte> buffers = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    StringBuffer ss;


    // 00 3D 7E AF
    // 00 91 B0 3D
    private void updateReceivedData(byte[] data) {

//        final String message = "Read " + data.length + " bytes: \n"
//                + HexDump.dumpHexString(data) + "\n\n";
        len += data.length;
        for (byte b :
                data) {
            sb.append(HexDump.toHexString(b));
        }

        if (len == 4) {
            demoScroller.smoothScrollTo(0, consoleText.getBottom());
            len = 0;
            byte[] bytes = HexDump.hexStringToByteArray(sb.toString());
            int cardno = byteArrayToInt(bytes);
            //0 补0
            //10 位
            String str = String.format("%010d", cardno);
            String message = "byte->" + sb.toString() + " " + str + "\n";
            consoleText.append(message);

            sb.delete(0, sb.toString().length());
        }
    }

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    private final static char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
}
