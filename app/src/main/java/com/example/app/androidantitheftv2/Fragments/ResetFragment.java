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
import static com.example.app.androidantitheftv2.Fragments.MainFragment.ACTIVATION_REQUEST;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetFragment extends Fragment implements View.OnClickListener {

    Button button_reset_device;
    DevicePolicyManager devicePolicyManager;
    ComponentName demoDeviceAdmin;

    public ResetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reset, container, false);

        button_reset_device = (Button) v.findViewById(R.id.button_reset_device);
        button_reset_device.setOnClickListener(this);

        // Initialize Device Policy Manager service and our receiver class
        devicePolicyManager = (DevicePolicyManager) super.getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(super.getActivity(), DeviceAdminReceiverActivity.class);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button_reset_device:
        // We reset the device - this will erase entire /data partition!
        Toast.makeText(super.getActivity(), "MASTER RESET ACTIVATED", Toast.LENGTH_LONG).show();
        Log.d(TAG,
                "RESETTING device now - all user data will be ERASED to factory settings");
        devicePolicyManager.wipeData(ACTIVATION_REQUEST);
        break;
    }
    }
}
