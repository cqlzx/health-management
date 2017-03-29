package com.along.android.healthmanagement.fragments;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.EmergencyContact;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padma on 3/25/17.
 */

public class EmergencyFragment extends BasicFragment {

    ProgressDialog prgDialog;
    EditText eName, eContact, eEmail;
    List<EmergencyContact> emergencyList = new ArrayList<EmergencyContact>();

    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        Button btnSaveEmergency = (Button) view.findViewById(R.id.btnSaveEmergency);

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        eName = (EditText) view.findViewById((R.id.eName));
        eContact = (EditText) view.findViewById(R.id.eContact);
        eEmail = (EditText) view.findViewById(R.id.eEmail);

        final List<EmergencyContact> emergencyContactList = EntityManager.listAll(EmergencyContact.class);

        if (emergencyContactList.size() >= 1) {
            for (EmergencyContact emergencyCont : emergencyContactList) {
                eName.setText(emergencyCont.getName());
                eContact.setText(emergencyCont.getPhoneNumber());
                eEmail.setText(emergencyCont.getEmail());
            }
        }

        Button btnCancelEmergency = (Button) view.findViewById(R.id.btnCancelEmergency);

        btnSaveEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eEmail.getText().toString();
                String name = eName.getText().toString();
                String phoneNumber = eContact.getText().toString();

                EmergencyContact cont = new EmergencyContact();
                cont.setName(name);
                cont.setEmail(email);
                cont.setPhoneNumber(phoneNumber);

                if (Validation.isEmpty(cont, getContext()) && Validation.isValidEmail(email, getContext()) && Validation.isValidPhonenumber(phoneNumber, getContext())) {
                    if (emergencyContactList.size() >= 1) {
                        emergencyContactList.get(0).setName(name);
                        emergencyContactList.get(0).setPhoneNumber(phoneNumber);
                        emergencyContactList.get(0).setEmail(email);
                        emergencyContactList.get(0).save();
                    } else {
                        EmergencyContact emergencyContact = new EmergencyContact();
                        emergencyContact.setName(name);
                        emergencyContact.setEmail(email);
                        emergencyContact.setPhoneNumber(phoneNumber);
                        emergencyContact.save();
                    }
                    Toast.makeText(getContext(), "Emergency contact added successfully", Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                }

            }

        });

        btnCancelEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;

    }


    private void EmergencyContactList(View view) {
        List<EmergencyContact> EmergencyList;

        try {
            EmergencyList = EmergencyContact.listAll(EmergencyContact.class);
        } catch (SQLiteException e) {
            EmergencyList = new ArrayList<>();
        }

    }


}