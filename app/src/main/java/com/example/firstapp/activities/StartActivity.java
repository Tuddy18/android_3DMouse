package com.example.firstapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.example.firstapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private ListView deviceList;
    private HashMap<String, String> deviceMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        deviceMap = new HashMap<>();
        deviceMap.put("Device1", "gibberish1");
        deviceMap.put("Device2", "gibberish2");
        deviceMap.put("Device3", "gibberish3");

        deviceList = findViewById(R.id.deviceList);

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
    }
}
