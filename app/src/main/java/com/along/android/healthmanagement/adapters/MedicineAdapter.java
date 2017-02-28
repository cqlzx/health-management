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
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.helpers.Utility;

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
        final ListView listView = (ListView) parent;
        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_medicine, parent, false);
        }

        final Medicine medicine = getItem(position);
        
        TextView medicineId = (TextView) listItemView.findViewById(R.id.tvAPMedicineId);
        medicineId.setText(medicine.getId().toString());

        TextView medicineName = (TextView) listItemView.findViewById(R.id.tvAPMedicineName);
        medicineName.setText(null != medicine.getName() ? medicine.getName() : "");

        TextView medicineQuantity = (TextView) listItemView.findViewById(R.id.tvAPMedicineQuantity);
        String qtyText = "Quantity: " + (null != medicine.getQuantity() ? medicine.getQuantity().toString() : DEFAULT_QTY);
        medicineQuantity.setText(qtyText);

        TextView medicineTimings = (TextView) listItemView.findViewById(R.id.tvAPMedicineTimings);
        String timingsText = "To be taken at " + (null != medicine.getTimings() ? medicine.getTimings() : "");
        medicineTimings.setText(timingsText);

        TextView medicineFrequency = (TextView) listItemView.findViewById(R.id.tvAPMedicineFrequency);
        String frequency = null != medicine.getFrequency() ? medicine.getFrequency().toString() : DEFAULT_FREQUENCY;
        String frequencyText = "1".equals(frequency) ? "everyday" : "in every " + frequency + " days";
        medicineFrequency.setText(frequencyText);

        ImageView deleteMedicine = (ImageView) listItemView.findViewById(R.id.ivDeleteMedicine);
        deleteMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(medicine, listView);
            }
        });

        return listItemView;
    }

    private void showDeleteAlertDialog(final Medicine medicine, final ListView listView) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirm Delete");
        alert.setMessage("Are you sure to delete this medicine entry?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* delete the medicine entry from the database */
                Medicine medicineRecord = Medicine.findById(Medicine.class, medicine.getId());
                medicineRecord.delete();
                MedicineAdapter.this.remove(medicine);
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
