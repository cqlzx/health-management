package com.along.android.healthmanagement.helpers;

import com.along.android.healthmanagement.entities.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RitenVithlani on 4/12/17.
 */

public class MealFood {

    private static MealFood mealFood;
    private List<Food> foods;
    private State currentState;

    private MealFood() {
    }

    ;

    public static MealFood getInstance() {
        if (null == mealFood) {
            mealFood = new MealFood();
        }
        return mealFood;
    }

    public State getState() {
        return currentState;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public void addFood(Food food) {
        if (foods == null) {
            foods = new ArrayList<Food>();
        }
        foods.add(food);
    }

    public enum State {
        BARCODE, SEARCH_RESULTS, FOOD_ADDED_FROM_PAGE, FOOD_ADDED_DIRECT
    }
}
