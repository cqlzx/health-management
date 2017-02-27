package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Prescription;

import java.util.List;

/**
 * Created by RitenVithlani on 2/25/17.
 */

public class CurrentMedicationAdapter extends ArrayAdapter<Prescription> {

    private final String COLOR_PRIMARY_DARK = "#388E3C";
    private final String DEFAULT_TEXT_COLOR = "#808080";
    private ImageView ivNotificationIcon;
    private ImageView ivNotificationIconActive;
    private TextView tvNotification;

    public CurrentMedicationAdapter(Context context, List<Prescription> prescriptions) {
        super(context, 0, prescriptions);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_current_medication, parent, false);
        }

        final Prescription prescription = getItem(position);

        TextView prescriptionId = (TextView) listItemView.findViewById(R.id.tvMLPrescriptionId);
        prescriptionId.setText(prescription.getId().toString());

        TextView medicines = (TextView) listItemView.findViewById(R.id.tvMLMedicationNames);
        medicines.setText(prescription.getMedication());

        TextView doctorName = (TextView) listItemView.findViewById(R.id.tvMLDoctorName);
        doctorName.setText(prescription.getDoctorName());

        TextView disease = (TextView) listItemView.findViewById(R.id.tvMLDisease);
        disease.setText(prescription.getDisease());

        ivNotificationIcon = (ImageView) listItemView.findViewById(R.id.ivMLNotificationIcon);
        ivNotificationIconActive = (ImageView) listItemView.findViewById(R.id.ivMLNotificationIconActive);
        tvNotification = (TextView) listItemView.findViewById(R.id.tvMLNotification);

        if(prescription.isNotificationEnabled()) {
            showActiveNotificationButton();
        } else {
            hideActiveNotificationButton();
        }

        LinearLayout llMLNotification = (LinearLayout) listItemView.findViewById(R.id.llMLNotification);
        llMLNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View llMLNotificationClickedView) {
                toggleNotificationButton(llMLNotificationClickedView, prescription);
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
                prescriptionRecord.delete();

                CurrentMedicationAdapter.this.remove(prescription);

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

    private void toggleNotificationButton(View llMLNotificationClickedView, Prescription prescription) {
        ivNotificationIcon = (ImageView) llMLNotificationClickedView.findViewById(R.id.ivMLNotificationIcon);
        ivNotificationIconActive = (ImageView) llMLNotificationClickedView.findViewById(R.id.ivMLNotificationIconActive);
        tvNotification = (TextView) llMLNotificationClickedView.findViewById(R.id.tvMLNotification);

        if(ivNotificationIcon.getVisibility() == View.VISIBLE) {
            showActiveNotificationButton();

            /*  Set the notification alarm and save to database */
            prescription.setNotificationEnabled(true);
            prescription.save();
        } else {
            hideActiveNotificationButton();

            /* Unset the notification alarm and save to database */
            prescription.setNotificationEnabled(false);
            prescription.save();
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
}
