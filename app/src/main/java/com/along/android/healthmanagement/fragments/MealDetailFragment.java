package com.along.android.healthmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.MealDetailAdapter;
import com.along.android.healthmanagement.entities.Food;
import com.along.android.healthmanagement.entities.Meal;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealDetailFragment extends BasicFragment {
    private static String MEAL_ID_STRING = "MEAL_ID_STRING";
    public Long mealId;
    private TextView tv_meal_date, tv_meal_type;

    public MealDetailFragment() {
        // Required empty public constructor
    }

    public static MealDetailFragment newInstance(Long mealId) {
        MealDetailFragment fragment = new MealDetailFragment();
        Bundle args = new Bundle();
        args.putLong(MEAL_ID_STRING, mealId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);




        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            Food food = new Food();
            food.setName("Sandwich" + i);
            food.setAmount((long)i);
            food.setFoodId("123");
            food.setCalories((long)100);
            Long foodId = food.save();
            sb.append(foodId);
            if (i != 5) {
                sb.append(",");
            }
        }
        Meal mockMeal = new Meal();
        mockMeal.setUid((long)10);
        mockMeal.setDate(Calendar.getInstance().getTimeInMillis());
        mockMeal.setType("Breakfast");
        mockMeal.setFoodIds(sb.toString());
        Long mid = mockMeal.save();
        mealId = mid;


        //mealId = getArguments().getLong(MEAL_ID_STRING);
        final Meal meal = EntityManager.findById(Meal.class, mealId);

        tv_meal_type = (TextView) view.findViewById(R.id.tv_meal_detail_type);
        tv_meal_type.setText(meal.getType());

        tv_meal_date = (TextView) view.findViewById(R.id.tv_meal_detail_date);
        tv_meal_date.setText(Utility.convertTimestampToDateFormat(meal.getDate()));

        TextView tv_meal_calories = (TextView) view.findViewById(R.id.tv_meal_detail_calories);
        tv_meal_calories.setText(meal.getMealCalories());


        Button addFood = (Button) view.findViewById(R.id.meal_detail_add_food);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMealFragment addMealFragment = new AddMealFragment();
                Bundle args = new Bundle();
                args.putString("mealType", tv_meal_type.getText().toString());
                args.putString("mealDate", tv_meal_date.getText().toString());
                args.putString("mealDateInMillis", meal.getDate() + "");
                args.putLong("mealId", mealId);
                addMealFragment.setArguments(args);
                createFragment(addMealFragment, "addMealFragment");
            }
        });


        String foodIds = meal.getFoodIds();
        String[] ids = foodIds.split(",");

        List<Food> list = new ArrayList<>();
        for (String id : ids) {
            Long foodId = Long.parseLong(id.trim());
            Food food = EntityManager.findById(Food.class, foodId);
            list.add(food);
        }

        MealDetailAdapter adapter = new MealDetailAdapter(getActivity(), list, this, view);

        ListView listView = (ListView) view.findViewById(R.id.meal_detail_list);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listView);

        return view;
    }

}
