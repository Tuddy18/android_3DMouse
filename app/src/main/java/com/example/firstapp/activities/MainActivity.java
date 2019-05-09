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
    private IPointerSettings pointerSettings;
    private IBufferManager bufferManager;
    private IPointerRecorder pointerRecorder;
    private SensorEventListener acceleratorEventListener;
    private ICommandExecutor executor;
    private IBTConnection btConnection;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;



    private void init() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        pointerSettings = new PointerSettings();
        pointerRecorder = new PointerRecorder(pointerSettings, BufferManager.getInstance());
        acceleratorEventListener = new AcceleratorEventListener(pointerSettings, pointerRecorder);
        mSensorManager.registerListener(acceleratorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        btConnection = new BTConnection();
        executor = new CommandExecutor(btConnection);
        CommandReceiver.getInstance().setExecutor(executor);
        CommandReceiver.getInstance().start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

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

        final TextView accText = findViewById(R.id.acc_text);
        accText.setTextColor(Color.LTGRAY);
        accText.setTextSize(16);

        /*accText.setText(pointerRecorder.getCurrentPosition().getX() + " " +
                pointerRecorder.getCurrentPosition().getY() + " " +
                pointerRecorder.getCurrentPosition().getZ());*/
        accText.setText("hola");

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
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
