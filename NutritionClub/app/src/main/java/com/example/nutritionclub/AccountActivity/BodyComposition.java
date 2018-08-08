package com.example.nutritionclub.AccountActivity;

import java.util.Date;

/**
 * Created by AhhBee on 31/7/2018.
 */

public class BodyComposition {

    String comment;
    String todayDate;
    double water;
    double weight;
    double fatPercent;
    int visceralFat;
    double boneMass;
    int metabolicAge;
    double muscle;
    double fatkg;
    double bmi;
    double height;
    String bodyId;
    String userId;

    public BodyComposition(String comment, String userId,String bodyId,String todayDate, double water, double weight, double fatPercent, int visceralFat, double boneMass, int metabolicAge, double muscle, double fatkg, double bmi, double height) {
        this.comment = comment;
        this.bodyId = bodyId;
        this.todayDate = todayDate;
        this.water = water;
        this.weight = weight;
        this.fatPercent = fatPercent;
        this.visceralFat = visceralFat;
        this.boneMass = boneMass;
        this.metabolicAge = metabolicAge;
        this.muscle = muscle;
        this.fatkg = fatkg;
        this.userId = userId;
        this.bmi = bmi;
        this.height = height;
    }

    public String getComment() {
        return comment;
    }

    public double getHeight() {
        return height;
    }

    public String getUserId() {
        return userId;
    }

    public BodyComposition(){}

    public double getWater() {
        return water;
    }

    public double getWeight() {
        return weight;
    }

    public double getFatPercent() {
        return fatPercent;
    }

    public int getVisceralFat() {
        return visceralFat;
    }

    public double getBoneMass() {
        return boneMass;
    }

    public int getMetabolicAge() {
        return metabolicAge;
    }

    public double getMuscle() {
        return muscle;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public double getFatkg() {
        return fatkg;
    }

    public double getBmi() {
        return bmi;
    }

    public String getBodyId() {
        return bodyId;
    }
}
