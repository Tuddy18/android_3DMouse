package com.example.firstapp.activities;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.example.firstapp.R;
import com.example.firstapp.bt.BTConnection;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private ListView deviceList;
    private HashMap<String, String> deviceMap;
    private ICommandExecutor executor;
    private IBTConnection btConnection;
    private boolean isDone = false;
    private ProgressBar progressBar;



    private void init() {
        btConnection = new BTConnection();
        executor = new CommandExecutor(btConnection);
        CommandReceiver.getInstance().setExecutor(executor);
        CommandReceiver.getInstance().start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();

        deviceMap = btConnection.getBluetoothDevices();

        deviceList = findViewById(R.id.deviceList);
        progressBar = findViewById(R.id.progressBar);

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems,
                android.R.layout.simple_list_item_2,
                new String[]{"First line", "Second line"},
                new int[]{android.R.id.text1, android.R.id.text2});

        for(String key: deviceMap.keySet()){
            HashMap<String, String> resultMap = new HashMap<>();
            resultMap.put("First line", key);
            resultMap.put("Second line", deviceMap.get(key));
            listItems.add(resultMap);
        }

        deviceList.setAdapter(adapter);
        final Handler mainHandler = new Handler(this.getMainLooper());
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                startProgressThread();
                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                String address = item.get("Second line");

                try {
                    btConnection.connect(address);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stopProgressThread();
            }
        });
    }

    private void startProgressThread() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                while (!isDone) {
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(1);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void stopProgressThread(){
        isDone = true;
    }
}
