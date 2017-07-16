package com.example.maxim.friendsviewer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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

public class FriendsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FriendAdapter mFriendAdapter;
    private SwipeRefreshLayout mSwipeContainer;

    private List<FriendData> mAllFriends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_view_friends);

        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAllFriends = new ArrayList<>();
        mFriendAdapter = new FriendAdapter(getActivity(), mAllFriends);
        mRecyclerView.setAdapter(mFriendAdapter);

        updateFriends();

        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                updateFriends();
            }

        });

        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        setHasOptionsMenu(true);

        return view;
    }

    private void updateFriends() {
        final VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS,
                "first_name,last_name,city,universities,photo_100"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiUser> vkFriends = (VKList) response.parsedModel;

                mAllFriends.clear();

                for (VKApiUser vkFriend : vkFriends) {
                    mAllFriends.add(parseVkFriend(vkFriend));
                }

                mRecyclerView.setAdapter(mFriendAdapter);
                mSwipeContainer.setRefreshing(false);
            }
        });
    }

    private FriendData parseVkFriend(VKApiUser friend) {
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
        return new FriendData(friend.first_name,
                friend.last_name, city.title, university.name, friend.photo_100);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(this);

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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            mFriendAdapter = new FriendAdapter(getActivity(), mAllFriends);
            mRecyclerView.setAdapter(mFriendAdapter);
            return false;
        }

        List<FriendData> filteredFriends = new ArrayList<>();
        for (FriendData friend : mAllFriends) {
            if (friend.getFirstName().toLowerCase().indexOf(newText) == 0
                    || friend.getLastName().toLowerCase().indexOf(newText) == 0
                    || friend.getFullName().toLowerCase().indexOf(newText) == 0
                    || friend.getCity().toLowerCase().indexOf(newText) == 0
                    || friend.getUniversity().toLowerCase().indexOf(newText) == 0) {
                filteredFriends.add(friend);
            }
        }

        mFriendAdapter = new FriendAdapter(getActivity(), filteredFriends);
        mRecyclerView.setAdapter(mFriendAdapter);
        return true;
    }
}
