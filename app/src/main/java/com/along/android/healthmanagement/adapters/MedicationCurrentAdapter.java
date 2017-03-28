package com.along.android.healthmanagement.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.receivers.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicationCurrentAdapter extends ArrayAdapter<Prescription> {

    private final String COLOR_PRIMARY_DARK = "#388E3C";
    private final String DEFAULT_TEXT_COLOR = "#808080";
    private final String PRESCRIPTION_ID = "PRESCRIPTION_ID";
    private ImageView ivNotificationIcon;
    private ImageView ivNotificationIconActive;
    private TextView tvNotification;

    private static List<Intent> alarmIntents;

    public MedicationCurrentAdapter(Context context, List<Prescription> prescriptions) {
        super(context, 0, prescriptions);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_medication_current, parent, false);
        }

        final Prescription prescription = getItem(position);

        TextView prescriptionId = (TextView) listItemView.findViewById(R.id.tvMLPrescriptionId);
        prescriptionId.setText(prescription.getId().toString());

        TextView medicines = (TextView) listItemView.findViewById(R.id.tvMLMedicationNames);
        medicines.setText(null != prescription.getMedication() ? prescription.getMedication() : "");

        TextView doctorName = (TextView) listItemView.findViewById(R.id.tvMLDoctorName);
        String doctorText = "Prescribed by: " + (null != prescription.getDoctorName() ? prescription.getDoctorName() : "");
        doctorName.setText(doctorText);

        TextView disease = (TextView) listItemView.findViewById(R.id.tvMLDisease);
        String diseaseText = "for " + (null != prescription.getDisease() ? prescription.getDisease() : "");
        disease.setText(diseaseText);

        ivNotificationIcon = (ImageView) listItemView.findViewById(R.id.ivMLNotificationIcon);
        ivNotificationIconActive = (ImageView) listItemView.findViewById(R.id.ivMLNotificationIconActive);
        tvNotification = (TextView) listItemView.findViewById(R.id.tvMLNotification);

        if(prescription.getNotificationEnabled()) {
            showActiveNotificationButton();
        } else {
            hideActiveNotificationButton();
        }

        LinearLayout llMLNotification = (LinearLayout) listItemView.findViewById(R.id.llMLNotification);
        llMLNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View llMLNotificationClickedView) {
                toggleNotification(llMLNotificationClickedView, prescription);
            }
        });

        LinearLayout llMLDelete = (LinearLayout) listItemView.findViewById(R.id.llMLDelete);
        llMLDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(prescription);
            }
        });

        return listItemView;
    }

    private void showDeleteAlertDialog(final Prescription prescription) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirm Delete");
        alert.setMessage("Are you sure to delete this medication entry?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* delete the medication entry from the database  */
                Prescription prescriptionRecord = Prescription.findById(Prescription.class, prescription.getId());

                //Must setAlarm before unsetAlarm, because intents will be gone after completely quiting the application,
                //leading to the result that getAlarmIntents will contain a list of null, that's the reason of crash
                setAlarm(getContext(), prescription);
                unsetAlarm(getContext(), prescription);

                List<Intent> alarms = getAlarmIntents();
                alarms.remove(getPosition(prescription));

                prescriptionRecord.delete();
                MedicationCurrentAdapter.this.remove(prescription);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void toggleNotification(View llMLNotificationClickedView, Prescription prescription) {
        ivNotificationIcon = (ImageView) llMLNotificationClickedView.findViewById(R.id.ivMLNotificationIcon);
        ivNotificationIconActive = (ImageView) llMLNotificationClickedView.findViewById(R.id.ivMLNotificationIconActive);
        tvNotification = (TextView) llMLNotificationClickedView.findViewById(R.id.tvMLNotification);

        Prescription prescriptionRecord = Prescription.findById(Prescription.class, prescription.getId());

        if(ivNotificationIcon.getVisibility() == View.VISIBLE) {
            showActiveNotificationButton();

            /*  Set the notification alarm and save to database */
            prescription.setNotificationEnabled(true);
            prescription.save();

            setAlarm(getContext(), prescription);
        } else {
            hideActiveNotificationButton();

            /* Unset the notification alarm and save to database */
            prescription.setNotificationEnabled(false);
            prescription.save();

            //Must setAlarm before unsetAlarm, because intents will be gone after completely quiting the application,
            //leading to the result that getAlarmIntents will contain a list of null, that's the reason of crash
            setAlarm(getContext(), prescription);
            unsetAlarm(getContext(), prescription);
        }
    }

    private void showActiveNotificationButton() {
        ivNotificationIcon.setVisibility(View.GONE);
        ivNotificationIconActive.setVisibility(View.VISIBLE);
        tvNotification.setTextColor(Color.parseColor(COLOR_PRIMARY_DARK));
        tvNotification.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void hideActiveNotificationButton() {
        ivNotificationIcon.setVisibility(View.VISIBLE);
        ivNotificationIconActive.setVisibility(View.GONE);
        tvNotification.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
        tvNotification.setTypeface(Typeface.DEFAULT);
    }

    private List<Intent> getAlarmIntents(){
        if (alarmIntents == null) {
            alarmIntents = new ArrayList<>();
            for (int i = 0; i < getCount(); i++) {
                alarmIntents.add(null);
            }
        }

        for (int i = alarmIntents.size(); i < getCount(); i++) {
            alarmIntents.add(null);
        }
        return alarmIntents;
    }

    private void setAlarm(Context context, Prescription prescription){
        String[] times = prescription.getIntakeTimes().split(",");

        Calendar start = Calendar.getInstance();

        //If startDate > now, set start to startDate
        if (Long.parseLong(prescription.getStartDate()) > start.getTimeInMillis()) {
            start.setTimeInMillis(Long.parseLong(prescription.getStartDate()));
        }


        for (String time : times) {
            time = time.trim();
            String[] hourMinute = time.split(":");
            Calendar calendar = Calendar.getInstance();

//            calendar.set(2017, 1, 26, 23, 26, 0);

            calendar.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE), Integer.parseInt(hourMinute[0].trim()), Integer.parseInt(hourMinute[1].trim()), 0);

            //If start point < now, set the start point on tomorrow at this time
            if (calendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                calendar.setTimeInMillis(calendar.getTimeInMillis() + 24 * 60 * 60 * 1000);
            }

            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra(PRESCRIPTION_ID, prescription.getId() + "");
            List<Intent> intents = getAlarmIntents();
            intents.set(getPosition(prescription), intent);
Log.d(">>>>", "111");
            PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
Log.d(">>>>", "222");
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Integer.parseInt(prescription.getFrequency()) * 24 * 60 * 60 * 1000, pending);
Log.d(">>>>", "333");
            Log.d("adapter", "!!!!!");
            Log.d(">>>>>>set", calendar.getTimeInMillis() + "");
            Log.d("<<<<<<now", Calendar.getInstance().getTimeInMillis() + "");
        }

    }

    private void unsetAlarm(Context context, Prescription prescription) {
        List<Intent> intents = getAlarmIntents();
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, intents.get(getPosition(prescription)), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
    }
}
