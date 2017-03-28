package com.along.android.healthmanagement.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Medicine;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicineFormFragment extends BasicFragment implements TextWatcher {
    public static final int THRESHOLD = 3;
    private static final List<String> COUNTRIES = null;
    ArrayAdapter<String> adapter;
    OnMedicineAddedListener mCallback;
    ProgressDialog prgDialog;
    AutoCompleteTextView autoCompleteTextView;
    List<Medicine> autoCompleteMedicines;
    TextView rxcui;

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

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);

        rxcui = (TextView) view.findViewById(R.id.tvRxcui);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line);
        adapter.setNotifyOnChange(true);

        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.etMedicineName);
        autoCompleteTextView.setThreshold(THRESHOLD);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.addTextChangedListener(this);
        /*autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {

                for(Medicine medicine : autoCompleteMedicines) {
                    if(medicine.equals(adapter.getItem(position))) {
                        TextView rxcui = (TextView) view.findViewById(R.id.tvRxcui);
                        rxcui.setText(medicine.getRxcui());
                        Log.e("Rxcui", medicine.getRxcui());
                        break;
                    }
                }
            }
        });*/

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
                String medicineName = autoCompleteTextView.getText().toString();
                if (!medicineName.equals("") &&
                        !medicineFrequency.getSelectedItem().toString().equals("") &&
                        !spinner.getSelectedItem().toString().equals("") &&
                        !medicineTimings.getText().toString().equals("")) {

                    Medicine medicine = new Medicine();
                    medicine.setName(medicineName);
                    medicine.setFrequency(medicineFrequency.getSelectedItem().toString());
                    medicine.setQuantity(spinner.getSelectedItem().toString());
                    medicine.setTimings(medicineTimings.getText().toString());
                    medicine.setRxcui(rxcui.getText().toString());
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() >= THRESHOLD) {
            // If typed/selected item is equal to the item in the list then don't make a service call
            if (null != autoCompleteMedicines) {
                for (Medicine medicine : autoCompleteMedicines) {
                    if (s.toString().equals(medicine.getName())) {
                        rxcui.setText(medicine.getRxcui());
                        Log.e("Rxcui", medicine.getRxcui());
                        return;
                    }
                }
            }
            // Get the auto complete medicines by making a service call
            getAutoCompleteMedicines(s.toString());
        }
    }

    private void getAutoCompleteMedicines(String medicineName) {
        prgDialog.show();
        autoCompleteMedicines = new ArrayList<Medicine>();

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("terms", medicineName);
        params.put("ef", "STRENGTHS_AND_FORMS,RXCUIS");

        client.get("https://clin-table-search.lhc.nlm.nih.gov/api/rxterms/v3/search", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                prgDialog.hide();
                try {
                    JSONArray responseMedicineNames = response.getJSONArray(1);
                    JSONArray rxcuis = response.getJSONObject(2).getJSONArray("RXCUIS");

                    adapter.clear();

                    for (int i = 0; i < responseMedicineNames.length(); i++) {
                        Medicine med = new Medicine();
                        med.setName(responseMedicineNames.getString(i));
                        med.setRxcui(rxcuis.getJSONArray(i).getString(0));
                        adapter.add(med.getName());
                        autoCompleteMedicines.add(med);
                    }

                    autoCompleteTextView.showDropDown();

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();
            }
        });
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

    public interface OnMedicineAddedListener {
        public void onMedicineAdded(Long medicineId);
    }
}
