package com.example.maxim.friendsviewer.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public FriendViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(mActivity).inflate(
                R.layout.friend_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final FriendViewHolder holder, int position) {
        holder.bind(mFriends.get(position));
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {

        private ImageView mFriendPhoto;
        private TextView mFriendName;
        private TextView mFriendCity;
        private TextView mFriendUniversity;

        public FriendViewHolder(View itemView) {
            super(itemView);


            mFriendPhoto = (ImageView) itemView.findViewById(R.id.friend_photo);
            mFriendName = (TextView) itemView.findViewById(R.id.friend_name);
            mFriendCity = (TextView) itemView.findViewById(R.id.friend_city);
            mFriendUniversity = (TextView) itemView.findViewById(R.id.friend_university);

        }

        public void bind(final FriendData data) {
            mFriendName.setText(data.getFullName());
            mFriendCity.setText(data.getCity());
            mFriendUniversity.setText(data.getUniversity());

            Picasso.with(mActivity)
                    .load(data.getPhotoUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mFriendPhoto, new Callback() {

                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Picasso.with(mActivity)
                                    .load(data.getPhotoUrl())
                                    .into(mFriendPhoto);
                        }
                    });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(mActivity, "item clicked " + mFriendName.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
