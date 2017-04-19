package com.along.android.healthmanagement.fragments.medication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.MedicationDetailsActivity;
import com.along.android.healthmanagement.adapters.MedicationSearchResultAdapter;
import com.along.android.healthmanagement.entities.Medicine;
import com.along.android.healthmanagement.entities.Prescription;
import com.along.android.healthmanagement.fragments.BasicFragment;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationSearchFragment extends BasicFragment {
    MedicationSearchResultAdapter adapter;
    List<Prescription> prescriptions = new ArrayList<>();
    public String searchText;

    public MedicationSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_medication_search, container, false);

        if (adapter == null) {
            adapter = new MedicationSearchResultAdapter(getActivity(), this, prescriptions);
        } else {
            adapter.notifyDataSetChanged();
        }


        TextView tvMedicationSearchResultEmpty = (TextView) view.findViewById(R.id.medication_search_result_empty);
        final ListView lvMedicationSearchResult = (ListView) view.findViewById(R.id.medication_search_result_list);
        lvMedicationSearchResult.setEmptyView(tvMedicationSearchResultEmpty);
        lvMedicationSearchResult.setAdapter(adapter);

        lvMedicationSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long prescriptionId = ((Prescription) lvMedicationSearchResult.getItemAtPosition(position)).getId();

                Intent medicationDetailsIntent = new Intent(getActivity(), MedicationDetailsActivity.class);
                medicationDetailsIntent.putExtra("selectedPrescriptionItemId", prescriptionId.toString());
                startActivity(medicationDetailsIntent);
            }
        });



        SearchView svMedicationSearchMain = (SearchView) view.findViewById(R.id.sv_medication_search_main);

        AutoCompleteTextView searchTextContent = (AutoCompleteTextView) svMedicationSearchMain.findViewById(svMedicationSearchMain.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        searchTextContent.setTextSize(16); //Set the text size
        searchTextContent.setGravity(Gravity.BOTTOM);

        svMedicationSearchMain.setIconifiedByDefault(false);
        svMedicationSearchMain.requestFocusFromTouch();
        svMedicationSearchMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    searchMedicationOnTextChange(newText);
                } else {
                    adapter.clear();
                }
                return false;
            }
        });



        TextView tvCancelSearch = (TextView) view.findViewById(R.id.tv_cancel_medication_search);
        tvCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                getFragmentManager().popBackStack();
            }
        });



        return view;
    }

    private void searchMedicationOnTextChange(String text) {
        List<Medicine> allMedicines = EntityManager.findWithQuery(Medicine.class, "select * from Medicine where name like ? order by id desc", "%" + text + "%");
        List<String> pidList = new ArrayList<>();
        Set<Long> pids = new HashSet<>();
        for (Medicine medicine : allMedicines) {
            if (pids.contains(medicine.getPid())) continue;
            pids.add(medicine.getPid());
            pidList.add(medicine.getPid() + "");
        }

        if (pidList.size() != 0) {
            String[] pidArr = new String[pidList.size()];
            adapter.clear();
            prescriptions = EntityManager.findByIds(Prescription.class, pidList.toArray(pidArr));
            adapter.addAll(prescriptions);
            adapter.setText(text);
            adapter.notifyDataSetChanged();
        } else {
            adapter.clear();
        }

    }

}
