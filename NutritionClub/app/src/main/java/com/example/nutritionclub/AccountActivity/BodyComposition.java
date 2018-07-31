package com.example.nutritionclub.AccountActivity;

import java.util.Date;

/**
 * Created by AhhBee on 31/7/2018.
 */

public class BodyComposition {

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

    public BodyComposition(String todayDate, double water, double weight, double fatPercent, int visceralFat, double boneMass, int metabolicAge, double muscle, double fatkg, double bmi) {
        this.todayDate = todayDate;
        this.water = water;
        this.weight = weight;
        this.fatPercent = fatPercent;
        this.visceralFat = visceralFat;
        this.boneMass = boneMass;
        this.metabolicAge = metabolicAge;
        this.muscle = muscle;
        this.fatkg = fatkg;
        this.bmi = bmi;
    }

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
}