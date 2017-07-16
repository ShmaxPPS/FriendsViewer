package com.example.maxim.friendsviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.maxim.friendsviewer.fragment.FriendsFragment;
import com.example.maxim.friendsviewer.fragment.LoginFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LaunchActivity extends AppCompatActivity {

    private boolean mIsResumed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                if (mIsResumed) {
                    switch (res) {
                        case LoggedIn:
                            showLogout();
                            break;

                        case LoggedOut:
                            showLogin();
                            break;

                        default:
                            break;
                    }
                }
            }

            @Override
            public void onError(VKError error) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode,
                resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {
            }

            @Override
            public void onError(VKError error) {
            }

        })) {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsResumed = true;
        if (VKSdk.isLoggedIn()) {
            showLogout();
        } else {
            showLogin();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResumed = false;
    }

    private void showLogin() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commitAllowingStateLoss();
    }

    private void showLogout() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new FriendsFragment())
                .commitAllowingStateLoss();
    }
}


