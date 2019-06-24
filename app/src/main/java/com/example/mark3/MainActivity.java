package com.example.mark3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    boolean TRACKING_SERVICE_RUNNING = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
    }

    protected void startTrack(View view) {
        if (!TRACKING_SERVICE_RUNNING) {
            startService(new Intent(this, TrackingService.class));
            Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show();
            TRACKING_SERVICE_RUNNING = true;
        } else
            Toast.makeText(this, "Already Active!", Toast.LENGTH_SHORT).show();
    }

    public void stopTrack(View view) {
        if (TRACKING_SERVICE_RUNNING) {
            stopService(new Intent(this, TrackingService.class));
            Toast.makeText(this, "Service Stopped!", Toast.LENGTH_SHORT).show();
            TRACKING_SERVICE_RUNNING = false;
            if (TrackingService.OVERLAY_RUNNING) {
                stopService(new Intent(this, OverlayService.class));
                TrackingService.OVERLAY_RUNNING = false;
            }
        } else
            Toast.makeText(this, "Service Stopped already!", Toast.LENGTH_SHORT).show();
    }
    public void buttonToggleON(){
        Button btn=(Button) findViewById(R.id.DeactivateButton);
        if(!btn.isEnabled()){
            btn.setEnabled(true);
        }
    }
    public void buttonToggleOFF(){
        Button btn=(Button) findViewById(R.id.DeactivateButton);
        if(btn.isEnabled()){
            btn.setEnabled(false);
        }
    }
}
