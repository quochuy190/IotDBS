package com.vbeeon.iotdbs.presentation.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.vbeeon.iotdbs.R;
import com.vbeeon.iotdbs.presentation.base.BaseActivity;
import com.vbeeon.iotdbs.utils.widget.barcodescan.DecoratedScanView;

import java.util.ArrayList;
import java.util.List;

public class ScanDeviceActivity extends BaseActivity implements DecoratedScanView.ResultHandler {

    private DecoratedScanView mScannerView;

    public static Intent newIntent(Context context) {
        return new Intent(context, ScanDeviceActivity.class);
    }
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    public static final String DATA = "DATA";

    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlash = false;
        mAutoFocus = true;
        mSelectedIndices = null;
        mCameraId = -1;
        mScannerView = findViewById(R.id.zxing_barcode_scanner);
        setupFormats();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Intent intent= new Intent();
            intent.putExtra(DATA, rawResult.toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        } catch (Exception ex){
            showDialogError();
        }
    }

    private void showDialogError() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(R.string.title_permission);
        alertBuilder.setMessage(R.string.message_error_permission);
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mScannerView.startCamera(mCameraId);
                        mScannerView.setFlash(mFlash);
                        mScannerView.setAutoFocus(mAutoFocus);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void handleResult(MediaBrowserServiceCompat.Result rawResult) {

    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < DecoratedScanView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(DecoratedScanView.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_scan_device;
    }

    @Override
    protected void setupView(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }
}

