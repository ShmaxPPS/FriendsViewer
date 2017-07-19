package com.example.maxim.friendsviewer.utils;


public final class Constants {

    private Constants() {
        // this prevents even the native class from
        // calling this constructor as well.
        throw new AssertionError();
    }

    public static final class BUNDLE {
        public static final String KEY_USER_ID       = "KEY_USER_ID";
        public static final String KEY_FRIENDS_LIST  = "KEY_FRIENDS_LIST";
        public static final String KEY_GROUPS_LIST   = "KEY_GROUPS_LIST";
        public static final String KEY_SEARCH_STRING = "KEY_SEARCH_STRING";

        private BUNDLE() {
            // this prevents even the native class from
            // calling this constructor as well.
            throw new AssertionError();
        }
    }


}
