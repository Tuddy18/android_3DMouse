package com.example.firstapp.interfaces;

public interface IPointerRecorder {
    void recordPosition(double x, double y, double z);
    boolean isDrawing();
    boolean isMoving();
    void setIsDrawing(boolean value);
    void setIsMoving(boolean value);
    void reposition();
}
