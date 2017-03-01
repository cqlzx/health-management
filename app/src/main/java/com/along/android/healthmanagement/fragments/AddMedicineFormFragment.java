package com.along.android.healthmanagement.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFormFragment extends BasicFragment{

    OnMedicineAddedListener mCallback;

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

        final EditText medicineName = (EditText) view.findViewById(R.id.etMedicineName);
        //final EditText medicineQty = (EditText) view.findViewById(R.id.etMedicineQty);

        final Spinner spinner = (Spinner) view.findViewById(R.id.spMedicineQty);
        spinner.getSelectedItem().toString();

        final TextView medicineTimings = (TextView) view.findViewById(R.id.tvTimings);
        final Spinner medicineFrequency = (Spinner) view.findViewById(R.id.spMedicineFrequency);

        TextView tvAddTime = (TextView) view.findViewById(R.id.tvAddTime);
        tvAddTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichTimer", "addMedicineTime");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "addMedicineTime");
            }
        });

        TextView tvResetTime = (TextView) view.findViewById(R.id.tvResetTime);
        final LinearLayout llMedicineTimings = (LinearLayout) view.findViewById(R.id.llMedicineTimings);
        tvResetTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                llMedicineTimings.setVisibility(View.GONE);
                medicineTimings.setText("");
            }
        });

        btnSaveMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine medicine = new Medicine();
                medicine.setName(medicineName.getText().toString());
                medicine.setFrequency(medicineFrequency.getSelectedItem().toString());
                medicine.setQuantity(spinner.getSelectedItem().toString());
                medicine.setTimings(medicineTimings.getText().toString());
                Long medicineId = medicine.save();

                mCallback.onMedicineAdded(medicineId);
                getFragmentManager().popBackStack();
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

    public interface OnMedicineAddedListener {
        public void onMedicineAdded(Long medicineId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnMedicineAddedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMedicineAddedListener");
        }
    }
}
