package com.ahmeturunveren.seismometer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class MainActivity_au extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager_au;
    private Sensor accelerometer_au;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager_au = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer_au = sensorManager_au.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer_au != null) {
            sensorManager_au.registerListener(this, accelerometer_au, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x_au = event.values[0];
        float y_au = event.values[1];
        float z_au = event.values[2];
        double siddet_au = Math.sqrt(x_au * x_au + y_au * y_au + z_au * z_au);
        if (siddet_au > 15) {
            // Deprem algılandı
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                showAlertDialog_au();
            } else {

                vibrator.vibrate(0x1f4);
            }

        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void showAlertDialog_au() {
        AlertDialog.Builder builder_au = new AlertDialog.Builder(this);
        builder_au.setTitle("Deprem Algılandı");
        builder_au.setMessage("Dikkatli olun!");
        builder_au.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder_au.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager_au.unregisterListener(this);
        finish(); // Uygulama kapatılır
    }

}