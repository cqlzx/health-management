package com.along.android.healthmanagement.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.apis.Apis;
import com.along.android.healthmanagement.common.JsonCallback;
import com.along.android.healthmanagement.entities.EmergencyContact;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Validation;
import com.along.android.healthmanagement.network.BaseResponse;
import com.along.android.healthmanagement.network.SimpleResponse;
import com.along.android.healthmanagement.utils.JSONUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

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

        SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long uid = sp.getLong("uid", 0);
        final List<EmergencyContact> emergencyContactList = EntityManager.listAll(EmergencyContact.class);
        OkGo.<BaseResponse<List<EmergencyContact>>>get(Apis.getContact())
                .tag(this)
                .params("uid", uid)
                .execute(new JsonCallback<BaseResponse<List<EmergencyContact>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<EmergencyContact>>> response) {
                        BaseResponse data = response.body();
                        if (data != null) {
                            List<EmergencyContact> emergencyContactList = (List<EmergencyContact>)data.data;
                            if (emergencyContactList != null && emergencyContactList.size() >= 1) {
                                for (EmergencyContact emergencyCont : emergencyContactList) {
                                    eName.setText(emergencyCont.getName());
                                    eContact.setText(emergencyCont.getPhoneNumber());
                                    eEmail.setText(emergencyCont.getEmail());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<List<EmergencyContact>>> response) {
                        super.onError(response);
                        if (emergencyContactList.size() >= 1) {
                            for (EmergencyContact emergencyCont : emergencyContactList) {
                                eName.setText(emergencyCont.getName());
                                eContact.setText(emergencyCont.getPhoneNumber());
                                eEmail.setText(emergencyCont.getEmail());
                            }
                        }
                    }
                });


        Button btnCancelEmergency = (Button) view.findViewById(R.id.btnCancelEmergency);

        btnSaveEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eEmail.getText().toString();
                String name = eName.getText().toString();
                String phoneNumber = eContact.getText().toString();

                final EmergencyContact cont = new EmergencyContact();
                cont.setName(name);
                cont.setEmail(email);
                cont.setPhoneNumber(phoneNumber);

                if (Validation.isEmpty(cont, getContext()) && Validation.isValidEmail(email, getContext()) && Validation.isValidPhonenumber(phoneNumber, getContext())) {
                    SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                    Long uid = sp.getLong("uid", 0);
                    cont.setUid(uid);
                    OkGo.<SimpleResponse>post(Apis.insertContact())
                            .tag(this)
                            .upJson(JSONUtil.toJSONObject(cont))
                            .execute(new JsonCallback<SimpleResponse>() {
                                @Override
                                public void onSuccess(Response<SimpleResponse> response) {
                                    Toast.makeText(getContext(), "Emergency contact added successfully", Toast.LENGTH_LONG).show();
                                    getFragmentManager().popBackStack();
                                }

                                @Override
                                public void onError(Response<SimpleResponse> response) {
                                    super.onError(response);
                                    cont.save();
                                    /*if (emergencyContactList.size() >= 1) {
                                        emergencyContactList.get(0).setUid(uid);
                                        emergencyContactList.get(0).setName(name);
                                        emergencyContactList.get(0).setPhoneNumber(phoneNumber);
                                        emergencyContactList.get(0).setEmail(email);
                                        emergencyContactList.get(0).save();
                                    } else {
                                        EmergencyContact emergencyContact = new EmergencyContact();
                                        emergencyContact.setUid(uid);
                                        emergencyContact.setName(name);
                                        emergencyContact.setEmail(email);
                                        emergencyContact.setPhoneNumber(phoneNumber);
                                        emergencyContact.save();
                                    }*/
                                    Toast.makeText(getContext(), "Emergency contact added error", Toast.LENGTH_LONG).show();
                                }
                            });
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