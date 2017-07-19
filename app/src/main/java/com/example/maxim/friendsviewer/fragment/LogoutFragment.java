package com.example.maxim.friendsviewer.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maxim.friendsviewer.R;
import com.example.maxim.friendsviewer.activity.LaunchActivity;
import com.vk.sdk.VKSdk;

public class LogoutFragment extends Fragment {

    public static final String TAG = LogoutFragment.class.getCanonicalName();

    private Button mContinueButton;
    private Button mLogoutButton;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        mContinueButton = (Button) view.findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LaunchActivity.class));
            }
        });

        mLogoutButton = (Button) view.findViewById(R.id.logout_button);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.logout();
                if (!VKSdk.isLoggedIn()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new LoginFragment())
                            .commitAllowingStateLoss();
                }
            }
        });
        return view;
    }
}