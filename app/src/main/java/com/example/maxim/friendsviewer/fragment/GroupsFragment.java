package com.example.maxim.friendsviewer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxim.friendsviewer.R;
import com.example.maxim.friendsviewer.adapter.GroupAdapter;
import com.example.maxim.friendsviewer.data.GroupData;
import com.example.maxim.friendsviewer.utils.Constants;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {

    public static final String TAG = GroupsFragment.class.getCanonicalName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private GroupAdapter mGroupAdapter;
    private SwipeRefreshLayout mSwipeContainer;

    private List<GroupData> mAllGroups;

    private int mUserId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("Vk Application", "GroupsFragment onCreateView");


        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_view_groups);

        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAllGroups = new ArrayList<>();
        mGroupAdapter = new GroupAdapter(getActivity(), mAllGroups);
        mRecyclerView.setAdapter(mGroupAdapter);

        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getInt(Constants.BUNDLE.KEY_USER_ID);
        } else {
            mUserId = getArguments().getInt(Constants.BUNDLE.KEY_USER_ID);
        }

        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                updateGroups();
            }
        });

        updateGroups();

        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Vk Application", "FriendsFragment onSaveInstanceState");
        outState.putInt(Constants.BUNDLE.KEY_USER_ID, mUserId);
    }

    private void updateGroups() {
        final VKRequest request = VKApi.groups().get(VKParameters.from(
                VKApiConst.USER_ID, mUserId,
                VKApiConst.EXTENDED, 1,
                VKApiConst.FIELDS, "name, photo_100"));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList<VKApiCommunity> vkGroups = (VKList) response.parsedModel;

                mAllGroups.clear();

                for (VKApiCommunity vkGroup: vkGroups) {
                    mAllGroups.add(new GroupData(vkGroup.name, vkGroup.photo_100));
                }

                mRecyclerView.setAdapter(mGroupAdapter);
                mSwipeContainer.setRefreshing(false);
            }
        });
    }


}
