package com.along.android.healthmanagement.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.along.android.healthmanagement.R;
import com.john.waveview.WaveView;

import java.util.Calendar;

import static com.loopj.android.http.AsyncHttpClient.log;


/**
 * A simple {@link Fragment} subclass.
 */
public class DietFragment extends BasicFragment {

    CollapsingToolbarLayout toolbarLayout;
    NestedScrollView scrollView;
    ImageView imageView,ivCup1,ivCup2,ivCup3,ivCup4,ivCup5,ivCup6,ivCup7,ivCup8;
    RelativeLayout rlTitle;
    TextView tvTitle, tvStartDateInDiet, tvStartDateDiet;
    WaveView waveViewCup1,waveViewCup2,waveViewCup3,waveViewCup4,waveViewCup5,waveViewCup6,waveViewCup7,waveViewCup8;
    int cup1ProgressCount,cup2ProgressCount,cup3ProgressCount,cup4ProgressCount,cup5ProgressCount,cup6ProgressCount,cup7ProgressCount,cup8ProgressCount;
    CardView cvBreakfast,cvLunch,cvDinner;
    ImageView ivAddDinner,ivAddLunch,ivAddBreakfast;
    public DietFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        initializeDietListData(view);

        return view;
    }
    private void initializeDietListData(View view){

        // sharePreferences
        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        Long userIdS = sp.getLong("uid", 0);
        System.out.println("----->>>>>>>>>>>>");

//        Meal meal = EntityManager.findById(Meal.class, userIdS);


        toolbarLayout =(CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitleEnabled(false);

        rlTitle = (RelativeLayout) view.findViewById(R.id.rl_title);
        //  count
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        // background
        imageView = (ImageView) view.findViewById(R.id.iv_title);

        waveViewCup1 = (WaveView) view.findViewById(R.id.wave_view_cup1);
        waveViewCup2 = (WaveView) view.findViewById(R.id.wave_view_cup2);
        waveViewCup3 = (WaveView) view.findViewById(R.id.wave_view_cup3);
        waveViewCup4 = (WaveView) view.findViewById(R.id.wave_view_cup4);
        waveViewCup5 = (WaveView) view.findViewById(R.id.wave_view_cup5);
        waveViewCup6 = (WaveView) view.findViewById(R.id.wave_view_cup6);
        waveViewCup7 = (WaveView) view.findViewById(R.id.wave_view_cup7);
        waveViewCup8 = (WaveView) view.findViewById(R.id.wave_view_cup8);


        waveViewCup1.setProgress(0);
        waveViewCup2.setProgress(0);
        waveViewCup3.setProgress(0);
        waveViewCup4.setProgress(0);
        waveViewCup5.setProgress(0);
        waveViewCup6.setProgress(0);
        waveViewCup7.setProgress(0);
        waveViewCup8.setProgress(0);

        ivCup1 = (ImageView) view.findViewById(R.id.diet_cup1);
        ivCup2 = (ImageView) view.findViewById(R.id.diet_cup2);
        ivCup3 = (ImageView) view.findViewById(R.id.diet_cup3);
        ivCup4 = (ImageView) view.findViewById(R.id.diet_cup4);
        ivCup5 = (ImageView) view.findViewById(R.id.diet_cup5);
        ivCup6 = (ImageView) view.findViewById(R.id.diet_cup6);
        ivCup7 = (ImageView) view.findViewById(R.id.diet_cup7);
        ivCup8 = (ImageView) view.findViewById(R.id.diet_cup8);

        cup1ProgressCount = 0;
        cup2ProgressCount = 0;
        cup3ProgressCount = 0;
        cup4ProgressCount = 0;
        cup5ProgressCount = 0;
        cup6ProgressCount = 0;
        cup7ProgressCount = 0;
        cup8ProgressCount = 0;

        ivCup1.setOnClickListener(new cupListener());
        ivCup1.setTag(1);
        ivCup2.setOnClickListener(new cupListener());
        ivCup2.setTag(2);
        ivCup3.setOnClickListener(new cupListener());
        ivCup3.setTag(3);
        ivCup4.setOnClickListener(new cupListener());
        ivCup4.setTag(4);
        ivCup5.setOnClickListener(new cupListener());
        ivCup5.setTag(5);
        ivCup6.setOnClickListener(new cupListener());
        ivCup6.setTag(6);
        ivCup7.setOnClickListener(new cupListener());
        ivCup7.setTag(7);
        ivCup8.setOnClickListener(new cupListener());
        ivCup8.setTag(8);

        // ---- date picker ----
        LinearLayout llStartDate = (LinearLayout) view.findViewById(R.id.llDietStartDate);
        llStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle data = new Bundle();
                data.putString("whichDate", "Today");
                newFragment.setArguments(data);
                newFragment.show(getFragmentManager(), "startDatePicker");
            }
        });
        tvStartDateInDiet = (TextView) view.findViewById(R.id.tvStartDateInDiet);

        // ---- set default start date as TODAY ----
        tvStartDateDiet = (TextView) view.findViewById(R.id.tvStartDateDiet);
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Calendar calendar = Calendar.getInstance();
        String text = months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
        tvStartDateDiet.setText(text);
        tvStartDateInDiet.setText(calendar.getTimeInMillis() + "");

        // ---------------------------------------------------------------------------------
        //  --  Add Breakfast  --
        ivAddBreakfast = (ImageView) view.findViewById(R.id.diet_breakfast_addBut);
        ivAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMealFragment addMealFragment = new AddMealFragment();
                Bundle args = new Bundle();
                args.putString("mealType", "Breakfast");
                args.putString("mealDate", tvStartDateDiet.getText().toString());
                args.putString("mealDateInMillis", tvStartDateInDiet.getText().toString());
                addMealFragment.setArguments(args);
                createFragment(addMealFragment, "addMealFragment");
            }
        });

        // - Breakfast Detail -
        cvBreakfast = (CardView)view.findViewById(R.id.diet_card_view_breakfast);

        cvBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // -- Add Lunch --
        ivAddLunch = (ImageView) view.findViewById(R.id.diet_luch_addBut);
        ivAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMealFragment addMealFragment = new AddMealFragment();
                Bundle args = new Bundle();
                args.putString("mealType", "Lunch");
                args.putString("mealDate", tvStartDateDiet.getText().toString());
                args.putString("mealDateInMillis", tvStartDateInDiet.getText().toString());
                addMealFragment.setArguments(args);
                createFragment(addMealFragment, "addMealFragment");
            }
        });

        // - Lunch Detail -
        cvLunch = (CardView)view.findViewById(R.id.diet_card_view_lunch);

        cvLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // -- Add Dinner --

        ivAddDinner = (ImageView) view.findViewById(R.id.diet_dinner_addBut);
        ivAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.d("---- >  haha", "xx");
                AddMealFragment addMealFragment = new AddMealFragment();
                Bundle args = new Bundle();
                args.putString("mealType", "Dinner");
                args.putString("mealDate", tvStartDateDiet.getText().toString());
                args.putString("mealDateInMillis", tvStartDateInDiet.getText().toString());
                addMealFragment.setArguments(args);
                createFragment(addMealFragment, "addMealFragment");
            }
        });
        // - Dinner Detail -
        cvDinner = (CardView)view.findViewById(R.id.diet_card_view_dinner);

        cvDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.d("---- >  nono","YY");
            }
        });





//
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float height = ((float)200 + verticalOffset)/ 200 * 200 + 100;

                CollapsingToolbarLayout.LayoutParams params =
                        (CollapsingToolbarLayout.LayoutParams) rlTitle.getLayoutParams(); // 取控件mGrid当前的布局参数
                params.height = (int)height;// 当控件的高强制设成75象素
                rlTitle.setLayoutParams(params); // 使设置好的布局参数应用到控件mGrid2

                tvTitle.setTextColor(changeAlpha(getResources().getColor(R.color.whiteF1),
                        Math.abs((300 + verticalOffset)*1.0f)/appBarLayout.getTotalScrollRange()));

            }
            public int changeAlpha(int color, float fraction) {
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);
                int alpha = (int) (Color.alpha(color) * fraction);
                return Color.argb(alpha, red, green, blue);
            }
        });

    }

private class cupListener implements View.OnClickListener{
    public void onClick(View v){

        int tag = (int) v.getTag();
        log.d("--haha-->",tag+"");
        switch (tag){
            case 1:
                if (cup1ProgressCount!=0){
                    waveViewCup1.setProgress(0);
                    cup1ProgressCount = 0;
                    waveViewCup2.setProgress(0);
                    cup2ProgressCount = 0;
                    waveViewCup3.setProgress(0);
                    cup3ProgressCount = 0;
                    waveViewCup4.setProgress(0);
                    cup4ProgressCount = 0;
                    waveViewCup5.setProgress(0);
                    cup5ProgressCount = 0;
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                }
                break;
            case 2:
                if (cup2ProgressCount!=0){
                    waveViewCup2.setProgress(0);
                    cup2ProgressCount = 0;
                    waveViewCup3.setProgress(0);
                    cup3ProgressCount = 0;
                    waveViewCup4.setProgress(0);
                    cup4ProgressCount = 0;
                    waveViewCup5.setProgress(0);
                    cup5ProgressCount = 0;
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                }
                break;
            case 3:
                if (cup3ProgressCount!=0){
                    waveViewCup3.setProgress(0);
                    cup3ProgressCount = 0;
                    waveViewCup4.setProgress(0);
                    cup4ProgressCount = 0;
                    waveViewCup5.setProgress(0);
                    cup5ProgressCount = 0;
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                }
                break;
            case 4:
                if (cup4ProgressCount!=0){
                    waveViewCup4.setProgress(0);
                    cup4ProgressCount = 0;
                    waveViewCup5.setProgress(0);
                    cup5ProgressCount = 0;
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                    waveViewCup4.setProgress(80);
                    cup4ProgressCount = 80;
                }
                break;
            case 5:
                if (cup5ProgressCount!=0){
                    waveViewCup5.setProgress(0);
                    cup5ProgressCount = 0;
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                    waveViewCup4.setProgress(80);
                    cup4ProgressCount = 80;
                    waveViewCup5.setProgress(80);
                    cup5ProgressCount = 80;
                }
                break;
            case 6:
                if (cup6ProgressCount!=0){
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                    waveViewCup4.setProgress(80);
                    cup4ProgressCount = 80;
                    waveViewCup5.setProgress(80);
                    cup5ProgressCount = 80;
                    waveViewCup6.setProgress(80);
                    cup6ProgressCount = 80;
                }
                break;
            case 7:
                if (cup7ProgressCount!=0){
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                    waveViewCup4.setProgress(80);
                    cup4ProgressCount = 80;
                    waveViewCup5.setProgress(80);
                    cup5ProgressCount = 80;
                    waveViewCup6.setProgress(80);
                    cup6ProgressCount = 80;
                    waveViewCup7.setProgress(80);
                    cup7ProgressCount = 80;
                }
                break;
            case 8:
                if (cup8ProgressCount!=0){
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                }else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                    waveViewCup4.setProgress(80);
                    cup4ProgressCount = 80;
                    waveViewCup5.setProgress(80);
                    cup5ProgressCount = 80;
                    waveViewCup6.setProgress(80);
                    cup6ProgressCount = 80;
                    waveViewCup7.setProgress(80);
                    cup7ProgressCount = 80;
                    waveViewCup8.setProgress(80);
                    cup8ProgressCount = 80;
                }
                break;
            default:
                break;
        }
    }
}
}
