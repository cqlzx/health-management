package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.entities.Prescription;

import java.util.List;

import static com.along.android.healthmanagement.R.id.xlMDDelete;

/**
 * Created by renxiaolei on 2/26/16.
 */


public class MedicationDetailAdapter extends ArrayAdapter<Medicine> {

    public MedicationDetailAdapter(Context context, List<Medicine> medicines) {
        super(context, 0, medicines);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_medication_detail, parent, false);
        }

        final Medicine medicine = getItem(position);

        TextView medicines = (TextView) listItemView.findViewById(R.id.xlMDMedicationNames);
        medicines.setText(medicine.getName());

        TextView doctorName = (TextView) listItemView.findViewById(R.id.xlMDTimeTaken);
//        doctorName.setText(medicine.getStartDate());

        TextView disease = (TextView) listItemView.findViewById(R.id.xlMDTimeInterval);
//        disease.setText(medicine.getEndDate());

        LinearLayout llMDDelete = (LinearLayout) listItemView.findViewById(xlMDDelete);
        llMDDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDeleteAlertDialog(medicine);
                Log.d("--delete--->>>>>>>", medicine.getName());
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
//                Medicine medicineRecord = Medicine.findById(Prescription.class, medicine.getId());
//                medicineRecord.delete();
//
//                MedicationDetailAdapter.this.remove(medicine);
//
//                dialog.dismiss();
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
