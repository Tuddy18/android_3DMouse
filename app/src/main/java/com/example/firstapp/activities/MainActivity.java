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
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.firstapp.R;
import com.example.firstapp.bt.BTConnection;
import com.example.firstapp.command.Command;
import com.example.firstapp.execution.BufferManager;
import com.example.firstapp.execution.CommandExecutor;
import com.example.firstapp.execution.CommandReceiver;
import com.example.firstapp.interfaces.IBTConnection;
import com.example.firstapp.interfaces.IBufferManager;
import com.example.firstapp.interfaces.ICommandExecutor;
import com.example.firstapp.interfaces.IPointerRecorder;
import com.example.firstapp.interfaces.IPointerSettings;
import com.example.firstapp.pointer.AcceleratorEventListener;
import com.example.firstapp.pointer.PointerRecorder;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static boolean drawing = false;
    private IPointerRecorder pointerRecorder;
    private SettingsActivity settingsActivity;
    private IPointerSettings pointerSettings;
    private IBufferManager bufferManager;
    private SensorEventListener acceleratorEventListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public MainActivity(){

    }


    private void init() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        pointerSettings = new PointerSettings();
        pointerRecorder = new PointerRecorder(pointerSettings, BufferManager.getInstance());
        acceleratorEventListener = new AcceleratorEventListener(pointerSettings, pointerRecorder);
        mSensorManager.registerListener(acceleratorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        settingsActivity = new SettingsActivity(pointerSettings);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        /*final TextView textViewX = findViewById(R.id.textViewX);
        textViewX.setTextColor(Color.LTGRAY);
        textViewX.setTextSize(16);

        final TextView textViewY = findViewById(R.id.textViewY);
        textViewY.setTextColor(Color.LTGRAY);
        textViewY.setTextSize(16);

        final TextView textViewZ = findViewById(R.id.textViewZ);
        textViewZ.setTextColor(Color.LTGRAY);
        textViewZ.setTextSize(16);*/

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


        Button positionButton = findViewById(R.id.position);
        Drawable rounded = getResources().getDrawable(R.drawable.roundedbutton);
        positionButton.setBackground(rounded);

        //textViewX.setText(pointerRecorder.getCurrentPosition().getX() + "");
        //textViewY.setText(pointerRecorder.getCurrentPosition().getY() + "");
        //textViewZ.setText(pointerRecorder.getCurrentPosition().getZ() + "");


        positionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Pressed
                    pointerRecorder.setIsMoving(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    pointerRecorder.setIsMoving(false);
                }
                return true;
            }
        });

        /*accText.setText(pointerRecorder.getCurrentPosition().getX() + " " +
                pointerRecorder.getCurrentPosition().getY() + " " +
                pointerRecorder.getCurrentPosition().getZ());*/
        //textViewX.setText(pointerRecorder.getCurrentPosition().getX() + "");

        Button repositionButton = findViewById(R.id.reposition);
        repositionButton.setBackground(rounded);
        repositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button draw = findViewById(R.id.draw);
        draw.setBackground(rounded);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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
            startActivity(new Intent(getApplicationContext(), settingsActivity.getClass()));
        } else if(item.getItemId() == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
