package com.along.android.healthmanagement.fragments.vitalsigns;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.fragments.BasicFragment;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Validation;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddVitalSignFormFragment extends BasicFragment {

    private EditText etSystolic, etDiastolic, etWeight, etHeight, etBodyTemperature, etHeartRate, etBloodGlucose;

    public AddVitalSignFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vital_sign, container, false);

        etWeight = (EditText) view.findViewById(R.id.etWeight);
        etHeight = (EditText) view.findViewById(R.id.etHeight);
        etSystolic = (EditText) view.findViewById(R.id.etSystolic);
        etDiastolic = (EditText) view.findViewById(R.id.etDiastolic);
        etBloodGlucose = (EditText) view.findViewById(R.id.etBloodGlucose);
        etHeartRate = (EditText) view.findViewById(R.id.etHeartRate);
        etBodyTemperature = (EditText) view.findViewById(R.id.etBodyTemperature);

        Button btnCancelVitalSign = (Button) view.findViewById(R.id.btnCancelVitalSign);
        Button btnSaveVitalSign = (Button) view.findViewById(R.id.btnSaveVitalSign);

        btnSaveVitalSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validation.isNumeric(etWeight.getText().toString(), "Weight", getActivity()) &&
                        Validation.isNumeric(etHeight.getText().toString(), "Height", getActivity()) &&
                        Validation.isNumeric(etSystolic.getText().toString(), "Systolic", getActivity()) &&
                        Validation.isNumeric(etDiastolic.getText().toString(), "Diastolic", getActivity()) &&
                        Validation.isNumeric(etBloodGlucose.getText().toString(), "BloodGlucose", getActivity()) &&
                        Validation.isNumeric(etHeartRate.getText().toString(), "HeartRate", getActivity()) &&
                        Validation.isNumeric(etBodyTemperature.getText().toString(), "BodyTemperature", getActivity())) {
                    saveVitalSign();
                }

            }
        });

        btnCancelVitalSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void saveVitalSign() {
        SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        long uid = sp.getLong("uid", 0);
        User user = EntityManager.findById(User.class, uid);
        if (user != null) {
            VitalSign vs = new VitalSign();
            vs.setUid(user.getId());

            vs.setHeight(etHeight.getText() == null ? "" : etHeight.getText().toString());
            vs.setWeight(etWeight.getText() == null ? "" : etWeight.getText().toString());
            vs.setBloodGlucose(etBloodGlucose.getText() == null ? "" : etBloodGlucose.getText().toString());
            vs.setBloodPressure((etSystolic.getText() == null || etDiastolic.getText() == null) ?
                    "" : etSystolic.getText().toString() + "," + etDiastolic.getText().toString());
            vs.setHeartRate(etHeartRate.getText() == null ? "" : etHeartRate.getText().toString());
            vs.setBodyTemperature(etBodyTemperature.getText() == null ? "" : etBodyTemperature.getText().toString());

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.clear();
            calendar.set(year, month, day);
            vs.setDate(calendar.getTimeInMillis());

            vs.save();
        } else {
            Toast.makeText(getActivity(), "Something wrong! Please log in again!", Toast.LENGTH_SHORT).show();
        }

        getFragmentManager().popBackStack();
    }
}
