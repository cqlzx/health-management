package com.along.android.healthmanagement.fragments.vitalsigns;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.VitalSignDetailsActivity;
import com.along.android.healthmanagement.adapters.VitalSignsHistoryAdapter;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.fragments.BasicFragment;
import com.along.android.healthmanagement.fragments.DatePickerFragment;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VitalSignTabFragment extends BasicFragment {

    TextView tvStartDateVitalSignInMillis;
    TextView tvEndDateVitalSignInMillis;
    VitalSignsHistoryAdapter vitalSignsHistoryAdapter;

    public VitalSignTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vital_signs, container, false);

        tvStartDateVitalSignInMillis = (TextView) view.findViewById(R.id.tvStartDateVitalSignInMillis);
        tvEndDateVitalSignInMillis = (TextView) view.findViewById(R.id.tvEndDateVitalSignInMillis);




        //Dummy data, comment when demo
        SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        long uid = sp.getLong("uid", 0);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        EntityManager.deleteAll(VitalSign.class);
        for (int i = 1; i < 10; i++) {
            VitalSign vs = new VitalSign();
            vs.setUid(uid);
            vs.setHeight("5.6");
            vs.setWeight("170");
            vs.setBloodGlucose("22");
            vs.setBloodPressure("120/80");
            vs.setHeartRate("70");
            vs.setBodyTemperature("104");

            calendar.clear();
            calendar.set(year, month, day - i * i);
            vs.setDate(calendar.getTimeInMillis());

            vs.save();
        }




        List<VitalSign> vitalSignList;
        try {
            vitalSignList = EntityManager.listAll(VitalSign.class);
        } catch (SQLiteException e) {
            vitalSignList = new ArrayList<>();
        }


        LinearLayout llStartDateVitalSign = (LinearLayout) view.findViewById(R.id.llStartDateVitalSign);
        llStartDateVitalSign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate", "startDateVitalSign");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "startDatePicker");
            }
        });

        LinearLayout llEndDateVitalSign = (LinearLayout) view.findViewById(R.id.llEndDateVitalSign);
        llEndDateVitalSign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate", "endDateVitalSign");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "endDatePicker");
            }
        });


        Button btnVitalSignSearch = (Button) view.findViewById(R.id.btnVitalSignSearch);
        btnVitalSignSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVitalSignList();
            }
        });


        ImageView fab = (ImageView) view.findViewById(R.id.add_medication_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Invoke the fragment to add new Vital Signs form
                createFragment(new AddVitalSignFormFragment(), "addVitalSignFormFragment");
            }
        });


        if (vitalSignsHistoryAdapter == null) {
            vitalSignsHistoryAdapter = new VitalSignsHistoryAdapter(getActivity(), vitalSignList);
        } else {
            vitalSignsHistoryAdapter.clear();
            vitalSignsHistoryAdapter.addAll(vitalSignList);
            vitalSignsHistoryAdapter.notifyDataSetChanged();
        }


        ListView listView = (ListView) view.findViewById(R.id.vital_signs_history_list);
        TextView tvEmptyMsg = (TextView) view.findViewById(R.id.tvVSEmptyMsg);
        listView.setEmptyView(tvEmptyMsg);
        listView.setAdapter(vitalSignsHistoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vitalSignId = (TextView) view.findViewById(R.id.tvXLVSHId);
                TextView vitalSignDate = (TextView) view.findViewById(R.id.tvXLVSHDate);
                Intent vitalSignDetailsIntent = new Intent(getActivity(), VitalSignDetailsActivity.class);
                vitalSignDetailsIntent.putExtra("selectedVitalSignItemId", vitalSignId.getText().toString());
                vitalSignDetailsIntent.putExtra("selectedVitalSignItemDate", vitalSignDate.getText().toString());
                startActivity(vitalSignDetailsIntent);
            }
        });


        return view;
    }

    private void changeVitalSignList() {
        String startTime = tvStartDateVitalSignInMillis.getText().toString();
        String endTime = tvEndDateVitalSignInMillis.getText().toString();

        List<VitalSign> vitalSigns;
        if (!startTime.equals("Start date in millis") && !endTime.equals("End date in millis")) {
            vitalSigns = EntityManager.findWithQuery(VitalSign.class, "SELECT * FROM VITAL_SIGN WHERE date >= ? AND date <= ?", startTime, endTime);
        } else if (!startTime.equals("Start date in millis")) {
            vitalSigns = EntityManager.findWithQuery(VitalSign.class, "SELECT * FROM VITAL_SIGN WHERE date >= ?", startTime);
        } else if (!endTime.equals("End date in millis")) {
            vitalSigns = EntityManager.findWithQuery(VitalSign.class, "SELECT * FROM VITAL_SIGN WHERE date <= ?", endTime);
        } else {
            vitalSigns = EntityManager.listAll(VitalSign.class);
        }

        vitalSignsHistoryAdapter.clear();
        vitalSignsHistoryAdapter.addAll(vitalSigns);
        vitalSignsHistoryAdapter.notifyDataSetChanged();

    }

}
