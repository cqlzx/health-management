package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFormFragment extends BasicFragment{


    public AddMedicineFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medicine, container, false);

        Button btnSaveMedicine = (Button) view.findViewById(R.id.btnSaveMedicine);
        Button btnCancelMedicine = (Button) view.findViewById(R.id.btnCancelMedicine);


        btnSaveMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine medicine = new Medicine();
                //medicine.set
            }
        });

        btnCancelMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
}
