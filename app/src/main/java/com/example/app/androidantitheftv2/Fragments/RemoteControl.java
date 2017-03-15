package com.example.app.androidantitheftv2.Fragments;


import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.androidantitheftv2.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;

import static android.app.Activity.RESULT_OK;

public class RemoteControl extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    protected static final int REQUEST_CODE_RESOLUTION = 1;
    private static final String TAG = "BaseDriveActivity";
    Button driveButton;
    private TextView mLogTextView;
    DriveId Id;
    public RemoteControl() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_remote_control, container, false);

        driveButton = (Button) v.findViewById(R.id.driveButton);
        driveButton.setOnClickListener(this);
        mLogTextView = (TextView) v.findViewById(R.id.textViewLog);
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(super.getActivity())
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addScope(Drive.SCOPE_APPFOLDER) // required for App Folder sample
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RESOLUTION && resultCode == RESULT_OK) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "GoogleApiClient connected");
    }



    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "GoogleApiClient connection suspended");
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(super.getActivity(), result.getErrorCode(), 0).show();
            return;
        }
        try {
            result.startResolutionForResult(super.getActivity(), REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }
    public void showMessage(String message) {
        Toast.makeText(super.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onClick(View v) {
        Drive.DriveApi.newDriveContents(getGoogleApiClient())
                .setResultCallback(driveContentsCallback);
    }
    // [START drive_contents_callback]
    final private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create new file contents");
                        return;
                    }

                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle("newfile.txt")
                            .setMimeType("text/plain")
                            .build();
                    Drive.DriveApi.getAppFolder(getGoogleApiClient())
                            .createFile(getGoogleApiClient(), changeSet, result.getDriveContents())
                            .setResultCallback(fileCallback);
                }
            };
    // [END drive_contents_callback]

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the file");
                        return;
                    }
                    Log.d(TAG, "DriveId: " + result.getDriveFile().getDriveId());
                    showMessage("Created a file in App Folder: "
                            + result.getDriveFile().getDriveId());
                    Id = result.getDriveFile().getDriveId();
                    DriveFile file = Id.asDriveFile();
                            file.addChangeListener(getGoogleApiClient(), changeListener);

                }
            };

    final private ChangeListener changeListener = new ChangeListener() {
        @Override
        public void onChange(ChangeEvent event) {
            mLogTextView.setText(String.format("File change event: %s", event));
            showMessage("AppDataFile Changed.....");
        }
    };


}

