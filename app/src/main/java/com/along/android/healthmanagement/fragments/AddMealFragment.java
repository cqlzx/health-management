package com.along.android.healthmanagement.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.AutoSuggestedFoodAdapter;
import com.along.android.healthmanagement.entities.Food;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends BasicFragment {

    private static final int THRESHOLD = 3;
    private static final String APP_ID = "08310541";
    private static final String APP_KEY = "b6d4734ba1b80bce25497956cfb60ca9";
    private static final String URL = "https://api.nutritionix.com/v1_1/search/";
    private static final String REQUIRED_FIELDS_IN_RESPONSE = "item_name%2Citem_id%2Cnf_calories%2Cnf_serving_size_qty%2Cnf_serving_size_unit"; //%2C means comma

    private AutoSuggestedFoodAdapter adapter;
    private ProgressDialog prgDialog;
    private ListView listView;
    private List<Food> autoSuggestedFoods;

    public AddMealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        initTitle(view);
        initSearch(view);
        initAutoSuggestFoodAdapter(view);
        return view;
    }

    private void initTitle(View view) {
        String mealType = getArguments().getString("mealType");
        String mealDate = getArguments().getString("mealDate");
        TextView tvMealName = (TextView) view.findViewById(R.id.tv_meal_name);
        tvMealName.setText(mealType);
        TextView tvMealDate = (TextView) view.findViewById(R.id.tv_meal_date);
        tvMealDate.setText(mealDate);
    }

    private void initAutoSuggestFoodAdapter(View view) {
        TextView tvEmptyMsg = (TextView) view.findViewById(R.id.tv_no_food_entered_msg);

        adapter = new AutoSuggestedFoodAdapter(getActivity(), new ArrayList<Food>());
        listView = (ListView) view.findViewById(R.id.auto_suggest_food_list);

        listView.setEmptyView(tvEmptyMsg);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    private void initSearch(View view) {
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(true);

        final LinearLayout llSearchResultSection = (LinearLayout) view.findViewById(R.id.ll_search_result_section);
        final LinearLayout llBarcodeSection = (LinearLayout) view.findViewById(R.id.ll_barcode_section);

        SearchView svSearchFood = (SearchView) view.findViewById(R.id.sv_search_food);
        svSearchFood.setIconified(false);
        svSearchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= THRESHOLD) {
                    // hide barcode section and show search result section
                    llBarcodeSection.setVisibility(View.GONE);
                    llSearchResultSection.setVisibility(View.VISIBLE);

                    getAutoSuggestFoodNames(newText.toString());
                } else {
                    // hide search result section and show barcode section
                    llBarcodeSection.setVisibility(View.VISIBLE);
                    llSearchResultSection.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    private void getAutoSuggestFoodNames(String typedFoodName) {
        prgDialog.show();
        autoSuggestedFoods = new ArrayList<Food>();

        AsyncHttpClient client = new AsyncHttpClient();

        String request = URL + typedFoodName + "?fields=" + REQUIRED_FIELDS_IN_RESPONSE + "&appId=" + APP_ID + "&appKey=" + APP_KEY;

        //https://api.nutritionix.com/v1_1/search/che?fields=item_name%2Citem_id%2Cnf_calories%2Fnf_serving_size_qty%2Fnf_serving_size_unit&appId=08310541&appKey=b6d4734ba1b80bce25497956cfb60ca9
        Log.i("URL", URL + typedFoodName);
        client.get(request, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                prgDialog.hide();
                try {
                    if (null != response.getJSONArray("hits") && response.getJSONArray("hits").length() > 0) {
                        JSONArray hits = response.getJSONArray("hits");

                        adapter.clear();

                        for (int i = 0; i < hits.length(); i++) {
                            String foodId = hits.getJSONObject(i).getJSONObject("fields").getString("item_id");
                            String foodName = hits.getJSONObject(i).getJSONObject("fields").getString("item_name");
                            Double dCalories = hits.getJSONObject(i).getJSONObject("fields").getDouble("nf_calories");
                            Double dQty = hits.getJSONObject(i).getJSONObject("fields").getDouble("nf_serving_size_qty");
                            String unit = hits.getJSONObject(i).getJSONObject("fields").getString("nf_serving_size_unit");

                            Long calories = Math.round(dCalories);
                            Long qty = Math.round(dQty);

                            Food food = new Food();
                            food.setFoodId(foodId);
                            food.setName(foodName);
                            food.setCalories(calories);
                            food.setAmount(qty);
                            food.setUnit(unit);

                            adapter.add(food);
                            autoSuggestedFoods.add(food);
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();
            }
        });
    }
}
