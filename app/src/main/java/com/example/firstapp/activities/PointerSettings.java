package com.example.firstapp.activities;

import com.example.firstapp.interfaces.IPointerSettings;

public class PointerSettings implements IPointerSettings {

    private String color = "#f4426b";
    private double sensibility = 0.8;
    private double granularity = 1.0;

    private int defaultColorR = 0;
    private int defaultColorG = 0;
    private int defaultColorB = 0;

    @Override
    public int getDefaultColorR() {
        return defaultColorR;
    }

    @Override
    public int getDefaultColorG() {
        return defaultColorG;
    }

    @Override
    public int getDefaultColorB() {
        return defaultColorB;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public double getSensibility() {
        return sensibility;
    }

    @Override
    public double getGranularity() {
        return granularity;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void setSensibility(double sensibility) {
        this.sensibility = sensibility;
    }

    @Override
    public void setGranularity(double granularity) {
        this.granularity = granularity;
    }
}
