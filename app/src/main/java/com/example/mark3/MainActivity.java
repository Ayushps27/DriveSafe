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
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    boolean TRACKING_SERVICE_RUNNING = false;
    private static String ParentalPin;
    boolean PARENTAL_SWITCH_ON = false;
    //static Switch pswitch;// = (Switch) findViewById(R.id.ParentalSwitch);
//    public MainActivity(){
//        pswitch=(Switch) findViewById(R.id.ParentalSwitch);
//    }

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
        final Switch pswitch=(Switch) findViewById(R.id.ParentalSwitch);
        pswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PARENTAL_SWITCH_ON = isChecked;
                Log.i("info", "onCheckedChanged: " + PARENTAL_SWITCH_ON);
                if (isChecked) {
                    showPinSetDialog();
                    /*if(ParentalPin.isEmpty()){
                        pswitch.setChecked(false);
                        PARENTAL_SWITCH_ON=false;
                    }*/
                }
                if (!isChecked) {
                    showPinCheckDialog();
                    /*if(!ParentalPin.isEmpty()){
                        pswitch.setChecked(true);
                        PARENTAL_SWITCH_ON=true;
                    }*/
                }
            }
        });
    }

    protected void startTrack(View view) {
        if (!TRACKING_SERVICE_RUNNING) {
            startService(new Intent(this, TrackingService.class));
            Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show();
            TRACKING_SERVICE_RUNNING = true;
        } else
            Toast.makeText(this, "Already Active!", Toast.LENGTH_SHORT).show();
    }

    protected void stopTrack(View view) {
        if (PARENTAL_SWITCH_ON) {
            Toast.makeText(this, "Parental Mode ON! Turn it OFF first", Toast.LENGTH_SHORT).show();
        } else {
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
    }


    /*protected boolean checkSwitch(){
        final Switch pswitch= findViewById(R.id.ParentalSwitch);
        final boolean[] flag = {false};

        return flag[0];
    }*/
    private void showPinSetDialog() {
        PinSetDialog alertDialog = new PinSetDialog(this);
        alertDialog.show();
    }

    private void showPinCheckDialog() {
        PinCheckDialog alertDialog = new PinCheckDialog(this);
        alertDialog.show();
    }

    public static void setParentalPin(String p) {
        ParentalPin = p;
    }

    public static String getParentalPin() {
        return ParentalPin;
    }
    /*public static void turnOffswitch(){
        pswitch.setChecked(false);
        PARENTAL_SWITCH_ON=false;
    }
    public static  void turnOnswitch(){
        pswitch.setChecked(true);
        PARENTAL_SWITCH_ON=true;
    }*/

}