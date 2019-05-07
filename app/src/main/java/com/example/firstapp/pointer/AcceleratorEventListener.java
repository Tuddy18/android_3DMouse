package com.example.firstapp.pointer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.firstapp.interfaces.IPointerRecorder;
import com.example.firstapp.interfaces.IPointerSettings;

public class AcceleratorEventListener implements SensorEventListener {
    private double lastUpdate = 0;

    private double velocityX = 0;
    private double positionX = 0;
    private double velocityY = 0;
    private double positionY = 0;
    private double velocityZ = 0;
    private double positionZ = 0;

    private IPointerSettings pointerSettings;
    private IPointerRecorder pointerRecorder;

    public AcceleratorEventListener(IPointerSettings pointerSettings, IPointerRecorder pointerRecorder) {
        this.pointerSettings = pointerSettings;
        this.pointerRecorder = pointerRecorder;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        double sensibility = pointerSettings.getSensibility();

        if(lastUpdate == 0){
            lastUpdate = System.currentTimeMillis();
        }

        if (mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            double curTime = System.currentTimeMillis();
            double diffTime = curTime - lastUpdate;
            lastUpdate = curTime;
            double accelerationX = Math.round(sensorEvent.values[0] * sensibility) / sensibility;
            double accelerationY = Math.round(sensorEvent.values[1] * sensibility) / sensibility;
            double accelerationZ = Math.round(sensorEvent.values[2] * sensibility) / sensibility;

            if(pointerRecorder.isDrawing() || pointerRecorder.isMoving()){
                velocityX = velocityX + diffTime * accelerationX * 0.001;
                positionX = positionX + diffTime * velocityX * 100 * 0.001;
                positionX = positionY * pointerSettings.getGranularity();

                velocityY = velocityY + diffTime * accelerationY * 0.001;
                positionY = positionY + diffTime * velocityY * 100 * 0.001;
                positionY = positionY * pointerSettings.getGranularity();

                velocityZ = velocityZ + diffTime * accelerationZ * 0.001;
                positionZ = positionZ + diffTime * velocityZ * 100 * 0.001;
                positionZ = positionZ * pointerSettings.getGranularity();

                pointerRecorder.recordPosition(positionX, positionY, positionZ);
            }
            else{
                velocityX = 0;
                velocityY = 0;
                velocityZ = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
