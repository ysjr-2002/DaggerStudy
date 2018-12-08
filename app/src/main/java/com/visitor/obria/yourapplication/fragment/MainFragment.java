package com.visitor.obria.yourapplication.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.visitor.obria.yourapplication.R;


/**
 * created by yangshaojie  on 2018/12/8
 * email: ysjr-2002@163.com
 */
public class MainFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button mButton;

    Activity mActivity;

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, null);
        mButton = view.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if (getView().getId() == R.id.button) {

            Toast.makeText(mActivity, "are you ok?", Toast.LENGTH_SHORT).show();
        }
    }
}
