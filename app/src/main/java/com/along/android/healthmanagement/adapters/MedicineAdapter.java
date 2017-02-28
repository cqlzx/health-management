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
 * Created by RitenVithlani on 2/25/17.
 */

public class MedicineAdapter extends ArrayAdapter<Medicine> {

    public final String DEFAULT_QTY = "1";
    public final String DEFAULT_FREQUENCY = "1";

    public MedicineAdapter(Context context, List<Medicine> medicines) {
        super(context, 0, medicines);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_medicine, parent, false);
        }

        Medicine medicine = getItem(position);
        
        TextView medicineId = (TextView) listItemView.findViewById(R.id.tvAPMedicineId);
        medicineId.setText(medicine.getId().toString());

        TextView medicineName = (TextView) listItemView.findViewById(R.id.tvAPMedicineName);
        medicineName.setText(medicine.getName());

        TextView medicineQuantity = (TextView) listItemView.findViewById(R.id.tvAPMedicineQuantity);
        medicineQuantity.setText(null != medicine.getQuantity() ? medicine.getQuantity().toString() : DEFAULT_QTY);

        TextView medicineTimings = (TextView) listItemView.findViewById(R.id.tvAPMedicineTimings);
        medicineTimings.setText(null != medicine.getTimings() ? medicine.getTimings() : "");

        TextView medicineFrequency = (TextView) listItemView.findViewById(R.id.tvAPMedicineFrequency);
        medicineFrequency.setText(null != medicine.getFrequency() ? medicine.getFrequency().toString() : DEFAULT_FREQUENCY);

        return listItemView;
    }
}
