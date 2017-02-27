package com.along.android.healthmanagement.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.helpers.EntityManager;

public class AlertDialogActivity extends Activity {

    private final String COLOR_PRIMARY_DARK = "#388E3C";
    private static final String PRESCRIPTION_ID = "PRESCRIPTION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("dialog", "!!!!!");

        int pid = Integer.parseInt(getIntent().getStringExtra(PRESCRIPTION_ID));
        Prescription prescription = EntityManager.findById(Prescription.class, pid);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder
                .setTitle("Health Management ")
                .setMessage("It's time to take the medicine" + prescription.getMedication())
                .setCancelable(false)
                .setPositiveButton("Take them!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static Intent newIntent(Context context, String extra) {
        Intent i = new Intent(context, AlertDialogActivity.class);
        i.putExtra(PRESCRIPTION_ID, extra);
        return i;
    }
}
