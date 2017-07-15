package com.example.maxim.friendsviewer.adapter;


import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxim.friendsviewer.R;
import com.example.maxim.friendsviewer.data.FriendData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private Activity mActivity;
    private List<FriendData> mFriends;


    public FriendAdapter(Activity activity, List<FriendData> friends) {
        mActivity = activity;
        mFriends = friends;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(mActivity).inflate(
                R.layout.friend_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final FriendViewHolder holder, int position) {
        final FriendData friend = mFriends.get(position);
        holder.friendName.setText(friend.getFirstName() + " " + friend.getLastName());
        holder.friendCity.setText(friend.getCity());
        holder.friendUniversity.setText(friend.getUniversity());


        Picasso.with(mActivity)
                .load(friend.getPhotoUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.friendPhoto, new Callback() {

            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(mActivity)
                        .load(friend.getPhotoUrl())
                        .into(holder.friendPhoto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {

        ImageView friendPhoto;
        TextView friendName;
        TextView friendCity;
        TextView friendUniversity;

        public FriendViewHolder(View itemView) {
            super(itemView);


            friendPhoto = (ImageView) itemView.findViewById(R.id.friend_photo);
            friendName = (TextView) itemView.findViewById(R.id.friend_name);
            friendCity = (TextView) itemView.findViewById(R.id.friend_city);
            friendUniversity = (TextView) itemView.findViewById(R.id.friend_university);

        }
    }
}
