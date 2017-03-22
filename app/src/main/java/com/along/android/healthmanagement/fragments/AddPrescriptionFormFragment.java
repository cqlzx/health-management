package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.LoginActivity;
import com.along.android.healthmanagement.adapters.MedicineAdapter;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPrescriptionFormFragment extends BasicFragment {

    private final int DEFAULT_MIN = 100;

    MedicineAdapter medicineAdapter;
    EditText etPatientName, etDoctorName, etDisease;
    TextView tvStartDateInMillis, tvEndDateInMillis;
    List<Medicine> medicineList = new ArrayList<Medicine>();

    public AddPrescriptionFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);

        medicineAdapter = new MedicineAdapter(getActivity(), medicineList);
        ListView listView = (ListView) view.findViewById(R.id.medicine_list);
        listView.setAdapter(medicineAdapter);
        Utility.setListViewHeightBasedOnChildren(listView);

        LinearLayout llAddMedicine = (LinearLayout) view.findViewById(R.id.llAddMedicineLink);
        llAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new AddMedicineFormFragment(), "addMedicineFormFragment");
            }
        });

        LinearLayout llStartDate = (LinearLayout) view.findViewById(R.id.llStartDate);
        llStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate", "startDate");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "startDatePicker");
            }
        });

        LinearLayout llEndDate = (LinearLayout) view.findViewById(R.id.llEndDate);
        llEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate", "endDate");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "endDatePicker");
            }
        });

        Button btnCancelPrescription = (Button) view.findViewById(R.id.btnCancelPrescription);
        btnCancelPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        etPatientName = (EditText) view.findViewById(R.id.etPatientName);
        etDoctorName = (EditText) view.findViewById(R.id.etDoctorName);
        etDisease = (EditText) view.findViewById(R.id.etDisease);
        tvStartDateInMillis = (TextView) view.findViewById(R.id.tvStartDateInMillis);
        tvEndDateInMillis = (TextView) view.findViewById(R.id.tvEndDateInMillis);

        Button btnSavePrescription = (Button) view.findViewById(R.id.btnSavePrescription);
        btnSavePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all medicine ids and save it into prescription object
                // merge medicine timing of all medicines and store it in prescription
                // take min frequency of all the medicines and store it in prescription
                // save prescription
                savePrescription();
            }
        });

        return view;
    }

    private void savePrescription() {
        int count = medicineAdapter.getCount();

        Set<String> medicines = new HashSet<String>();
        Set<String> timings = new HashSet<String>();
        int minFrequency = DEFAULT_MIN;
        Medicine medicine;
        for (int i = 0; i < count; i++) {
            medicine = medicineAdapter.getItem(i);

            medicines.add(medicine.getName());
            timings.add(medicine.getTimings());

            int frequency = Integer.parseInt(medicine.getFrequency());
            if (frequency < minFrequency) {
                minFrequency = frequency;
            }
        }

        Prescription prescription = new Prescription();
        prescription.setPatientName(null != etPatientName.getText() ? etPatientName.getText().toString() : "");
        prescription.setDoctorName(null != etDoctorName.getText() ? etDoctorName.getText().toString() : "");
        prescription.setDisease(null != etDisease.getText() ? etDisease.getText().toString() : "");
        prescription.setStartDate(tvStartDateInMillis.getText().toString());
        prescription.setEndDate(tvEndDateInMillis.getText().toString());
        prescription.setFrequency(minFrequency + "");
        prescription.setIntakeTimes(android.text.TextUtils.join(",", timings));
        prescription.setMedication(android.text.TextUtils.join(",", medicines));

            Long prescriptionId = prescription.save();

            // Update the prescriptionId in the medicine table
            for (int i = 0; i < count; i++) {

                medicine = medicineAdapter.getItem(i);
                Medicine med = EntityManager.findById(Medicine.class, medicine.getId());
                med.setPid(prescriptionId);
                med.save();
            }

            getFragmentManager().popBackStack();


    }

    public void addMedicineToList(Long medicineId) {
        Medicine medicine = EntityManager.findById(Medicine.class, medicineId);
        medicineAdapter.add(medicine);
    }
}
