package com.example.firstapp.activities;

import com.example.firstapp.interfaces.IPointerSettings;

public class PointerSettings implements IPointerSettings {

    private String color = "#f4426b";
    private double sensibility = 0.8;
    private double granularity = 0.8;

    @Override
    public String getColor() {
        return null;
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
