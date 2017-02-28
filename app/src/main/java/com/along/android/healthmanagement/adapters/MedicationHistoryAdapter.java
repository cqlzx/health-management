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

public class MedicationHistoryAdapter extends ArrayAdapter<Prescription> {

    public MedicationHistoryAdapter(Context context, List<Prescription> prescriptions) {
        super(context, 0, prescriptions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_medication_history, parent, false);
        }

        Prescription prescription = getItem(position);

        TextView prescriptionId = (TextView) listItemView.findViewById(R.id.tvMHPrescriptionId);
        prescriptionId.setText(prescription.getId().toString());

        TextView medicines = (TextView) listItemView.findViewById(R.id.tvMHMedicationNames);
        medicines.setText(null != prescription.getMedication() ? prescription.getMedication() : "");

        TextView doctorName = (TextView) listItemView.findViewById(R.id.tvMHDoctorName);
        doctorName.setText(null != prescription.getDoctorName() ? prescription.getDoctorName() : "");

        TextView disease = (TextView) listItemView.findViewById(R.id.tvMHDisease);
        disease.setText(null != prescription.getDisease() ? prescription.getDisease() : "");


        return listItemView;
    }
}
