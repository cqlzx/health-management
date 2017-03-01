package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;

import java.util.List;


/**
 * Created by renxiaolei on 2/26/16.
 */


public class MedicationDetailAdapter extends ArrayAdapter<Medicine> {

    public MedicationDetailAdapter(Context context, List<Medicine> medicines) {
        super(context, 0, medicines);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_medication_detail, parent, false);
        }

        Medicine medicine = getItem(position);

        //xlMDMedicineId
        TextView medicineIdTV = (TextView) listItemView.findViewById(R.id.xlMDMedicineId);
        medicineIdTV.setText(medicine.getId().toString());
        //xlMDMedicineName
        TextView medicineNameTV = (TextView) listItemView.findViewById(R.id.xlMDMedicineName);
        String medicineNameText = (null != medicine.getTimings() ? medicine.getTimings() : "");
        medicineNameTV.setText(medicineNameText);
        //xlMDMedicineTimings
        TextView medicineTimingsTV = (TextView) listItemView.findViewById(R.id.xlMDMedicineTimings);
        String medicineTimingsText = "Timings: " + (null != medicine.getTimings() ? medicine.getTimings() : "");
        medicineTimingsTV.setText(medicineTimingsText);
        //xlMDMedicineFrequency
        TextView medicineFrequencyTV = (TextView) listItemView.findViewById(R.id.xlMDMedicineFrequency);
        String medicineFrequencyText = "Frequency: " + (null != medicine.getFrequency() ? medicine.getFrequency() : "");
        medicineFrequencyTV.setText(medicineFrequencyText);
        //xlMDMedicineQuantity
        TextView medicineQuantityTV = (TextView) listItemView.findViewById(R.id.xlMDMedicineQuantity);
        String medicineQuantityText = "Quantity: " + (null != medicine.getQuantity() ? medicine.getQuantity() : "");
        medicineQuantityTV.setText(medicineQuantityText);

        return listItemView;
    }

}
