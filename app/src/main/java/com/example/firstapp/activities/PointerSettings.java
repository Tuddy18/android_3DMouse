package com.example.firstapp.activities;

import com.example.firstapp.interfaces.IPointerRecorder;
import com.example.firstapp.interfaces.IPointerSettings;

public class PointerSettings implements IPointerSettings {

    private static IPointerSettings instance;
    private String color = "#f4426b";
    private double sensibility = 0.8;
    private double granularity = 1.0;

    private int defaultColorR = 0;
    private int defaultColorG = 0;
    private int defaultColorB = 0;


    public int getDefaultColorR() {
        return defaultColorR;
    }


    public int getDefaultColorG() {
        return defaultColorG;
    }


    public int getDefaultColorB() {
        return defaultColorB;
    }


    public String getColor() {
        return color;
    }


    public double getSensibility() {
        return sensibility;
    }


    public double getGranularity() {
        return granularity;
    }


    public void setColor(String color){
        this.color = color;
    }


    public void setSensibility(double sensibility) {
        this.sensibility = sensibility;
    }


    public void setGranularity(double granularity) {
        this.granularity = granularity;
    }

    public static IPointerSettings getInstance() {
        if(instance == null){
            instance = new PointerSettings();
        }
        return instance;
    }
}
