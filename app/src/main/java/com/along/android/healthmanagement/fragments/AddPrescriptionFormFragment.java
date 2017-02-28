package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.MedicineAdapter;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Utility;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPrescriptionFormFragment extends BasicFragment {
    MedicineAdapter medicineAdapter;

    public AddPrescriptionFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);

        List<Medicine> medicineList = Medicine.listAll(Medicine.class);
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
        llStartDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate","startDate");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "startDatePicker");
            }
        });

        LinearLayout llEndDate = (LinearLayout) view.findViewById(R.id.llEndDate);
        llEndDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate","endDate");
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

        Button btnSavePrescription = (Button) view.findViewById(R.id.btnSavePrescription);
        btnSavePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all medicine ids and save it into
            }
        });

        return view;
    }

    public void addMedicineToList(Long medicineId) {
        Medicine medicine = EntityManager.findById(Medicine.class, medicineId);
        medicineAdapter.add(medicine);
    }
}
