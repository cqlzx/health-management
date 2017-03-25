package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.VitalSign;

import java.util.Calendar;
import java.util.List;

/**
 * Created by renxiaolei on 3/25/16.
 */

public class VitalSignsHistoryAdapter extends ArrayAdapter<VitalSign> {
    public VitalSignsHistoryAdapter(Context context, List<VitalSign> vitalSigns) {
        super(context, 0, vitalSigns);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_vital_signs_history, parent, false);
        }

        VitalSign vitalsignhistoy = getItem(position);

        // Id
        TextView vshId = (TextView) listItemView.findViewById(R.id.tvXLVSHId);
        vshId.setText(vitalsignhistoy.getId().toString());

        // date
        TextView vshDateTV = (TextView) listItemView.findViewById(R.id.tvXLVSHDate);

        // Prescription Date
        Calendar c = Calendar.getInstance();
//        String startDate = null != prescription.getStartDate() ? prescription.getStartDate() : "";
        c.setTimeInMillis(Long.parseLong(vitalsignhistoy.getDate().toString()));
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String[] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String prescriptionDateText = "Vital Signs Date: " + months[mMonth] + " " + mDay + ", " + mYear;

        vshDateTV.setText(prescriptionDateText);
        Log.d("--haha--->>>>>>>", prescriptionDateText);

//        //xlMDMedicineName
//        TextView medicineNameTV = (TextView) listItemView.findViewById(R.id.xlMDMedicineName);
//        String medicineNameText = (null != medicine.getName() ? medicine.getName() : "");
//        medicineNameTV.setText(medicineNameText);
        return listItemView;
    }
}
