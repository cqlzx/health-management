package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.MealDetailAdapter;
import com.along.android.healthmanagement.entities.Food;
import com.along.android.healthmanagement.helpers.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealDetailFragment extends BasicFragment {

    public MealDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        Button addFood = (Button) view.findViewById(R.id.meal_detail_add_food);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        List<Food> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Food food = new Food();
            food.setName("Sandwich" + i);
            food.setAmount((long)i);
            food.setCalories((long)100);

            list.add(food);
        }

        MealDetailAdapter adapter = new MealDetailAdapter(getActivity(), list);

        ListView listView = (ListView) view.findViewById(R.id.meal_detail_list);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listView);

        return view;
    }

}
