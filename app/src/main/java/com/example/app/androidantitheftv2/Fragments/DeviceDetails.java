package com.example.app.androidantitheftv2.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.androidantitheftv2.R;


public class DeviceDetails extends Fragment {

    public DeviceDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin, container, false);
        TextView textView = (TextView)v.findViewById(R.id.manufacturer);
        textView.setText("Device: " + Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);

        TextView textView2 = (TextView)v.findViewById(R.id.sdk_level);
        textView2.setText("SDK Version: " + Build.VERSION.SDK);

        TextView textView3 = (TextView)v.findViewById(R.id.display);
        textView3.setText("Build Number: " + Build.DISPLAY);

        TextView textView4 = (TextView)v.findViewById(R.id.serial);
        textView4.setText("Hardware Serial Number: " + Build.SERIAL);
        return v;
    }
}
