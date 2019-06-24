package com.example.mark3;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class OverlayService extends Service {
    LinearLayout oView;
    String TAG = "Overlay Service";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        oView = new LinearLayout(this);
        oView.setBackgroundColor(Color.GRAY); // The translucent red color
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, 0 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(oView, params);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        Log.i(TAG, "created");
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        if (oView != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(oView);
        }
        super.onDestroy();
    }
}
