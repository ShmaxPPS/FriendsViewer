package com.example.maxim.friendsviewer.data;


public class FriendData {

    private String mFirstName;

    private String mLastName;

    private String mCity;

    private String mUniversity;

    private String mPhotoUrl;


    public FriendData() {
    }

    public FriendData(String firstName, String lastName,
                      String city, String university, String photoUrl) {
        mFirstName = firstName;
        mLastName = lastName;
        mCity = city;
        mUniversity = university;
        mPhotoUrl = photoUrl;
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
