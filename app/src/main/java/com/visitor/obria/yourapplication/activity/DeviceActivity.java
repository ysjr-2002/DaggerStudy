package com.visitor.obria.yourapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.util.DeviceUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.visitor.obria.yourapplication.util.DeviceUtil.getPathInputStream;
import static com.visitor.obria.yourapplication.util.DeviceUtil.writeSysFileValue;

public class DeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_open, R.id.btn_light})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                openDoor();
                break;
            case R.id.btn_light:
                timer();
                break;
        }
    }

    //门磁
    private static final String DOOR_RELAY_PATH = "/sys/class/gpio/gpio169/value";
    //光感
    private static final String LIGHT_PATH = "/sys/class/gpio/gpio199/value";
    boolean bOpen = true;

    private void openDoor() {
        if (bOpen) {
            writeSysFileValue(DOOR_RELAY_PATH, "1");
            bOpen = false;
        } else {
            writeSysFileValue(DOOR_RELAY_PATH, "0");
            bOpen = true;
        }
    }

    Timer mTimer = null;

    private void timer() {

        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 1000, 1000);
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            String value = getPathInputStream(LIGHT_PATH);
            if (TextUtils.equals(value, "1")) {
                Log.d("tata", "有人");
            } else {
                Log.d("tata", "无人");
            }
        }
    };
}
