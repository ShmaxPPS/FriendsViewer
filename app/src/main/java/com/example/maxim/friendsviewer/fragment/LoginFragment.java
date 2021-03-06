package com.example.maxim.friendsviewer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maxim.friendsviewer.R;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getCanonicalName();

    private String[] mScope = {VKScope.FRIENDS, VKScope.GROUPS, VKScope.NOHTTPS};

    private Button mLoginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginButton = (Button) view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(getActivity(), mScope);
            }
        });

        return view;
    }
}
