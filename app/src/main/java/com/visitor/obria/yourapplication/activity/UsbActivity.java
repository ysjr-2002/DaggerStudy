package com.visitor.obria.yourapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.visitor.obria.yourapplication.R;

import java.util.Map;

public class UsbActivity extends AppCompatActivity {

    private static final String TAG = "kaka";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);

        registerReceiver(mBroadcastReceiver, intentFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
                String a = "";
            }

            if (intent.getAction() == UsbManager.ACTION_USB_DEVICE_DETACHED) {

                String a = "";
            }
        }
    };

    private void test() {
        UsbManager usbManager = (UsbManager) getSystemService(USB_SERVICE);
        Map<String, UsbDevice> maps = usbManager.getDeviceList();
        for (Map.Entry<String, UsbDevice> item :
                maps.entrySet()) {

            Log.d(TAG, item.getKey());
            Log.d(TAG, "vernderid->" + item.getValue().getVendorId() + " product->" + item.getValue().getProductId());

            if (item.getValue().getProductId() == 8963) {
                String no = item.getValue().getSerialNumber();

                if (usbManager.hasPermission(item.getValue())) {

                    String ok = "";
                }


                UsbEndpoint mUsbEndpointIn = null;
                UsbEndpoint mUsbEndpointOut = null;

                UsbDevice device = item.getValue();
                UsbDeviceConnection connection = usbManager.openDevice(item.getValue());

                int jj = connection.getFileDescriptor();

                UsbInterface usbInterface = device.getInterface(0);

                for (int index = 0; index < usbInterface.getEndpointCount(); index++) {
                    UsbEndpoint point = usbInterface.getEndpoint(index);
                    if (point.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                        if (point.getDirection() == UsbConstants.USB_DIR_IN) {
                            mUsbEndpointIn = point;
                        } else if (point.getDirection() == UsbConstants.USB_DIR_OUT) {
                            mUsbEndpointOut = point;
                        }
                    }
                }
            }
        }
    }
}
