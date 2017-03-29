package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.helpers.Utility;

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
        final ListView listView = (ListView) parent;
        final VitalSign vitalsign = getItem(position);

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_vital_signs_history, parent, false);
        }



        // Id
        TextView vshId = (TextView) listItemView.findViewById(R.id.tvXLVSHId);
        vshId.setText(vitalsign.getId().toString());

        // date
        TextView vshDateTV = (TextView) listItemView.findViewById(R.id.tvXLVSHDate);

        // Prescription Date
        Calendar c = Calendar.getInstance();
//        String startDate = null != prescription.getStartDate() ? prescription.getStartDate() : "";
        c.setTimeInMillis(Long.parseLong(vitalsign.getDate().toString()));
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String[] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String prescriptionDateText =  months[mMonth] + " " + mDay + ", " + mYear;

        vshDateTV.setText(prescriptionDateText);


//        LinearLayout llDelete = (LinearLayout) listItemView.findViewById(R.id.llDelete);
//        ImageView ivDelete = (ImageView) listItemView.findViewById(R.id.ivDelete);
        TextView tvDelete = (TextView) listItemView.findViewById(R.id.tvDelete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(vitalsign,listView);
            }
        });
//        ivDelete.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showDeleteAlertDialog(vitalsign,listView);
//                return false;
//            }
//        });


//        //xlMDMedicineName
//        TextView medicineNameTV = (TextView) listItemView.findViewById(R.id.xlMDMedicineName);
//        String medicineNameText = (null != medicine.getName() ? medicine.getName() : "");
//        medicineNameTV.setText(medicineNameText);
        return listItemView;
    }
    private void showDeleteAlertDialog(final VitalSign vitalSign, final ListView listView) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirm Delete");
        alert.setMessage("Are you sure to delete this medicine entry?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* delete the medicine entry from the database */
//                Medicine medicineRecord = Medicine.findById(Medicine.class, medicine.getId());
//                medicineRecord.delete();
//                MedicineAdapter.this.remove(medicine);
//                Utility.setListViewHeightBasedOnChildren(listView);
                VitalSign vitalSignRecord = VitalSign.findById(VitalSign.class,vitalSign.getId());
                vitalSignRecord.delete();
                VitalSignsHistoryAdapter.this.remove(vitalSign);
                Utility.setListViewHeightBasedOnChildren(listView);
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
}
