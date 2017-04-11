package com.along.android.healthmanagement.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Food;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddFoodFragment extends BasicFragment {


    private static final String APP_ID = "08310541";
    private static final String APP_KEY = "b6d4734ba1b80bce25497956cfb60ca9";
    private static final String URL = "https://api.nutritionix.com/v1_1/search/";
    private static final String UPC_URL = "https://api.nutritionix.com/v1_1/item";
    private static final String REQUIRED_FIELDS_IN_RESPONSE = "item_name%2Citem_id%2Cnf_calories%2Cnf_serving_size_qty%2Cnf_serving_size_unit"; //%2C means comma

    //https://api.nutritionix.com/v1_1/item?id=513fc9c7673c4fbc260027af&appId=08310541&appKey=b6d4734ba1b80bce25497956cfb60ca9



    TextView tvItem_name, tvCalory;
    EditText etQuantity, etUnit;
    String foodId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        initLinearLayouts(view);

        return view;
    }

    public void initLinearLayouts(final View view) {

        foodId = getArguments().getString("FoodId");

        AsyncHttpClient client = new AsyncHttpClient();
        String request = UPC_URL + "?id=" + foodId + "&appId=" + APP_ID + "&appKey=" + APP_KEY;
        client.get(request, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                try {
                    String foodName = response.getString("item_name");
                    Double dCalories = response.getDouble("nf_calories");
                    Double dQty = response.getDouble("nf_serving_size_qty");
                    String unit = response.getString("nf_serving_size_unit");

                    float nf_calories_from_fat = Float.parseFloat(response.getString("nf_calories_from_fat").equals("null")?"0":response.getString("nf_calories_from_fat"));
                    float nf_protein = Float.parseFloat(response.getString("nf_protein").equals("null")?"0":response.getString("nf_protein"));
                    float nf_total_carbohydrate = Float.parseFloat(response.getString("nf_total_carbohydrate").equals("null")?"0":response.getString("nf_total_carbohydrate"));


                    int fat = (int)(nf_calories_from_fat/dCalories*100);
                    int protein = (int)((100-fat)*nf_protein/(nf_protein+nf_total_carbohydrate));
                    int carbohydrate = 100-fat-protein;


                    tvItem_name = (TextView) view.findViewById(R.id.tvItem_name);
                    tvItem_name.setText(foodName);
                    etQuantity = (EditText) view.findViewById(R.id.etQuantity);
                    etQuantity.setText(dQty.toString());
                    etUnit = (EditText) view.findViewById(R.id.etUnit);
                    etUnit.setText(unit);
                    tvCalory = (TextView) view.findViewById(R.id.tvCalory);
                    tvCalory.setText(dCalories+"");



                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                    if(carbohydrate == 0)progressBar.setProgress(1);else progressBar.setProgress(carbohydrate);
                    TextView tvProgressBar = (TextView) view.findViewById(R.id.tvProgressBar);
                    tvProgressBar.setText(carbohydrate+"%");

                    ProgressBar progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
                    if(protein == 0)progressBar2.setProgress(1);else progressBar2.setProgress(protein);
                    TextView tvProgressBar2 = (TextView) view.findViewById(R.id.tvProgressBar2);
                    tvProgressBar2.setText(protein+"%");

                    ProgressBar progressBar3 = (ProgressBar) view.findViewById(R.id.progressBar3);
                    if(fat == 0)progressBar3.setProgress(1);else progressBar3.setProgress(fat);
                    TextView tvProgressBar3 = (TextView) view.findViewById(R.id.tvProgressBar3);
                    tvProgressBar3.setText(fat+"%");


                    Long calories = Math.round(dCalories);
                    Long qty = Math.round(dQty);
                    Food food = new Food();
                    food.setFoodId(foodId);
                    food.setName(foodName);
                    food.setCalories(calories);
                    food.setAmount(qty);
                    food.setUnit(unit);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnAdd = (Button)view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //foodAddedToMealAdapter.add(food);
            }
        });
    }

}
