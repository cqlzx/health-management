package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.fragments.diet.DietFragment;
import com.along.android.healthmanagement.fragments.medication.AddPrescriptionFormFragment;
import com.along.android.healthmanagement.fragments.medication.MedicationMenuFragment;
import com.along.android.healthmanagement.fragments.vitalsigns.AddVitalSignFormFragment;
import com.along.android.healthmanagement.fragments.vitalsigns.VitalSignTabFragment;

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

        /****** Medication section ******/
        RelativeLayout rlMedication = (RelativeLayout) view.findViewById(R.id.rl_medication);
        rlMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new MedicationMenuFragment(), "medicationListingFragment");
            }
        });

        LinearLayout llHomeAddMedication = (LinearLayout) view.findViewById(R.id.ll_home_add_medication);
        llHomeAddMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFragment(new AddPrescriptionFormFragment(), "addPrescriptionFormFragment");
            }
        });

        /****** Vital Signs section ******/
        RelativeLayout rlVitalSigns = (RelativeLayout) view.findViewById(R.id.rl_vital_signs);
        rlVitalSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new VitalSignTabFragment(), "vitalSignsFragment");
            }
        });

        LinearLayout llHomeAddVitalSigns = (LinearLayout) view.findViewById(R.id.ll_home_add_vital_signs);
        llHomeAddVitalSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFragment(new AddVitalSignFormFragment(), "addVitalSignFormFragment");
            }
        });


        /****** Diet section ******/
        RelativeLayout rlDiet = (RelativeLayout) view.findViewById(R.id.rl_diet);
        rlDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new DietFragment(), "dietFragment");
            }
        });

        /****** Notes section ******/
        RelativeLayout rlNotes = (RelativeLayout) view.findViewById(R.id.rl_notes);
        rlNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new NotesFragment(), "notesFragment");
            }
        });

        LinearLayout llHomeAddNotes = (LinearLayout) view.findViewById(R.id.ll_home_add_notes);
        // link to add notes

        /****** Communication section ******/
        RelativeLayout rlCommunication = (RelativeLayout) view.findViewById(R.id.rl_communication);
        rlCommunication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new EmergencyFragment(), "emergencyFragment");
            }
        });

        return view;
    }

}
