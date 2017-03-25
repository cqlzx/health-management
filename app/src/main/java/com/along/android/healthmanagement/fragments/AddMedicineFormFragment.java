package com.along.android.healthmanagement.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFormFragment extends BasicFragment{

    OnMedicineAddedListener mCallback;
    ProgressDialog prgDialog;

    public AddMedicineFormFragment() {
        // Required empty public constructor
    }

    private static final List<String> COUNTRIES = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medicine, container, false);

        Button btnSaveMedicine = (Button) view.findViewById(R.id.btnSaveMedicine);
        Button btnCancelMedicine = (Button) view.findViewById(R.id.btnCancelMedicine);

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        List<String> medicines = getMedicineNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, medicines);
        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.etMedicineName);
        textView.setThreshold(3);
        textView.setAdapter(adapter);

        final EditText medicineName = (EditText) view.findViewById(R.id.etMedicineName);

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
                if (!medicineName.getText().toString().equals("") &&
                        !medicineFrequency.getSelectedItem().toString().equals("") &&
                        !spinner.getSelectedItem().toString().equals("") &&
                        !medicineTimings.getText().toString().equals("")) {

                    Medicine medicine = new Medicine();
                    medicine.setName(medicineName.getText().toString());
                    medicine.setFrequency(medicineFrequency.getSelectedItem().toString());
                    medicine.setQuantity(spinner.getSelectedItem().toString());
                    medicine.setTimings(medicineTimings.getText().toString());
                    Long medicineId = medicine.save();

                    mCallback.onMedicineAdded(medicineId);
                    getFragmentManager().popBackStack();
                }
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

    private List<String> getMedicineNames() {
        prgDialog.show();
        final List<String> medicineNames = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://rxnav.nlm.nih.gov/REST/displaynames.json",null ,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                prgDialog.hide();
                try {
                    JSONObject termsList = response.getJSONObject("displayTermsList");
                    JSONArray termArray = termsList.getJSONArray("term");
                    for(int i=0; i<termArray.length(); i++) {
                        medicineNames.add(termArray.getString(i));
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

        });

        return medicineNames;
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
