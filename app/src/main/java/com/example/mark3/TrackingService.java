package com.example.mark3;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

public class TrackingService extends Service {
    protected static LocationManager locationManager;
    static LocationListener locationListener;
    final long MIN_TIME = 2 * 1000;//2000 milliseconds
    final long MIN_DIST = 10;
    final float TO_KMPH = 3.6f;
    final float MAX_SPEED = 30f;
    static boolean OVERLAY_RUNNING=false;

    String TAG = "Tracking service";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getLocationUpdates();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        removeUpdates();
        super.onDestroy();
    }

    protected void removeUpdates() {
        if (locationManager != null) {
            {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.removeUpdates(locationListener);
                }
            }
        }
        Log.i(TAG, "removeUpdates");
    }

    protected void getLocationUpdates() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, "onLocationChanged: " + Double.toString(location.getSpeed()));
                if (location.hasSpeed() && location.getSpeed() * TO_KMPH > MAX_SPEED) {
                    startOverlay();
                }
                if (!location.hasSpeed() || location.getSpeed() * TO_KMPH < MAX_SPEED) {
                    stopOverlay();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
    }

    public String getForegroundApp() {
//        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//        // The first in the list of RunningTasks is always the foreground task.
//        ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
//        String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
//        PackageManager pm = this.getPackageManager();
//        PackageInfo foregroundAppPackageInfo = null;
//        try {
//            foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString();
//        return foregroundTaskAppName;
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        String currentapp = "NULL";
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                Log.i("Foreground App", appProcess.processName);
                currentapp = appProcess.processName;
            }
        }
        return currentapp;
    }

    public void startOverlay() {
        if(!OVERLAY_RUNNING){
        startService(new Intent(this, OverlayService.class));
        OVERLAY_RUNNING=true;
        }
    }

    public void stopOverlay() {
        if(OVERLAY_RUNNING){
            stopService(new Intent(this,OverlayService.class));
            OVERLAY_RUNNING=false;
        }
    }
}
