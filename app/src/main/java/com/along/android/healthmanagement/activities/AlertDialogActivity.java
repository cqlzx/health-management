package com.along.android.healthmanagement.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.NotificationResponse;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.MailHelper;

import java.util.Calendar;


public class AlertDialogActivity extends Activity {

    private static final String PRESCRIPTION_ID = "PRESCRIPTION_ID";
    private long uid, pid;
    private Ringtone r;
    private boolean isTaken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("dialog", "!!!!!");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setNotificationResponse(false);
                r.stop();
                finish();
                if (!isTaken) {
                    sendContactEmail();
                }

            }
        }, 5 * 60000);


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        uid = sp.getLong("uid", 0);
        pid = Integer.parseInt(getIntent().getStringExtra(PRESCRIPTION_ID));

        final Prescription prescription = EntityManager.findById(Prescription.class, pid);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Health Management ")
                .setMessage("It's time to take the medicine : " + prescription.getMedication())
                .setCancelable(false)
                .setPositiveButton("Take them!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

//                        setNotificationResponse(true);

                        //This is only for the demo to show that we have stored the response of the user and the corresponding uid and pid
                        Toast.makeText(AlertDialogActivity.this, "Medicine is taken! Uid = " + uid + ", pid = " + pid, Toast.LENGTH_SHORT).show();
                        isTaken = true;
                        r.stop();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        setNotificationResponse(false);

                        //This is only for the demo to show that we have stored the response of the user and the corresponding uid and pid
                        Toast.makeText(AlertDialogActivity.this, "Medicine is NOT taken! Uid = " + uid + ", pid = " + pid, Toast.LENGTH_SHORT).show();

                        r.stop();
                        finish();
                        sendContactEmail();
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

    private void setNotificationResponse(boolean response) {

        NotificationResponse nr = new NotificationResponse();

        nr.setUserId((long)uid);
        nr.setPrescriptionId((long)pid);
        nr.setNotificationResponse(response);
        nr.setTime(Calendar.getInstance().getTimeInMillis());

        nr.save();

    }

    private void sendContactEmail(){
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        User user = EntityManager.findById(User.class, userIdS);
        String email = user.getEmail();
        String subject = "HealthManagement Support";
        String content = makeEmailContent(user);
        new MailHelper().execute(email, subject, content);

    }

    private String makeEmailContent(User user) {
        return "Dear " + user.getRealname() + "," +
                "<br /><br />" + user.getRealname() + " didn`t take the medicine, tell him/her!";
    }
}
