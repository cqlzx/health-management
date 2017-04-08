package com.along.android.healthmanagement.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Food;

import java.util.List;

/**
 * Created by RitenVithlani on 2/25/17.
 */

public class AutoSuggestedFoodAdapter extends ArrayAdapter<Food> {

    private static final String DEFAULT_UNIT = "unit";
    private static final String DEFAULT_QTY = "1";

    public AutoSuggestedFoodAdapter(Context context, List<Food> foods) {
        super(context, 0, foods);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final ListView listView = (ListView) parent;
        if (null == convertView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_auto_suggested_food, parent, false);
        }

        final Food food = getItem(position);

        TextView foodId = (TextView) listItemView.findViewById(R.id.tv_food_id);
        foodId.setText(food.getFoodId());

        TextView foodName = (TextView) listItemView.findViewById(R.id.tv_food_name);
        foodName.setText(null != food.getName() ? food.getName() : "");

        TextView calories = (TextView) listItemView.findViewById(R.id.tv_food_calories);
        calories.setText(null != food.getCalories() ? food.getCalories().toString() + " cal " : "");

        TextView foodQty = (TextView) listItemView.findViewById(R.id.tv_food_quantity);
        foodQty.setText(null != food.getAmount() ? food.getAmount().toString() : DEFAULT_QTY);

        TextView foodUnit = (TextView) listItemView.findViewById(R.id.tv_food_unit);
        foodUnit.setText(null != food.getUnit() ? food.getUnit() : DEFAULT_UNIT);

        return listItemView;
    }

}
