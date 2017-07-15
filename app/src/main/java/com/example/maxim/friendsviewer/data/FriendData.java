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
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getCity() {
        return mCity;
    }

    public String getUniversity() {
        return mUniversity;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

}
