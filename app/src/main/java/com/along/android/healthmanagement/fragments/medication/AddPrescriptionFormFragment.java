package com.along.android.healthmanagement.fragments.medication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.ConflictingMedicineAdapter;
import com.along.android.healthmanagement.adapters.MedicineAdapter;
import com.along.android.healthmanagement.apis.Apis;
import com.along.android.healthmanagement.common.JsonCallback;
import com.along.android.healthmanagement.entities.ConflictingMedicine;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.fragments.BasicFragment;
import com.along.android.healthmanagement.fragments.DatePickerFragment;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Utility;
import com.along.android.healthmanagement.network.BaseResponse;
import com.along.android.healthmanagement.network.SimpleResponse;
import com.along.android.healthmanagement.utils.JSONUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPrescriptionFormFragment extends BasicFragment {

    private final int DEFAULT_MIN = 100;

    MedicineAdapter medicineAdapter;
    ConflictingMedicineAdapter conflictingMedicineAdapter;
    EditText etPatientName, etDoctorName, etDisease;
    TextView tvStartDateInMillis, tvEndDateInMillis;
    List<Medicine> medicineList = new ArrayList<Medicine>();
    ListView listViewConflictingMedicines;
    Prescription prescription1;

    public AddPrescriptionFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);

        medicineAdapter = new MedicineAdapter(getActivity(), medicineList);
        ListView listView = (ListView) view.findViewById(R.id.medicine_list);
        listView.setAdapter(medicineAdapter);
        Utility.setListViewHeightBasedOnChildren(listView);

        listViewConflictingMedicines = (ListView) view.findViewById(R.id.conflicting_medicine_list);

        LinearLayout llAddMedicine = (LinearLayout) view.findViewById(R.id.llAddMedicineLink);
        llAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(new AddMedicineFormFragment(), "addMedicineFormFragment");
            }
        });

        LinearLayout llStartDate = (LinearLayout) view.findViewById(R.id.llStartDate);
        llStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate", "startDate");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "startDatePicker");
            }
        });

        LinearLayout llEndDate = (LinearLayout) view.findViewById(R.id.llEndDate);
        llEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate", "endDate");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "endDatePicker");
            }
        });

        Button btnCancelPrescription = (Button) view.findViewById(R.id.btnCancelPrescription);
        btnCancelPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        etPatientName = (EditText) view.findViewById(R.id.etPatientName);
        etDoctorName = (EditText) view.findViewById(R.id.etDoctorName);
        etDisease = (EditText) view.findViewById(R.id.etDisease);
        tvStartDateInMillis = (TextView) view.findViewById(R.id.tvStartDateInMillis);
        tvEndDateInMillis = (TextView) view.findViewById(R.id.tvEndDateInMillis);

        Button btnSavePrescription = (Button) view.findViewById(R.id.btnSavePrescription);
        btnSavePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all medicine ids and save it into prescription object
                // merge medicine timing of all autoCompleteMedicines and store it in prescription
                // take min frequency of all the autoCompleteMedicines and store it in prescription
                // save prescription
                Log.d("enddata", tvEndDateInMillis.getText().toString());
                if (!etPatientName.getText().toString().equals("") &&
                        !etDoctorName.getText().toString().equals("") &&
                        !etDisease.getText().toString().equals("") &&
                        !tvStartDateInMillis.getText().toString().equals("Start date in millis") &&
                        !tvEndDateInMillis.getText().toString().equals("End date in millis")) {
                    savePrescription();
                }

            }
        });

        // Notify conflicting medicine
        displayConflictingMedicine();

        return view;
    }

    private void displayConflictingMedicine() {
        List<Prescription> prescriptionList = EntityManager.listAll(Prescription.class);
        if (medicineAdapter.getCount() > 1 || (medicineAdapter.getCount() >= 1 && prescriptionList.size() > 0)) {

            String allRxcui = getAllRxcuis(prescriptionList);
            Log.i("Rxcuis: ", allRxcui);
            notifyConflictingMedicine(allRxcui);
        }
    }

    @NonNull
    private String getAllRxcuis(List<Prescription> prescriptionList) {
        String allRxcui = "";

        // Get rxcuis of all the medicines existing in the prescription
        for (int i = 0; i < prescriptionList.size(); i++) {
            allRxcui += prescriptionList.get(i).getRxcuis();
            if (i != (prescriptionList.size() - 1)) {
                allRxcui += ",";
            }
            allRxcui = allRxcui.replace(",", "+");
        }
        allRxcui += "+";

        // Get rxcuis of all the medicines in the current prescription
        for (int i = 0; i < medicineAdapter.getCount(); i++) {
            allRxcui += medicineAdapter.getItem(i).getRxcui();
            if (i != (medicineAdapter.getCount() - 1)) {
                allRxcui += "+";
            }
        }
        return allRxcui;
    }

    private void savePrescription() {
        int count = medicineAdapter.getCount();

        Map<String, String> medicines = new HashMap<String, String>();
        //Set<String> medicines = new HashSet<String>();
        Set<String> timings = new HashSet<String>();
        Set<String> mids = new HashSet<String>();
        int minFrequency = DEFAULT_MIN;
        Medicine medicine;
        for (int i = 0; i < count; i++) {
            medicine = medicineAdapter.getItem(i);

            medicines.put(medicine.getRxcui(), medicine.getName());
            timings.add(medicine.getTimings());
            mids.add(medicine.getMid() + "");

            int frequency = Integer.parseInt(medicine.getFrequency());
            if (frequency < minFrequency) {
                minFrequency = frequency;
            }
        }

        Prescription prescription = new Prescription();
        prescription.setPatientName(null != etPatientName.getText() ? etPatientName.getText().toString() : "");
        prescription.setDoctorName(null != etDoctorName.getText() ? etDoctorName.getText().toString() : "");
        prescription.setDisease(null != etDisease.getText() ? etDisease.getText().toString() : "");
        prescription.setStartDate(tvStartDateInMillis.getText().toString());
        prescription.setEndDate(tvEndDateInMillis.getText().toString());
        prescription.setFrequency(minFrequency + "");
        prescription.setIntakeTimes(android.text.TextUtils.join(",", timings));
        prescription.setMids(android.text.TextUtils.join(",", mids));
        prescription.setMedication(android.text.TextUtils.join(",", medicines.values()));
        prescription.setRxcuis(android.text.TextUtils.join(",", medicines.keySet()));

        Long prescriptionId = prescription.save();

        OkGo.<BaseResponse<Long>>post(Apis.postPrescriptionUrl())
                .tag(this)
                .upJson(JSONUtil.toJSONObject(prescription))
                .execute(new JsonCallback<BaseResponse<Long>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<Long>> response) {
                        BaseResponse<Long> data = response.body();
                        if (data != null) {
                            // prescription1 = (Prescription) data.data;
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<Long>> response) {
                        super.onError(response);
                    }
                });

        // Update the prescriptionId in the medicine table
        /*for (int i = 0; i < count; i++) {
            // local
            medicine = medicineAdapter.getItem(i);
            Medicine med = EntityManager.findById(Medicine.class, medicine.getId());
            med.setPid(prescriptionId);
            med.save();

            // network
            med.setPid(prescription1.getId());
            OkGo.<BaseResponse<Medicine>>post(Apis.postMedicineUrl())
                    .tag(this)
                    .upJson(JSONUtil.toJSONObject(med))
                    .execute(new JsonCallback<BaseResponse<Medicine>>() {
                        @Override
                        public void onSuccess(Response<BaseResponse<Medicine>> response) {
                            BaseResponse<Medicine> data = response.body();
                            if (data != null) {
                            }
                        }

                        @Override
                        public void onError(Response<BaseResponse<Medicine>> response) {
                            super.onError(response);
                        }
                    });
        }*/

        getFragmentManager().popBackStack();
    }

    public void addMedicineToList(Medicine medicine) {
        // Medicine medicine = EntityManager.findById(Medicine.class, medicineId);
        medicineAdapter.add(medicine);
    }

    private void notifyConflictingMedicine(String rxcuis) {
        final List<ConflictingMedicine> conflictingMedicines = new ArrayList<ConflictingMedicine>();
        AsyncHttpClient client = new AsyncHttpClient();

        //https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=207106+152923+656659
        client.get("https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=" + rxcuis, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("fullInteractionTypeGroup")) {
                        JSONArray fullInteractionType = response.getJSONArray("fullInteractionTypeGroup").getJSONObject(0).getJSONArray("fullInteractionType");
                        for (int i = 0; i < fullInteractionType.length(); i++) {
                            JSONArray minConcept = fullInteractionType.getJSONObject(i).getJSONArray("minConcept");

                            Medicine medicine1 = new Medicine();
                            medicine1.setRxcui(minConcept.getJSONObject(0).getString("rxcui"));
                            medicine1.setName(minConcept.getJSONObject(0).getString("name"));

                            Medicine medicine2 = new Medicine();
                            medicine2.setRxcui(minConcept.getJSONObject(1).getString("rxcui"));
                            medicine2.setName(minConcept.getJSONObject(1).getString("name"));

                            JSONArray interactionPair = fullInteractionType.getJSONObject(i).getJSONArray("interactionPair");
                            JSONObject sourceConceptItem = interactionPair.getJSONObject(0).getJSONArray("interactionConcept").getJSONObject(0).getJSONObject("sourceConceptItem");
                            String externalUrl = sourceConceptItem.getString("url");

                            ConflictingMedicine pair = new ConflictingMedicine();
                            pair.setMedicine1(medicine1);
                            pair.setMedicine2(medicine2);
                            pair.setExternalUrl(externalUrl);

                            conflictingMedicines.add(pair);
                        }
                        conflictingMedicineAdapter = new ConflictingMedicineAdapter(getActivity(), conflictingMedicines);
                        listViewConflictingMedicines.setAdapter(conflictingMedicineAdapter);
                        Utility.setListViewHeightBasedOnChildren(listViewConflictingMedicines);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();
                    Log.e("Error Message: ", e.getMessage());
                }
            }

        });
    }
}
