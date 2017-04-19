package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.MedicationDetailsActivity;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.fragments.medication.MedicationSearchFragment;

import java.util.List;

public class MedicationSearchResultAdapter extends ArrayAdapter<Prescription> {
    private String searchText;

    public MedicationSearchResultAdapter(Context context, MedicationSearchFragment fragment, List<Prescription> prescriptions) {
        super(context, 0, prescriptions);
        this.searchText = fragment.searchText;
    }

    public void setText(String text) {
        this.searchText = text;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        ListView listView = (ListView) parent;
        Prescription prescription = getItem(position);

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_medication_search_result, parent, false);
        }

        TextView tvMedicationSearchSymptom = (TextView) listItemView.findViewById(R.id.tv_medication_search_symptom);
        tvMedicationSearchSymptom.setText("For " + prescription.getDisease());

        String medication = (null != prescription.getMedication()) ? prescription.getMedication() : "";
        String medicationIgnoreCase = medication.toLowerCase();
        String searchTextIgnoreCase = searchText.toLowerCase();

        int index = medicationIgnoreCase.indexOf(searchTextIgnoreCase);
        String part1 = medication.substring(0, index);
        String part2 = medication.substring(index, index + searchText.length());
        String part3 = medication.substring(index + searchText.length());

        TextView tvMedicationSearchName1 = (TextView) listItemView.findViewById(R.id.tv_medication_search_name1);
        TextView tvMedicationSearchName2 = (TextView) listItemView.findViewById(R.id.tv_medication_search_name2);
        TextView tvMedicationSearchName3 = (TextView) listItemView.findViewById(R.id.tv_medication_search_name3);
        tvMedicationSearchName1.setText(part1);
        tvMedicationSearchName2.setText(part2);
        tvMedicationSearchName3.setText(part3);

        return listItemView;
    }
}
