package com.example.lockscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, DeviceAdmin.class);

        if (!mDPM.isAdminActive(componentName)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            startActivity(intent);
        }

        Button buttonLock = (Button) findViewById(R.id.buttonLock);

        buttonLock.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  mDPM.lockNow();
              }
          }
        );
    }
}