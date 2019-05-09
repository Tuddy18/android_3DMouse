package com.example.firstapp.interfaces;

import java.io.Serializable;

public interface IPointerSettings extends Serializable {
    String getColor();

    double getSensibility();

    double getGranularity();

    void setColor(String color);

    void setSensibility(double sensibility);

    void setGranularity(double granularity);

    int getDefaultColorR();

    int getDefaultColorG();

    int getDefaultColorB();
}
