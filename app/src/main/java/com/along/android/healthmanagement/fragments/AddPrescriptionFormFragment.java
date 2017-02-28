package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.along.android.healthmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPrescriptionFormFragment extends BasicFragment {


    public AddPrescriptionFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);

        Button addMedicineButton = (Button) view.findViewById(R.id.btnAddNewMedicine);



        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new AddMedicineFormFragment(), "addMedicineFormFragment");
            }
        });
        return view;
    }
}
