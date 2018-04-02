package com.along.android.healthmanagement.fragments.medication;


import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.MedicationDetailsActivity;
import com.along.android.healthmanagement.adapters.MedicationCurrentAdapter;
import com.along.android.healthmanagement.apis.Apis;
import com.along.android.healthmanagement.common.JsonCallback;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.network.BaseResponse;
import com.along.android.healthmanagement.utils.JSONUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationCurrentTabFragment extends Fragment {

    private Fragment mContext;

    private List<Prescription> mPrescriptionList = new ArrayList<>();

    public MedicationCurrentTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication_current, container, false);

        mContext = this;
        initializeMedicationListData(view);

        return view;
    }

    private void initializeMedicationListData(final View view) {
        // Create a list of prescriptions
        /*
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

        // Get the actual data from the database
        for(int i=1; i<=1;i++) {
            Prescription prescription = new Prescription();
            prescription.setMedication(i + ". Rabonik, Telsar, Telnol");
            prescription.setDoctorName("Prescribed by Dr. Nitin Shah");
            prescription.setDisease("for Blood Pressure");
            prescription.setIntakeTimes("9:00,12:00,18:00");
            prescription.setStartDate("1488067200000");
            prescription.setEndDate("1488326400000");
            prescription.setNotificationEnabled(i%2 == 0 ? true : false);

            prescriptions.add(prescription);
            prescription.save();
        }*/
        /*if (EntityManager.count(Prescription.class) == 0) {
            for(int i=1; i<=3;i++) {
                Prescription prescription = new Prescription();
                prescription.setMedication(i + ". Rabonik, Telsar, Telnol");
                prescription.setDoctorName("Prescribed by Dr. Nitin Shah");
                prescription.setDisease("for Blood Pressure");
                prescription.setIntakeTimes("9:00,12:00,18:00");
                prescription.setStartDate("1488067200000");
                prescription.setEndDate("1489996400000");

                prescription.save();
            }
        }*/

        OkGo.<BaseResponse<List<Prescription>>>get(Apis.getCurrentMedication())
                .tag(this)
                .execute(new JsonCallback<BaseResponse<List<Prescription>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<Prescription>>> response) {
                        BaseResponse data = response.body();
                        if (data != null) {
                            mPrescriptionList = (List<Prescription>) data.data;
                            initCurrentList(mPrescriptionList, view);
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<List<Prescription>>> response) {
                        super.onError(response);
                        // 错误的时候取本地
                        List<Prescription> prescriptionList;

                        try {
                            prescriptionList = Prescription.listAll(Prescription.class);
                        } catch (SQLiteException e) {
                            prescriptionList = new ArrayList<>();
                        }
                        initCurrentList(prescriptionList, view);
                    }
                });


        /*List<Prescription> currentPrescription = new ArrayList<Prescription>();
        for (Prescription prescription : prescriptionList) {
            //If today <= endDate, then add to current
            try {
                if (Calendar.getInstance().getTimeInMillis() <= Long.parseLong(prescription.getEndDate())) {
                    currentPrescription.add(prescription);
                }
            } catch (NumberFormatException nfe) {
                currentPrescription.add(prescription);
            }
        }

        MedicationCurrentAdapter medicationCurrentAdapter =
                new MedicationCurrentAdapter(getActivity(), currentPrescription);

        TextView tvEmptyMsg = (TextView) view.findViewById(R.id.tvMLEmptyMsg);
        ListView listView = (ListView) view.findViewById(R.id.current_medication_list);

        listView.setEmptyView(tvEmptyMsg);
        listView.setAdapter(medicationCurrentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicationDetailsActivity medicationDetailActivity = new MedicationDetailsActivity();

                TextView prescriptionId = (TextView) view.findViewById(R.id.tvMLPrescriptionId);

                Intent medicationDetailsIntent = new Intent(getActivity(), MedicationDetailsActivity.class);
                medicationDetailsIntent.putExtra("selectedPrescriptionItemId", prescriptionId.getText().toString());
                startActivity(medicationDetailsIntent);
            }
        });*/
    }

    public void initCurrentList(List<Prescription> prescriptionList, View view) {
        if (prescriptionList == null || view == null) {
            Toast.makeText(mContext.getContext(), "current list error", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Prescription> currentPrescription = new ArrayList<Prescription>();

        for (Prescription prescription : prescriptionList) {
            //If today <= endDate, then add to current
            try {
                if (Calendar.getInstance().getTimeInMillis() <= Long.parseLong(prescription.getEndDate())) {
                    currentPrescription.add(prescription);
                }
            } catch (NumberFormatException nfe) {
                currentPrescription.add(prescription);
            }
        }

        MedicationCurrentAdapter medicationCurrentAdapter =
                new MedicationCurrentAdapter(getActivity(), currentPrescription);

        TextView tvEmptyMsg = (TextView) view.findViewById(R.id.tvMLEmptyMsg);
        ListView listView = (ListView) view.findViewById(R.id.current_medication_list);

        listView.setEmptyView(tvEmptyMsg);
        listView.setAdapter(medicationCurrentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicationDetailsActivity medicationDetailActivity = new MedicationDetailsActivity();

                TextView prescriptionId = (TextView) view.findViewById(R.id.tvMLPrescriptionId);

                Intent medicationDetailsIntent = new Intent(getActivity(), MedicationDetailsActivity.class);
                medicationDetailsIntent.putExtra("selectedPrescriptionItemId", prescriptionId.getText().toString());
                startActivity(medicationDetailsIntent);
            }
        });
    }

}
