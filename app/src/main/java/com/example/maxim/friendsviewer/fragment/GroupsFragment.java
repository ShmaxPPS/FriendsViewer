package com.example.maxim.friendsviewer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maxim.friendsviewer.R;
import com.example.maxim.friendsviewer.adapter.GroupAdapter;
import com.example.maxim.friendsviewer.data.GroupData;
import com.example.maxim.friendsviewer.utils.Constants;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {

    public static final String TAG = GroupsFragment.class.getCanonicalName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private GroupAdapter mGroupAdapter;
    private SwipeRefreshLayout mSwipeContainer;

    private ArrayList<GroupData> mAllGroups;

    private int mUserId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_view_groups);

        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAllGroups = new ArrayList<>();

        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getInt(Constants.BUNDLE.KEY_USER_ID);
            mAllGroups = savedInstanceState
                    .getParcelableArrayList(Constants.BUNDLE.KEY_GROUPS_LIST);
        } else {
            mUserId = getArguments().getInt(Constants.BUNDLE.KEY_USER_ID);
            updateGroups();
        }

        mGroupAdapter = new GroupAdapter(getActivity(), mAllGroups);
        mRecyclerView.setAdapter(mGroupAdapter);

        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                updateGroups();
            }
        });


        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.BUNDLE.KEY_USER_ID, mUserId);
        outState.putParcelableArrayList(Constants.BUNDLE.KEY_GROUPS_LIST, mAllGroups);
    }

    private void updateGroups() {
        VKRequest request = VKApi.groups().get(VKParameters.from(
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

            @Override
            public void onError(VKError error) {
                super.onError(error);
                switch (error.errorCode) {
                    case VKError.VK_REQUEST_HTTP_FAILED :
                        Toast.makeText(getActivity(),
                                R.string.internet_connection_error, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(),
                                error.toString(), Toast.LENGTH_SHORT).show();
                }
                mSwipeContainer.setRefreshing(false);
            }
        });
    }
}
