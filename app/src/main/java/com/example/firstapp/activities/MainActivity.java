package com.example.firstapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstapp.R;
import com.example.firstapp.execution.BufferManager;
import com.example.firstapp.interfaces.IBufferManager;
import com.example.firstapp.interfaces.IPointerRecorder;
import com.example.firstapp.interfaces.IPointerSettings;
import com.example.firstapp.pointer.AcceleratorEventListener;
import com.example.firstapp.pointer.PointerRecorder;

public class MainActivity extends AppCompatActivity {

    public static boolean drawing = false;
    private IPointerRecorder pointerRecorder;
    private SettingsActivity settingsActivity;
    private IPointerSettings pointerSettings;
    private IBufferManager bufferManager;
    private SensorEventListener acceleratorEventListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    TextView coordinates;

    public MainActivity(){

    }


    private void init() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        pointerSettings = PointerSettings.getInstance();
        pointerRecorder = new PointerRecorder(pointerSettings, BufferManager.getInstance());
        acceleratorEventListener = new AcceleratorEventListener(pointerSettings, pointerRecorder);
        mSensorManager.registerListener(acceleratorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        settingsActivity = new SettingsActivity();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        coordinates = findViewById(R.id.coordinates);
        coordinates = new TextView(this);
        coordinates.setTextColor(Color.LTGRAY);
        coordinates.setTextSize(16);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button positionButton = findViewById(R.id.position);
        Drawable rounded = getResources().getDrawable(R.drawable.roundedbutton);
        positionButton.setBackground(rounded);


        positionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Pressed
                    pointerRecorder.setIsMoving(true);
                    coordinates.setText(pointerRecorder.getCurrentPosition().getX() + " " +
                            pointerRecorder.getCurrentPosition().getY() + " " +
                            pointerRecorder.getCurrentPosition().getZ());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    pointerRecorder.setIsMoving(false);
                }
                return true;
            }
        });

        Button repositionButton = findViewById(R.id.reposition);
        repositionButton.setBackground(rounded);

        repositionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Pressed
                    pointerRecorder.reposition();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    //pointerRecorder.reposition();
                }
                return true;
            }
        });

        Button draw = findViewById(R.id.draw);
        draw.setBackground(rounded);

        draw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Pressed
                    pointerRecorder.setIsDrawing(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    pointerRecorder.setIsDrawing(false);
                }
                return true;
            }
        });

        Button clear = findViewById(R.id.clear);
        clear.setBackground(rounded);

        clear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Pressed
                    //pointerRecorder.
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                }
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), settingsActivity.getClass());
            startActivity(intent);
        } else if(item.getItemId() == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
