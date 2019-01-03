package com.pa.door.facetv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.etwelcome)
    EditText etwelcome;
    @BindView(R.id.etcamera)
    EditText etcamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String welcome = AppTV.getSharedInstance().getWelcome();
        String camera = AppTV.getSharedInstance().getCamera();

        etwelcome.setText(welcome);
        etcamera.setText(camera);
    }

    @OnClick({R.id.btnsave})
    public void onViewClicked(View view) {

        String welcome = etwelcome.getText().toString().trim();
        String camera = etcamera.getText().toString().trim();

        AppTV.getSharedInstance().saveWelcome(welcome);
        AppTV.getSharedInstance().saveCamera(camera);

        this.finish();
    }
}
