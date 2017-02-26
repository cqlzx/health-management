package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.CurrentMedicationAdapter;
import com.along.android.healthmanagement.entities.Prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentMedicationTabFragment extends Fragment {

    public CurrentMedicationTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_medication, container, false);

        initializeMedicationListData(view);

        return view;
    }

    private void initializeMedicationListData(View view) {
        // Create a list of prescriptions
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

        CurrentMedicationAdapter currentMedicationAdapter =
                new CurrentMedicationAdapter(getActivity(), prescriptionList);

        ListView listView = (ListView) view.findViewById(R.id.current_medication_list);

        listView.setAdapter(currentMedicationAdapter);
    }

}
