package com.example.maxim.friendsviewer.adapter;


import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxim.friendsviewer.R;
import com.example.maxim.friendsviewer.data.GroupData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private FragmentActivity mActivity;
    private List<GroupData> mGroups;

    public GroupAdapter(FragmentActivity activity, List<GroupData> groups) {
        mActivity = activity;
        mGroups = groups;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupAdapter.GroupViewHolder(LayoutInflater.from(mActivity).inflate(
                R.layout.group_view, parent, false));
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.bind(mGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        private ImageView mGroupPhoto;
        private TextView mGroupName;

        public GroupViewHolder(View itemView) {
            super(itemView);

            mGroupPhoto = (ImageView) itemView.findViewById(R.id.group_photo);
            mGroupName = (TextView) itemView.findViewById(R.id.group_name);
        }

        public void bind(final GroupData data) {
            mGroupName.setText(data.getName());

            Picasso.with(mActivity)
                    .load(data.getPhotoUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mGroupPhoto, new Callback() {

                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Picasso.with(mActivity)
                                    .load(data.getPhotoUrl())
                                    .into(mGroupPhoto);
                        }
                    });
        }
    }
}
