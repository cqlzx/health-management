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
public class HomeFragment extends BasicFragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnMedication = (Button) view.findViewById(R.id.btn_medication_tile);
        btnMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new MedicationMenuFragment(), "medicationListingFragment");
            }
        });

        Button btnDiet = (Button) view.findViewById(R.id.btn_diet_tile);
        btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:change back
//                createFragment(new DietFragment(), "dietFragment");
                createFragment(new MealDetailFragment(), "mealDetailFragment");
            }
        });

        Button btnVitalSign = (Button) view.findViewById(R.id.btn_vital_signs_tile);
        btnVitalSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new VitalSignTabFragment(), "vitalSignsFragment");
            }
        });

        return view;
    }

}
