package com.along.android.healthmanagement.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.along.android.healthmanagement.R;

/**
 * Created by abc on 2017/4/23.
 */

public class BmiFragment extends Fragment {
    EditText etHeight, etWeight;
    String height_string, weight_string;
    TextView tv_bmi_result;
    Button Calculate;

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
        tv_bmi_result = (TextView) view.findViewById(R.id.tv_bmi_result);

        Calculate = (Button) view.findViewById(R.id.btnCalculate);

        Calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etHeight.getText().toString().length() == 0) {
                    etHeight.setError("Please input Height");
                    return;
                }
                if (etWeight.getText().toString().length() == 0) {
                    etWeight.setError("Please input Weight");
                    return;
                }
                height_string = etHeight.getText().toString();
                weight_string = etWeight.getText().toString();
                height = Double.valueOf(height_string);
                weight = Double.valueOf(weight_string);

                result = weight * 1.0 / height / height;

                tv_bmi_result.setText("BMI Value : " + result + "You should take XXX ");
            }
        });

        return view;
    }
}

