package com.along.android.healthmanagement.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.activities.EditProfileActivity;
import com.along.android.healthmanagement.activities.NavigationDrawerActivity;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    Button btn_cancel_profile, btn_editprofile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeMedicationListData(view);

        return view;
    }

    private void initializeMedicationListData(View view){

        // sharePreferences
        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        System.out.println("----->>>>>>>>>>>>");

        String realNameS = sp.getString("realname", null);
        System.out.println(realNameS);

        // name & email
        TextView realNameText = (TextView) view.findViewById(R.id.realname);
        TextView emailText = (TextView) view.findViewById(R.id.email);
//        if (realNameS != null) {
//            realNameText.setText(realNameS);
//        }

        // user informations
        TextView ageText = (TextView) view.findViewById(R.id.age);
        TextView genderText = (TextView) view.findViewById(R.id.gender);
        TextView heightText = (TextView) view.findViewById(R.id.height);
        TextView weightText = (TextView) view.findViewById(R.id.weight);
        TextView phoneText = (TextView) view.findViewById(R.id.phone);

        User user = EntityManager.findById(User.class, userIdS);
        if(user != null){
            emailText.setText(user.getEmail());
            Log.d("--email--->>>>>>>", user.getEmail());
            realNameText.setText(user.getRealname());

            // age
            if (user.getAge() != null && !user.getAge().equals("")){
                ageText.setText(user.getAge());
            }else {
                LinearLayout llAge = (LinearLayout) view.findViewById(R.id.llAge);
                llAge.setVisibility(View.GONE);
            }
            // gender
            if (user.getGender() != null && !user.getGender().equals("")){
                genderText.setText(user.getGender());
            }else {
                LinearLayout llGender = (LinearLayout) view.findViewById(R.id.llGender);
                llGender.setVisibility(View.GONE);
            }
            //Height
            if (user.getHeight() != null && !user.getHeight().equals("")){
                heightText.setText(user.getHeight());
            }else {
                LinearLayout llHeight = (LinearLayout) view.findViewById(R.id.llHeight);
                llHeight.setVisibility(View.GONE);
            }
            //Weight
            if (user.getWeight() != null && !user.getWeight().equals("")){
                weightText.setText(user.getWeight());
            }else {
                LinearLayout llWeight = (LinearLayout) view.findViewById(R.id.llWeight);
                llWeight.setVisibility(View.GONE);
            }
            //Phone
            if (user.getPhonenumber() != null && !user.getPhonenumber().equals("")){
                phoneText.setText(user.getPhonenumber());
            }else {
                LinearLayout llPhone = (LinearLayout) view.findViewById(R.id.llPhone);
                llPhone.setVisibility(View.GONE);
            }
        }

        // button

        btn_editprofile = (Button) view.findViewById(R.id.btn_editprofile);

        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // edit
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
//                intent.putExtra("position", position);
                startActivity(intent);
            }
        });


        btn_cancel_profile = (Button) view.findViewById(R.id.btn_cancel_profile);
        btn_cancel_profile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                // home
                Intent intent = new Intent(getActivity(),NavigationDrawerActivity.class);
//                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}
