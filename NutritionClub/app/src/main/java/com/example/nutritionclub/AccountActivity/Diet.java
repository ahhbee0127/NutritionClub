package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 2/8/2018.
 */

public class Diet {
    String id;
    String image;
    String meal;
    String date;
    String calories;

    public Diet(){}

    public Diet(String id, String image, String meal, String date, String calories) {
        this.id = id;
        this.image = image;
        this.meal = meal;
        this.date = date;
        this.calories = calories;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getMeal() {
        return meal;
    }

    public String getDate() {
        return date;
    }

    public String getCalories() {
        return calories;
    }
}
