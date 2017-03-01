package com.example.app.androidantitheftv2.Fragments;


import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.app.androidantitheftv2.DeviceAdminReceiverActivity;
import com.example.app.androidantitheftv2.R;

import static android.content.ContentValues.TAG;


public class LockFragment extends Fragment implements View.OnClickListener {

    Button button_lock_device;
    DevicePolicyManager devicePolicyManager;
    ComponentName demoDeviceAdmin;

    public LockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lock, container, false);
        button_lock_device = (Button) v.findViewById(R.id.button_lock_device);
        button_lock_device.setOnClickListener(this);


        devicePolicyManager = (DevicePolicyManager) super.getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(super.getActivity(), DeviceAdminReceiverActivity.class);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_lock_device:
                //We lock the screen
                Toast.makeText(super.getActivity(), "Locking device...", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Locking device now");
                devicePolicyManager.lockNow();
                break;
        }
    }
}
