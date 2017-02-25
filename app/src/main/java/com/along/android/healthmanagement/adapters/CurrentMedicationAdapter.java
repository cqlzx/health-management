package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Prescription;

import java.util.List;

/**
 * Created by RitenVithlani on 2/25/17.
 */

public class CurrentMedicationAdapter extends ArrayAdapter<Prescription> {

    public CurrentMedicationAdapter(Context context, List<Prescription> prescriptions) {
        super(context, 0, prescriptions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_current_medication, parent, false);
        }

        Prescription prescription = getItem(position);

        TextView medicines = (TextView) listItemView.findViewById(R.id.tvMLMedicationNames);
        medicines.setText(prescription.getMedication());

        TextView doctorName = (TextView) listItemView.findViewById(R.id.tvMLDoctorName);
        doctorName.setText(prescription.getDoctorName());

        TextView disease = (TextView) listItemView.findViewById(R.id.tvMLDisease);
        disease.setText(prescription.getDisease());

        if(prescription.isNotificationEnabled()) {
            // Highlight Notification button
        }

        return listItemView;
    }
}
