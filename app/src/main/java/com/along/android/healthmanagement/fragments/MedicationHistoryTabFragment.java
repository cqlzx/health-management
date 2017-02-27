package com.along.android.healthmanagement.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.MedicationDetailsActivity;
import com.along.android.healthmanagement.adapters.MedicationHistoryAdapter;
import com.along.android.healthmanagement.entities.Prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationHistoryTabFragment extends Fragment {


    public MedicationHistoryTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication_history, container, false);

        // Create a list of words
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

        // Get the actual data from the database
        for(int i=1; i<=1;i++) {
            Prescription prescription = new Prescription();
            prescription.setMedication(i + ". Rabonik, Telsar, Telnol");
            prescription.setDoctorName("Prescribed by Dr. Nitin Shah");
            prescription.setDisease("for Blood Pressure");
            prescription.setNotificationEnabled(i%2 == 0 ? true : false);

            prescriptions.add(prescription);
            prescription.save();
        }

        List<Prescription> prescriptionList = Prescription.listAll(Prescription.class);

        MedicationHistoryAdapter medicationHistoryAdapter =
                new MedicationHistoryAdapter(getActivity(), prescriptionList);

        ListView listView = (ListView) view.findViewById(R.id.medication_history_list);

        listView.setAdapter(medicationHistoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicationDetailsActivity medicationDetailActivity = new MedicationDetailsActivity();

                TextView prescriptionId = (TextView) view.findViewById(R.id.tvMHPrescriptionId);

                Intent medicationDetailsIntent = new Intent(getActivity(), MedicationDetailsActivity.class);
                medicationDetailsIntent.putExtra("selectedPrescriptionItemId", prescriptionId.getText().toString());
                startActivity(medicationDetailsIntent);
            }
        });

        return view;
    }

}
