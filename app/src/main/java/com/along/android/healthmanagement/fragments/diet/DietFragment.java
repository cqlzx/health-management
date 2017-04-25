package com.along.android.healthmanagement.fragments.diet;

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
import com.along.android.healthmanagement.entities.Meal;
import com.along.android.healthmanagement.entities.WaterConsumption;
import com.along.android.healthmanagement.fragments.BasicFragment;
import com.along.android.healthmanagement.fragments.DatePickerFragment;
import com.along.android.healthmanagement.helpers.EntityManager;
import com.john.waveview.WaveView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DietFragment extends BasicFragment {

    CollapsingToolbarLayout toolbarLayout;
    NestedScrollView scrollView;
    ImageView imageView,ivCup1,ivCup2,ivCup3,ivCup4,ivCup5,ivCup6,ivCup7,ivCup8;
    RelativeLayout rlTitle;
    TextView tvTitle, tvStartDateInDiet, tvStartDateDiet, tvBreakfastContent,tvLunchContent,tvDinnerContent,tvDietWaterFloz;
    WaveView waveViewCup1,waveViewCup2,waveViewCup3,waveViewCup4,waveViewCup5,waveViewCup6,waveViewCup7,waveViewCup8;
    int cup1ProgressCount,cup2ProgressCount,cup3ProgressCount,cup4ProgressCount,cup5ProgressCount,cup6ProgressCount,cup7ProgressCount,cup8ProgressCount;
    CardView cvBreakfast,cvLunch,cvDinner;
    ImageView ivAddDinner,ivAddLunch,ivAddBreakfast;
    Long mealId;
    String breakfastCalories,lunchCalories,dinnerCalories;
    double waterfloz,waterInvariant;
    Calendar calendar;

    WaterConsumption waterConsumption = new WaterConsumption();

    Long userId,breakfastMealId,lunchMealId,dinnerMealId;
    Long dayTime, changDay;

    public DietFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet, container, false);
        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        userId = sp.getLong("uid", 0);
        initializeDietListData(view);

        return view;
    }
    private void initializeDietListData(View view){

        toolbarLayout =(CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitleEnabled(false);

        rlTitle = (RelativeLayout) view.findViewById(R.id.rl_title);
        //  count
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        breakfastCalories = "0";
        lunchCalories = "0";
        dinnerCalories = "0";
        waterfloz = 0.00;
        waterInvariant = 8.45;
        breakfastMealId =null;
        lunchMealId =null;
        dinnerMealId =null;

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        calendar = new GregorianCalendar(year, month, day);

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR , 0);
        calendar.set(Calendar.MINUTE , 0);
        calendar.set(Calendar.SECOND , 0);

        dayTime =calendar.getTimeInMillis();
        // background
        imageView = (ImageView) view.findViewById(R.id.iv_title);
        tvDietWaterFloz = (TextView) view.findViewById(R.id.tvDiet_waterFloz);

        waveViewCup1 = (WaveView) view.findViewById(R.id.wave_view_cup1);
        waveViewCup2 = (WaveView) view.findViewById(R.id.wave_view_cup2);
        waveViewCup3 = (WaveView) view.findViewById(R.id.wave_view_cup3);
        waveViewCup4 = (WaveView) view.findViewById(R.id.wave_view_cup4);
        waveViewCup5 = (WaveView) view.findViewById(R.id.wave_view_cup5);
        waveViewCup6 = (WaveView) view.findViewById(R.id.wave_view_cup6);
        waveViewCup7 = (WaveView) view.findViewById(R.id.wave_view_cup7);
        waveViewCup8 = (WaveView) view.findViewById(R.id.wave_view_cup8);
        ivCup1 = (ImageView) view.findViewById(R.id.diet_cup1);
        ivCup2 = (ImageView) view.findViewById(R.id.diet_cup2);
        ivCup3 = (ImageView) view.findViewById(R.id.diet_cup3);
        ivCup4 = (ImageView) view.findViewById(R.id.diet_cup4);
        ivCup5 = (ImageView) view.findViewById(R.id.diet_cup5);
        ivCup6 = (ImageView) view.findViewById(R.id.diet_cup6);
        ivCup7 = (ImageView) view.findViewById(R.id.diet_cup7);
        ivCup8 = (ImageView) view.findViewById(R.id.diet_cup8);
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

//        waveViewCup1.setProgress(0);
//        waveViewCup2.setProgress(0);
//        waveViewCup3.setProgress(0);
//        waveViewCup4.setProgress(0);
//        waveViewCup5.setProgress(0);
//        waveViewCup6.setProgress(0);
//        waveViewCup7.setProgress(0);
//        waveViewCup8.setProgress(0);


//        // --  test date --
//        StringBuilder sb = new StringBuilder();
//        for (int i = 1; i <= 5; i++) {
//            Food food = new Food();
//            food.setName("Sandwich" + i);
//            food.setAmount((long)i);
//            food.setCalories((long)100);
//            Long foodId = food.save();
//            sb.append(foodId);
//            if (i != 5) {
//                sb.append(",");
//            }
//        }
//        Meal mockMeal = new Meal();
//        mockMeal.setUid(userId);
//        mockMeal.setDate(dayTime);
//        mockMeal.setType("Breakfast");
//        mockMeal.setFoodIds(sb.toString());
//        mockMeal.save();

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

        String text = months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
        tvStartDateDiet.setText(text);
        tvStartDateInDiet.setText(calendar.getTimeInMillis() + "");


        System.out.println("-Jintian-->>>"+tvStartDateDiet.getText());  // zhege
        System.out.println("-J-->>>"+tvStartDateInDiet.getText());
        System.out.println("-H-->>>"+dayTime);


        // -- display MealCalories
        tvBreakfastContent = (TextView) view.findViewById(R.id.tvDietBreakfastContent);
        tvLunchContent = (TextView) view.findViewById(R.id.tvDiet_LunchContent);
        tvDinnerContent = (TextView) view.findViewById(R.id.tvDiet_DinnerContent);
        // --- display Consumption ---

        uploadMealDate(dayTime);

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
                if (breakfastMealId !=null){
                    MealDetailFragment detailFragment = new MealDetailFragment();
                    Bundle args = new Bundle();
                    args.putLong("mealId", breakfastMealId);
                    detailFragment.setArguments(args);
                    createFragment(detailFragment, "mealDetailFragment");
                }
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
                if (lunchMealId !=null){
                    MealDetailFragment detailFragment = new MealDetailFragment();
                    Bundle args = new Bundle();
                    args.putLong("mealId", lunchMealId);
                    detailFragment.setArguments(args);
                    createFragment(detailFragment, "mealDetailFragment");
                }
            }
        });

        // -- Add Dinner --
        ivAddDinner = (ImageView) view.findViewById(R.id.diet_dinner_addBut);
        ivAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (dinnerMealId !=null){
                    MealDetailFragment detailFragment = new MealDetailFragment();
                    Bundle args = new Bundle();
                    args.putLong("mealId", dinnerMealId);
                    detailFragment.setArguments(args);
                    createFragment(detailFragment, "mealDetailFragment");
                }
            }
        });

        // --
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
        uploadWateDate(dayTime);

//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
////
////                cup1ProgressCount = 100;
////            cup2ProgressCount = 100;
////            cup3ProgressCount = 100;
////            cup4ProgressCount = 100;
////            cup5ProgressCount = 100;
////            cup6ProgressCount = 100;
////            cup7ProgressCount = 100;
////            cup8ProgressCount = 100;
////                doCupClick(1);
////                waveViewCup1.setProgress(0);
////                cup1ProgressCount = 0;
////                waveViewCup2.setProgress(0);
////                cup2ProgressCount = 0;
////                waveViewCup3.setProgress(0);
////                cup3ProgressCount = 0;
////                waveViewCup4.setProgress(0);
////                cup4ProgressCount = 0;
////                waveViewCup5.setProgress(0);
////                cup5ProgressCount = 0;
////                waveViewCup6.setProgress(0);
////                cup6ProgressCount = 0;
////                waveViewCup7.setProgress(0);
////                cup7ProgressCount = 0;
////                waveViewCup8.setProgress(0);
////                cup8ProgressCount = 0;
//
////                waterfloz = 0.00;
////                tvDietWaterFloz.setText(waterfloz + " fl.oz");
////                waterConsumption.setNumber(waterfloz);
////                waterConsumption.save();
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task, 1000);
    }

    public void receiveSelectedDate(Calendar selectedDate) {

        System.out.println("-selected Date-->>>"+selectedDate.getTimeInMillis());
        uploadMealDate(selectedDate.getTimeInMillis());
        uploadWateDate(selectedDate.getTimeInMillis());
        dayTime =selectedDate.getTimeInMillis();
    }

    private void uploadMealDate(Long chengDayTime) {

        List<Meal> meals = EntityManager.find(Meal.class, "uid = ? and date = ?", userId + "", chengDayTime + "");
        if (meals.size() != 0) {
            for (Meal meal : meals) {
                if (meal.getType().equals("Breakfast")) {
                    breakfastCalories = meal.getMealCalories();
                    tvBreakfastContent.setText("Recommended: " + meal.getMealCalories() + " cals");
                    breakfastMealId = meal.getId();

                    System.out.println("-l-->>>" + breakfastCalories);
                } else if (meal.getType().equals("Lunch")) {
                    lunchCalories = meal.getMealCalories();
                    tvLunchContent.setText("Recommended: " + meal.getMealCalories() + " cals");
                    lunchMealId = meal.getId();
                } else if (meal.getType().equals("Dinner")) {
                    dinnerCalories = meal.getMealCalories();
                    tvDinnerContent.setText("Recommended: " + meal.getMealCalories() + " cals");
                    dinnerMealId = meal.getId();
                }
            }
            Long totalCalories = Long.parseLong(breakfastCalories) + Long.parseLong(lunchCalories) + Long.parseLong(dinnerCalories);
            tvTitle.setText(totalCalories + "");

        } else {
            tvBreakfastContent.setText("Recommended: 0 cals");
            tvLunchContent.setText("Recommended: 0 cals");
            tvDinnerContent.setText("Recommended: 0 cals");
            breakfastMealId = null;
            lunchMealId = null;
            dinnerMealId = null;
            tvTitle.setText("0");
        }
    }

    private void uploadWateDate(Long chengDayTime) {

        WaterConsumption getWaterConsumption = EntityManager.findOneBy(WaterConsumption.class, "uid = ? and date = ?", userId + "", chengDayTime + "");
        if (getWaterConsumption != null) {
            waterfloz = getWaterConsumption.getNumber();
            double tag = waterfloz / waterInvariant;
            int t = (int) tag;
            System.out.println("-d-->>>" + tag);
            System.out.println("-e-->>>" + t);
            System.out.println("-w-->>>" + waterfloz);


            if (t == 0) {
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
                waterfloz = 0;
                tvDietWaterFloz.setText(waterfloz + " fl.oz");
            } else {
                autoDoCupClick(t);
            }
        } else {
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
            waterfloz = 0;
            tvDietWaterFloz.setText(waterfloz + " fl.oz");
//            waterConsumption.setNumber(waterfloz);
//            waterConsumption.save();
        }
    }


    private class cupListener implements View.OnClickListener {
            public void onClick(View v) {
                int tag = (int) v.getTag();
                waterConsumption.setUid(userId);
                waterConsumption.setDate(dayTime);
                userDoCupClick(tag);
            }
    }

    private void autoDoCupClick(int tag) {
        switch (tag) {
            case 1: {
                waveViewCup1.setProgress(80);
                cup1ProgressCount = 80;
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
                waterfloz = waterInvariant;
                tvDietWaterFloz.setText(waterfloz + " fl.oz");
                waterConsumption.setNumber(waterfloz);
                waterConsumption.save();
            }
            break;
            case 2: {
                waveViewCup1.setProgress(80);
                cup1ProgressCount = 80;
                waveViewCup2.setProgress(80);
                cup2ProgressCount = 80;
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
                waterfloz = waterInvariant * 2;
                tvDietWaterFloz.setText(waterfloz + " fl.oz");
                waterConsumption.setNumber(waterfloz);
                waterConsumption.save();

            }
            break;
            case 3: {
                waveViewCup1.setProgress(80);
                cup1ProgressCount = 80;
                waveViewCup2.setProgress(80);
                cup2ProgressCount = 80;
                waveViewCup3.setProgress(80);
                cup3ProgressCount = 80;
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
                waterfloz = waterInvariant * 3;
                BigDecimal bd = new BigDecimal(waterfloz);
                double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                tvDietWaterFloz.setText(scaler + " fl.oz");
                waterConsumption.setNumber(scaler);
                waterConsumption.save();
            }
            break;
            case 4: {
                waveViewCup1.setProgress(80);
                cup1ProgressCount = 80;
                waveViewCup2.setProgress(80);
                cup2ProgressCount = 80;
                waveViewCup3.setProgress(80);
                cup3ProgressCount = 80;
                waveViewCup4.setProgress(80);
                cup4ProgressCount = 80;
                waveViewCup5.setProgress(0);
                cup5ProgressCount = 0;
                waveViewCup6.setProgress(0);
                cup6ProgressCount = 0;
                waveViewCup7.setProgress(0);
                cup7ProgressCount = 0;
                waveViewCup8.setProgress(0);
                cup8ProgressCount = 0;
                waterfloz = waterInvariant * 4;
                tvDietWaterFloz.setText(waterfloz + " fl.oz");
                waterConsumption.setNumber(waterfloz);
                waterConsumption.save();
            }
            break;
            case 5: {
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
                waveViewCup6.setProgress(0);
                cup6ProgressCount = 0;
                waveViewCup7.setProgress(0);
                cup7ProgressCount = 0;
                waveViewCup8.setProgress(0);
                cup8ProgressCount = 0;
                waterfloz = waterInvariant * 5;
                tvDietWaterFloz.setText(waterfloz + " fl.oz");
                waterConsumption.setNumber(waterfloz);
                waterConsumption.save();
            }
            break;
            case 6: {
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
                waveViewCup7.setProgress(0);
                cup7ProgressCount = 0;
                waveViewCup8.setProgress(0);
                cup8ProgressCount = 0;
                waterfloz = waterInvariant * 6;
                BigDecimal bd = new BigDecimal(waterfloz);
                double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                tvDietWaterFloz.setText(scaler + " fl.oz");
                waterConsumption.setNumber(scaler);
                waterConsumption.save();
            }
            break;
            case 7: {
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
                waterfloz = waterInvariant * 7;
                waveViewCup8.setProgress(0);
                cup8ProgressCount = 0;
                BigDecimal bd = new BigDecimal(waterfloz);
                double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                tvDietWaterFloz.setText(scaler + " fl.oz");
                waterConsumption.setNumber(scaler);
                waterConsumption.save();
            }
            break;
            case 8: {
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
                waterfloz = waterInvariant * 8;
                tvDietWaterFloz.setText(waterfloz + " fl.oz");
                waterConsumption.setNumber(waterfloz);
                waterConsumption.save();
            }
            break;
            default:
                break;

        }
    }

    private void userDoCupClick(int tag) {

        switch (tag) {
            case 1:
                if (cup1ProgressCount != 0) {
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
                    waterfloz = 0.00;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                } else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waterfloz = waterInvariant;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                }
                break;
            case 2:
                if (cup2ProgressCount != 0) {
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
                    waterfloz = waterInvariant;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                } else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waterfloz = waterInvariant * 2;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                }
                break;
            case 3:
                if (cup3ProgressCount != 0) {
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
                    waterfloz = waterInvariant * 2;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                } else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                    waterfloz = waterInvariant * 3;
                    BigDecimal bd = new BigDecimal(waterfloz);
                    double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                    tvDietWaterFloz.setText(scaler + " fl.oz");
                    waterConsumption.setNumber(scaler);
                    waterConsumption.save();
                }
                break;
            case 4:
                if (cup4ProgressCount != 0) {
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
                    waterfloz = waterInvariant * 3;
                    BigDecimal bd = new BigDecimal(waterfloz);
                    double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                    tvDietWaterFloz.setText(scaler + " fl.oz");
                    waterConsumption.setNumber(scaler);
                    waterConsumption.save();
                } else {
                    waveViewCup1.setProgress(80);
                    cup1ProgressCount = 80;
                    waveViewCup2.setProgress(80);
                    cup2ProgressCount = 80;
                    waveViewCup3.setProgress(80);
                    cup3ProgressCount = 80;
                    waveViewCup4.setProgress(80);
                    cup4ProgressCount = 80;
                    waterfloz = waterInvariant * 4;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                }
                break;
            case 5:
                if (cup5ProgressCount != 0) {
                    waveViewCup5.setProgress(0);
                    cup5ProgressCount = 0;
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                    waterfloz = waterInvariant * 4;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                } else {
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
                    waterfloz = waterInvariant * 5;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                }
                break;
            case 6:
                if (cup6ProgressCount != 0) {
                    waveViewCup6.setProgress(0);
                    cup6ProgressCount = 0;
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                    waterfloz = waterInvariant * 5;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                } else {
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
                    waterfloz = waterInvariant * 6;
                    BigDecimal bd = new BigDecimal(waterfloz);
                    double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                    tvDietWaterFloz.setText(scaler + " fl.oz");
                    waterConsumption.setNumber(scaler);
                    waterConsumption.save();
                }
                break;
            case 7:
                if (cup7ProgressCount != 0) {
                    waveViewCup7.setProgress(0);
                    cup7ProgressCount = 0;
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                    waterfloz = waterInvariant * 6;
                    BigDecimal bd = new BigDecimal(waterfloz);
                    double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                    tvDietWaterFloz.setText(scaler + " fl.oz");
                    waterConsumption.setNumber(scaler);
                    waterConsumption.save();
                } else {
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
                    waterfloz = waterInvariant * 7;
                    BigDecimal bd = new BigDecimal(waterfloz);
                    double scaler = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
                    tvDietWaterFloz.setText(scaler + " fl.oz");
                    waterConsumption.setNumber(scaler);
                    waterConsumption.save();
                }
                break;
            case 8:
                if (cup8ProgressCount != 0) {
                    waveViewCup8.setProgress(0);
                    cup8ProgressCount = 0;
                    waterfloz = waterInvariant * 7;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                } else {
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
                    waterfloz = waterInvariant * 8;
                    tvDietWaterFloz.setText(waterfloz + " fl.oz");
                    waterConsumption.setNumber(waterfloz);
                    waterConsumption.save();
                }
                break;
            default:
                break;
        } //
    }

}
