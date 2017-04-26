package com.along.android.healthmanagement.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

/**
 * Created by abc on 2017/4/23.
 */

public class BmiFragment extends Fragment {
    EditText etHeight, etWeight, etAgeBmi;
    String height_string, weight_string;
    TextView tv_bmi_result;
    Button calculate,btnCancelCalculate;
    RadioGroup radioGroupGenderBmi;
    RadioButton male, female, radioButtonGender;
    User user;
    private double result;
    private double weight, height;

    public BmiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle saveInstanceState) {
        //super.onCreate(saveInstanceState);
        View view = inflater.inflate(R.layout.fragment_bmi_calculate, container, false);

        etWeight = (EditText) view.findViewById(R.id.etWeight);
        etHeight = (EditText) view.findViewById(R.id.etHeight);
        etAgeBmi = (EditText) view.findViewById(R.id.etAgeBmi);
        radioGroupGenderBmi = (RadioGroup) view.findViewById(R.id.radioGroupGenderBmi);
        tv_bmi_result = (TextView) view.findViewById(R.id.tv_bmi_result);

        male = (RadioButton) view.findViewById(R.id.radioMaleBmi);
        female = (RadioButton) view.findViewById(R.id.radioFemaleBmi);

        SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        long userId = sp.getLong("uid", 0);
        user = EntityManager.findById(User.class, userId);

        prepopulateFields();

        int selectedId = radioGroupGenderBmi.getCheckedRadioButtonId();
        radioButtonGender = (RadioButton) view.findViewById(selectedId);

        calculate = (Button) view.findViewById(R.id.btnCalculate);
        btnCancelCalculate = (Button) view.findViewById(R.id.btnCancelCalculate);

        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (validateInput()) return;

                calculateBmi();
                double calorieCount = calculateCalorieCount();
                user.setCalorieCount(calorieCount + "");
                user.save();
                tv_bmi_result.setText("BMI Value : " + String.format("%.2f", result) +
                        "\nConsume " + calorieCount + " cal/day");
            }
        });

        btnCancelCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private double calculateCalorieCount() {
        String ageString = etAgeBmi.getText().toString();
        double age = Double.valueOf(ageString);
        double bmr;


        if (radioButtonGender.getText().toString().equals("Male")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }
        return bmr;
    }

    private void calculateBmi() {
        height_string = etHeight.getText().toString();
        weight_string = etWeight.getText().toString();
        height = Double.valueOf(height_string);
        weight = Double.valueOf(weight_string);

        result = weight / (0.0001 * height * height);
    }

    private boolean validateInput() {
        if (etHeight.getText().toString().length() == 0) {
            etHeight.setError("Please input Height");
            return true;
        }
        if (etWeight.getText().toString().length() == 0) {
            etWeight.setError("Please input Weight");
            return true;
        }
        if (etAgeBmi.getText().toString().length() == 0) {
            etAgeBmi.setError("Please input your age");
            return true;
        }
        return false;
    }

    private void prepopulateFields() {
        String age = user.getAge();
        String gender = user.getGender();
        if (null != age && !"".equals(age)) {
            etAgeBmi.setText(age);
        }

        if (male.getText().toString().equals(gender))
            radioGroupGenderBmi.check(R.id.radioMaleBmi);
        else
            radioGroupGenderBmi.check(R.id.radioFemaleBmi);
    }

}

