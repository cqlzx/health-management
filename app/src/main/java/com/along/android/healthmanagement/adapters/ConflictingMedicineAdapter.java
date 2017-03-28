package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.ConflictingMedicine;

import java.util.List;

/**
 * Created by RitenVithlani on 2/25/17.
 */

public class ConflictingMedicineAdapter extends ArrayAdapter<ConflictingMedicine> {

    public ConflictingMedicineAdapter(Context context, List<ConflictingMedicine> conflictingMedicines) {
        super(context, 0, conflictingMedicines);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final ListView listView = (ListView) parent;
        if (null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_conflicting_medicine, parent, false);
        }

        final ConflictingMedicine conflictingMedicine = getItem(position);

        TextView message = (TextView) listItemView.findViewById(R.id.tvConflictingMedicineMessage);
        message.setText("Medicines " + conflictingMedicine.getMedicine1().getName() + " and " + conflictingMedicine.getMedicine2().getName() + " are conflicting.");

        TextView link = (TextView) listItemView.findViewById(R.id.tvConflictingMedicineDetailsLink);
        link.setText("More Details");

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(conflictingMedicine.getExternalUrl()));
                getContext().startActivity(browserIntent);
            }
        });
        return listItemView;
    }
}
