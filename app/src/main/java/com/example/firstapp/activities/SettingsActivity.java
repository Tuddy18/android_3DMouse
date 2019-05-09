package com.example.firstapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.firstapp.R;
import com.example.firstapp.interfaces.IPointerSettings;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class SettingsActivity extends AppCompatActivity{

    private IPointerSettings pointerSettings;

    ColorPicker cp;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pointerSettings = PointerSettings.getInstance();
        initColorPicker();

        setContentView(R.layout.settings);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        final SeekBar colorSlider = findViewById(R.id.slider);
        colorSlider.setMax(50);

        final TextView sliderText = findViewById(R.id.sliderText);

        sliderText.setTextColor(Color.LTGRAY);
        sliderText.setTextSize(20);
        sliderText.setText("Color");

        colorSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //pointerSettings.setColor(colorSlider.getProgress() / 10.0);

                pointerSettings.setColor("#f4426b");

                sliderText.setText("Color: " + pointerSettings.getColor());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar sensibilitySlider = findViewById(R.id.slider2);
        sensibilitySlider.setMax(50);

        final TextView sliderText2 = findViewById(R.id.sliderText2);

        sliderText2.setTextColor(Color.LTGRAY);
        sliderText2.setTextSize(20);
        sliderText2.setText("Sensibility");

        sensibilitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointerSettings.setSensibility(sensibilitySlider.getProgress() / 10.0);

                sliderText2.setText("Sensibility: " + pointerSettings.getSensibility());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar granularitySlider = findViewById(R.id.slider3);
        granularitySlider.setMax(50);

        final TextView sliderText3 = findViewById(R.id.sliderText3);

        sliderText3.setTextColor(Color.LTGRAY);
        sliderText3.setTextSize(20);
        sliderText3.setText("Granularity");

        granularitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointerSettings.setGranularity(granularitySlider.getProgress() / 10.0);

                sliderText3.setText("Granularity: " + pointerSettings.getGranularity());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initColorPicker() {
        /* Show color picker dialog */
        this.cp = new ColorPicker(SettingsActivity.this, pointerSettings.getDefaultColorR(),
                pointerSettings.getDefaultColorG(), pointerSettings.getDefaultColorB());
        cp.show();

        cp.enableAutoClose(); // Enable auto-dismiss for the dialog

        /* Set a new Listener called when user click "select" */
        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {

                //"Red", Integer.toString(Color.red(color))
                //"Green", Integer.toString(Color.green(color))
                //"Blue", Integer.toString(Color.blue(color))
                //"Pure Hex", Integer.toHexString(color)
                //"#Hex no alpha", String.format("#%06X", (0xFFFFFF & color))
                //"#Hex with alpha", String.format("#%08X", (0xFFFFFFFF & color))

                pointerSettings.setColor(Integer.toHexString(color));

                // If the auto-dismiss option is not enable (disabled as default) you have to manually dismiss the dialog
                // cp.dismiss();
            }
        });
    }

}
