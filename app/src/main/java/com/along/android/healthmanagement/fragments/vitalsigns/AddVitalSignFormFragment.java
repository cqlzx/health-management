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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    //    private OnFragmentInteractionListener mListener;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddVitalSignFormFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddVitalSignFormFragment newInstance(String param1, String param2) {
//        AddVitalSignFormFragment fragment = new AddVitalSignFormFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
    private EditText etSystolic, etDiastolic, etWeight, etHeight, etBodyTemperature, etHeartRate, etBloodGlucose;

    public AddVitalSignFormFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

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
            vs.setDate(Calendar.getInstance().getTimeInMillis());

            vs.save();
        } else {
            Toast.makeText(getActivity(), "Something wrong! Please log in again!", Toast.LENGTH_SHORT).show();
        }

        getFragmentManager().popBackStack();
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
