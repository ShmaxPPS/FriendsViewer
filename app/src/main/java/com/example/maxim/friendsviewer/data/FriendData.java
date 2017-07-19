package com.example.maxim.friendsviewer.data;


import android.os.Parcel;
import android.os.Parcelable;

public class FriendData implements Parcelable {

    private final int mId;

    private final String mFirstName;

    private final String mLastName;

    private final String mCity;

    private final String mUniversity;

    private final String mPhotoUrl;

    public static final Parcelable.Creator<FriendData> CREATOR =
            new Parcelable.Creator<FriendData>() {
                public FriendData createFromParcel(Parcel in) {
                    return new FriendData(in);
                }

                public FriendData[] newArray(int size) {
                    return new FriendData[size];
                }
            };

    private FriendData(Parcel in) {
        mId = in.readInt();
        mFirstName = in.readString();
        mLastName = in.readString();
        mCity = in.readString();
        mUniversity = in.readString();
        mPhotoUrl = in.readString();
    }

    public FriendData(int id, String firstName, String lastName,
                      String city, String university, String photoUrl) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mCity = city;
        mUniversity = university;
        mPhotoUrl = photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeString(mCity);
        dest.writeString(mUniversity);
        dest.writeString(mPhotoUrl);
    }

    public int getId() {
        return mId;
    }

    public String getFirstName() {
        return mFirstName == null ? "" : mFirstName;
    }

    public String getLastName() {
        return mLastName == null ? "" : mLastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getCity() {
        return mCity == null ? "" : mCity;
    }

    public String getUniversity() {
        return mUniversity == null ? "" : mUniversity;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }


}
