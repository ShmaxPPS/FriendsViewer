package com.example.maxim.friendsviewer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxim.friendsviewer.LaunchActivity;
import com.example.maxim.friendsviewer.R;
import com.example.maxim.friendsviewer.adapter.FriendAdapter;
import com.example.maxim.friendsviewer.data.FriendData;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCity;
import com.vk.sdk.api.model.VKApiUniversity;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FriendAdapter mFriendAdapter;

    private List<FriendData> mFriends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_view_friends);

        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFriends = new ArrayList<>();

        mFriendAdapter = new FriendAdapter(getActivity(), mFriends);
        mRecyclerView.setAdapter(mFriendAdapter);

        final VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS,
                "first_name,last_name,city,universities,photo_100"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiUser> friends = (VKList) response.parsedModel;

                for (VKApiUser friend : friends) {
                    showFriend(friend);
                }

                mFriendAdapter.notifyDataSetChanged();

            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    private void showFriend(VKApiUser friend) {
        VKApiCity city = new VKApiCity();
        try {
            city.parse(friend.fields.getJSONObject("city"));
        } catch (JSONException e) {
            // Log.e("VK Application", "Could not parse city");
        }

        VKApiUniversity university = new VKApiUniversity();
        try {
            university.parse(friend.fields.getJSONArray("universities").getJSONObject(0));
        } catch (JSONException e) {
            // Log.e("VK Application", "Could not parse first city");
        }

        mFriends.add(new FriendData(friend.first_name,
                friend.last_name, city.title, university.name, friend.photo_100));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                VKSdk.logout();
                if (!VKSdk.isLoggedIn()) {
                    showLogin();
                }
                return true;
            case R.id.action_search:

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showLogin() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commitAllowingStateLoss();
    }

}
