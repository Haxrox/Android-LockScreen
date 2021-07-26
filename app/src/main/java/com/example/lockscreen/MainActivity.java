package com.example.lockscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, DeviceAdmin.class);

        Log.i(TAG, String.valueOf(getReferrer()));

        AccessibilityService accessibilityService = new AccessibilityService() {
            @Override
            public void onAccessibilityEvent(AccessibilityEvent event) {
                
            }

            @Override
            public void onInterrupt() {

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
        } else {
            if (!mDPM.isAdminActive(componentName)) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                startActivity(intent);
            }
        }

        Button buttonLock = (Button) findViewById(R.id.buttonLock);

        buttonLock.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                      accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
                  } else {
                      mDPM.lockNow();
                  }
              }
          }
        );
    }
}