package com.along.android.healthmanagement.fragments;


import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.MedicationDetailsActivity;
import com.along.android.healthmanagement.activities.VitalSignDetailsActivity;
import com.along.android.healthmanagement.adapters.VitalSignsHistoryAdapter;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VitalSignsFragment extends BasicFragment {


    public VitalSignsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vital_signs, container, false);

        List<VitalSign> vitalSignList;
        try {
            vitalSignList = EntityManager.listAll(VitalSign.class);
        } catch (SQLiteException e) {
            vitalSignList = new ArrayList<>();
        }

        ImageView fab = (ImageView) view.findViewById(R.id.add_medication_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Invoke the fragment to add new Medication form
                createFragment(new AddVitalSignFormFragment(), "addVitalSignFormFragment");
            }
        });


        VitalSignsHistoryAdapter vitalSignsHistoryAdapter =
                new VitalSignsHistoryAdapter(getActivity(), vitalSignList);

        TextView tvEmptyMsg = (TextView) view.findViewById(R.id.tvMHEmptyMsg);
//        tvEmptyMsg.setText("No vital signs history to display");

        ListView listView = (ListView) view.findViewById(R.id.vital_signs_history_list);

        listView.setAdapter(vitalSignsHistoryAdapter);
        listView.setEmptyView(tvEmptyMsg);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vitalSignId = (TextView) view.findViewById(R.id.tvXLVSHId);
                Log.d("--haha--->>>>>>>", vitalSignId.getText().toString());
//                MedicationDetailsActivity medicationDetailActivity = new MedicationDetailsActivity();
//
//                TextView prescriptionId = (TextView) view.findViewById(R.id.tvXLVSHDate);
//
//                Intent medicationDetailsIntent = new Intent(getActivity(), MedicationDetailsActivity.class);
//                medicationDetailsIntent.putExtra("selectedPrescriptionItemId", prescriptionId.getText().toString());
//                startActivity(medicationDetailsIntent);
                Intent vitalSignDetailsIntent = new Intent(getActivity(), VitalSignDetailsActivity.class);
                vitalSignDetailsIntent.putExtra("selectedVitalSignItemId", vitalSignId.getText().toString());
                startActivity(vitalSignDetailsIntent);
            }
        });


        return view;
    }

}
