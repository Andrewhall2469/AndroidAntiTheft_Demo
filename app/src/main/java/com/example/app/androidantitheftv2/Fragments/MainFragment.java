package com.example.app.androidantitheftv2.Fragments;


import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.app.androidantitheftv2.DeviceAdminReceiverActivity;
import com.example.app.androidantitheftv2.R;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    static final int ACTIVATION_REQUEST = 47; // identifies our request id
    private DevicePolicyManager devicePolicyManager;
    private ComponentName demoDeviceAdmin;
    private static final int ADMIN_INTENT = 1;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize Device Policy Manager service and our receiver class
        devicePolicyManager = (DevicePolicyManager) super.getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(super.getActivity(), DeviceAdminReceiverActivity.class);

        final ToggleButton toggleButton = (ToggleButton) v
                .findViewById(R.id.toggle_device_admin);
        if (devicePolicyManager != null && devicePolicyManager.isAdminActive(demoDeviceAdmin)) {
            toggleButton.setChecked(true);
        }
        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, demoDeviceAdmin);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Please read the following: ");
                    startActivityForResult(intent, ADMIN_INTENT);
                } else {
                    devicePolicyManager.removeActiveAdmin(demoDeviceAdmin);
                }
            }
        });

        return v;
    }

    /**
     * Called when startActivityForResult() call is completed. The result of
     * activation could be success of failure, mostly depending on user okaying
     * this app's request to administer the device.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ADMIN_INTENT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(super.getContext(), "Registered as Admin",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

}
