package com.example.maxim.friendsviewer.data;


import android.os.Parcel;
import android.os.Parcelable;


public class GroupData implements Parcelable {

    private String mName;

    private String mPhotoUrl;

    public static final Parcelable.Creator<GroupData> CREATOR =
            new Parcelable.Creator<GroupData>() {
                public GroupData createFromParcel(Parcel in) {
                    return new GroupData(in);
                }

                public GroupData[] newArray(int size) {
                    return new GroupData[size];
                }
            };

    private GroupData(Parcel in) {
        mName = in.readString();
        mPhotoUrl = in.readString();
    }

    public GroupData(String mName, String mPhotoUrl) {
        this.mName = mName;
        this.mPhotoUrl = mPhotoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mPhotoUrl);
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }
}
