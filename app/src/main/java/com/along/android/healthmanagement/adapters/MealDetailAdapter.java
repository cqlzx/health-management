package com.along.android.healthmanagement.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Food;
import com.along.android.healthmanagement.entities.Meal;
import com.along.android.healthmanagement.entities.VitalSign;
import com.along.android.healthmanagement.fragments.MealDetailFragment;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.along.android.healthmanagement.helpers.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MealDetailAdapter extends ArrayAdapter<Food>{
    private TextView tv_meal_calories;
    private Long mealId;
    private MealDetailFragment mealDetailFragment;

    public MealDetailAdapter(@NonNull Context context, @NonNull List<Food> objects, MealDetailFragment mealDetailFragment, View view) {
        super(context, 0, objects);

        this.mealId = mealDetailFragment.mealId;
        this.mealDetailFragment = mealDetailFragment;
        this.tv_meal_calories = (TextView) view.findViewById(R.id.tv_meal_detail_calories);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        final ListView listView = (ListView) parent;
        final Food food = getItem(position);

        if(null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_meal_detail, parent, false);
        }

        TextView foodName = (TextView) listItemView.findViewById(R.id.meal_detail_food_name);
        foodName.setText(food.getName());

        TextView foodCalsAmount = (TextView) listItemView.findViewById(R.id.meal_detail_food_cals_amount);
        foodCalsAmount.setText(food.getFoodCalories() + " cals     " + "amount : " + food.getAmount());

        ImageView deleteFood = (ImageView) listItemView.findViewById(R.id.meal_detail_delete);
        deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(food, listView);
            }
        });

        return listItemView;
    }

    private void showDeleteAlertDialog(final Food food, final ListView listView) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirm Delete");
        alert.setMessage("Are you sure to delete this food?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                MealDetailAdapter.this.remove(food);
                Utility.setListViewHeightBasedOnChildren(listView);
                dialog.dismiss();

                updateCalories(food);
                boolean isEmpty = deleteFood(food);
                if(isEmpty) {
                    mealDetailFragment.getFragmentManager().popBackStack();
                }
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private boolean deleteFood(Food food) {
        long foodId = food.getId();

        Food foodRecord = EntityManager.findById(Food.class, foodId);
        foodRecord.delete();

        Meal meal = EntityManager.findById(Meal.class, mealId);
        String[] tmp = meal.getFoodIds().split(",");
        List<String> foodIds = Arrays.asList(tmp);

        StringBuilder joinIds = new StringBuilder();
        for (String id : foodIds) {
            if (!id.trim().equals(foodId + "")) {
                id += ",";
                joinIds.append(id);
            }
        }
        if (joinIds.length() != 0) {
            joinIds.delete(joinIds.length() - 1, joinIds.length());
        }


        if (joinIds.length() == 0) {
            meal.delete();
            return true;
        } else {
            meal.setFoodIds(joinIds.toString());
            meal.save();
            return false;
        }
    }

    private void updateCalories(Food food) {
        String preCalories = tv_meal_calories.getText().toString();
        long tmp = Long.parseLong(preCalories) - food.getFoodCalories();
        String curCalories = tmp + "";
        tv_meal_calories.setText(curCalories);
    }
}
