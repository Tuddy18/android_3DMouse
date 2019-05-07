package com.example.firstapp.interfaces;

public interface IPointerRecorder {
    void recordPosition(int x, int y, int z);
    void setIsDrawing(boolean value);
    void setIsMoving(boolean value);
    void reposition();
}
