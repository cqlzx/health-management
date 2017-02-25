package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.MedicationHistoryAdapter;
import com.along.android.healthmanagement.entities.Prescription;

import java.util.ArrayList;

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
        Prescription prescription = new Prescription();
        prescription.setMedication("Rabonik, Telsar, Telnol");
        prescription.setDoctorName("Prescribed by Dr. Nitin Shah");
        prescription.setDisease("for Blood Pressure");

        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);

        MedicationHistoryAdapter medicationHistoryAdapter =
                new MedicationHistoryAdapter(getActivity(), prescriptions);

        ListView listView = (ListView) view.findViewById(R.id.medication_history_list);

        listView.setAdapter(medicationHistoryAdapter);

        return view;
    }

}
