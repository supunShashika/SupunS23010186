package com.s23010186.lab05;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView temperatureText;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        temperatureText = findViewById(R.id.temperatureText);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        } else {
            temperatureText.setText("Ambient Temperature Sensor not available");
        }


        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float temperature = event.values[0];
        temperatureText.setText("Current Temperature: " + temperature + "°C");

        float TEMPERATURE_THRESHOLD = 86;
        if (temperature > TEMPERATURE_THRESHOLD && !isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
        } else if (temperature <= TEMPERATURE_THRESHOLD) {
            isPlaying = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
