package com.example.firstapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.firstapp.R;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton myButton;
    public static boolean drawing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        myButton = findViewById(R.id.fab);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.textView)).setText("It works!");
            }
        });


        Button positionButton = findViewById(R.id.position);
        Drawable rounded = getResources().getDrawable(R.drawable.roundedbutton);
        positionButton.setBackground(rounded);



        positionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Pressed
                    MainActivity.drawing = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    MainActivity.drawing = false;
                }
                return true;
            }
        });

        final SeekBar slider = findViewById(R.id.slider);
        slider.setMax(50);

        final TextView accText = findViewById(R.id.acc_text);
        accText.setTextColor(Color.LTGRAY);
        accText.setTextSize(16);


        final TextView sliderText = findViewById(R.id.sliderText);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor acc = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        final SensorEventListener accListener = new SensorEventListener() {
            private double lastUpdate = 0;

            private double velocityX = 0;
            private double positionX = 0;
            private double velocityY = 0;
            private double positionY = 0;
            private double velocityZ = 0;
            private double positionZ = 0;

            private double sensibility = 0.8;

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Sensor mySensor = sensorEvent.sensor;
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
                    if(MainActivity.drawing){
                        velocityX = velocityX + diffTime * accelerationX * 0.001;
                        positionX = positionX + diffTime * velocityX * 100 * 0.001;

                        velocityY = velocityY + diffTime * accelerationY * 0.001;
                        positionY = positionY + diffTime * velocityY * 100 * 0.001;

                        velocityZ = velocityZ + diffTime * accelerationZ * 0.001;
                        positionZ = positionZ + diffTime * velocityZ * 100 * 0.001;
                    }
                    else{
                        velocityX = 0;
                        velocityY = 0;
                        velocityZ = 0;

                        //sensibility = slider.getProgress() / 10.0;
                        sensibility = 0.8;
                        sliderText.setTextColor(Color.LTGRAY);
                        //sliderText.setText("Sensibility: " + sensibility);

                    }

                    displayPosition(diffTime);

//                    System.out.println("Your acceleration on x is: " + accelerationX + " m/s^2\n" +
//                            "Your velocity on x is: " + velocityX + " m/s \n" +
//                            "Your position on x is: " + positionX + " cm \n" +
//                            "Time difference: " + diffTime);
//                    System.out.println(String.format("Acc: x: %f\ty: %f\tz: %f\ttime: %d", accelerationX, accelerationY, accelerationZ, curTime));

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            public void displayPosition(double diffTime){
                accText.setText("Your position on x is: \n" + positionX + " cm \n" +
                        "Your position on y is: \n" + positionY + " cm \n" +
                        "Your position on Z is: \n" + positionZ + " cm \n" +
                        "Sample rate: " + diffTime);
            }

            public void reposition(){
                positionX=0;
                positionY=0;
                positionZ=0;
                displayPosition(0);
            }
        };

        Button repositionButton = findViewById(R.id.reposition);
        repositionButton.setBackground(rounded);
        repositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        sensorManager.registerListener(accListener, acc, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/

        if(item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
