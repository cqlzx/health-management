package com.along.android.healthmanagement.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.along.android.healthmanagement.activities.AlertDialogActivity;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.Calendar;


public class AlarmReceiver extends BroadcastReceiver {

    private final String PRESCRIPTION_ID = "PRESCRIPTION_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("receiver", "!!!!!!");
        int pid = Integer.parseInt(intent.getStringExtra(PRESCRIPTION_ID));
        Prescription prescription = EntityManager.findById(Prescription.class, pid);

        if (Long.parseLong(prescription.getEndDate()) < Calendar.getInstance().getTimeInMillis()) {
            PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
        } else {
            Intent i = AlertDialogActivity.newIntent(context, intent.getStringExtra(PRESCRIPTION_ID));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
