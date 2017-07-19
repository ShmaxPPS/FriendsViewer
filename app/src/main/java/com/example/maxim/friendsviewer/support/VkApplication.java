package com.example.maxim.friendsviewer.support;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.example.maxim.friendsviewer.activity.LaunchActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class VkApplication extends Application {


    private VKAccessTokenTracker mVkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Toast.makeText(getApplicationContext(),
                        "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(VkApplication.this, LaunchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mVkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }
}
