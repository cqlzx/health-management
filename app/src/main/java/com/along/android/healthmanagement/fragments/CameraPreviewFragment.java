package com.along.android.healthmanagement.fragments;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.along.android.healthmanagement.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by RitenVithlani on 4/8/17.
 */

public class CameraPreviewFragment extends AbstractRuntimePermission implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_PERMISSION = 10;

    private OnBarcodeDetectedListener barcodeDetectListener;
    private ZXingScannerView mScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_camera_preview, container, false);
        mScannerView = new ZXingScannerView(getActivity());
        mScannerView.setResultHandler(this);

        requestAppPermissions(new String[]{Manifest.permission.CAMERA}, R.string.camera_permission_msg, REQUEST_PERMISSION);

        return mScannerView;
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        mScannerView.startCamera();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            barcodeDetectListener = (OnBarcodeDetectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBarcodeDetectedListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.i("Barcode: ", result.getText());
        Log.i("Barcode Format: ", result.getBarcodeFormat().name());
        barcodeDetectListener.onBarcoodeDetected(result.getText());
    }

    public interface OnBarcodeDetectedListener {
        public void onBarcoodeDetected(String detectedBarcode);
    }
}
