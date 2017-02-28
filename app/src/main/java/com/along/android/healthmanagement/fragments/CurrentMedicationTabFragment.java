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
import com.along.android.healthmanagement.adapters.CurrentMedicationAdapter;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentMedicationTabFragment extends Fragment {

    public CurrentMedicationTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
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
//        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
//
//        // Get the actual data from the database
//        for(int i=1; i<=1;i++) {
//            Prescription prescription = new Prescription();
//            prescription.setMedication(i + ". Rabonik, Telsar, Telnol");
//            prescription.setDoctorName("Prescribed by Dr. Nitin Shah");
//            prescription.setDisease("for Blood Pressure");
//            prescription.setIntakeTimes("9:00,12:00,18:00");
//            prescription.setStartDate("1488067200000");
//            prescription.setEndDate("1488326400000");
//            prescription.setNotificationEnabled(i%2 == 0 ? true : false);
//
//            prescriptions.add(prescription);
//            prescription.save();
//        }
        if (EntityManager.count(Prescription.class) == 0) {
            for(int i=1; i<=3;i++) {
                Prescription prescription = new Prescription();
                prescription.setMedication(i + ". Rabonik, Telsar, Telnol");
                prescription.setDoctorName("Prescribed by Dr. Nitin Shah");
                prescription.setDisease("for Blood Pressure");
                prescription.setIntakeTimes("9:00,12:00,18:00");
                prescription.setStartDate("1488067200000");
                prescription.setEndDate("1488326400000");

                prescription.save();
            }
        }

        //If today > endDate, the add to history

        List<Prescription> prescriptionList = Prescription.listAll(Prescription.class);

        CurrentMedicationAdapter currentMedicationAdapter =
                new CurrentMedicationAdapter(getActivity(), prescriptionList);

        TextView tvEmptyMsg = (TextView) view.findViewById(R.id.tvMLEmptyMsg);
        ListView listView = (ListView) view.findViewById(R.id.current_medication_list);

        listView.setEmptyView(tvEmptyMsg);
        listView.setAdapter(currentMedicationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicationDetailsActivity medicationDetailActivity = new MedicationDetailsActivity();

                TextView prescriptionId = (TextView) view.findViewById(R.id.tvMLPrescriptionId);

                Intent medicationDetailsIntent = new Intent(getActivity(), MedicationDetailsActivity.class);
                medicationDetailsIntent.putExtra("selectedPrescriptionItemId", prescriptionId.getText().toString());
                startActivity(medicationDetailsIntent);
            }
        });
    }

}
