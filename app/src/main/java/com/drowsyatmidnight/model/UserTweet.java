package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haint on 01/07/2017.
 */

public class UserTweet implements Parcelable {
    private long id;
    private String name;
    private String screen_name;
    private String profile_image_url;

    public UserTweet(long id, String name, String screen_name, String profile_image_url) {
        this.id = id;
        this.name = name;
        this.screen_name = screen_name;
        this.profile_image_url = profile_image_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    protected UserTweet(Parcel in) {
        id = in.readLong();
        name = in.readString();
        screen_name = in.readString();
        profile_image_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(screen_name);
        dest.writeString(profile_image_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserTweet> CREATOR = new Creator<UserTweet>() {
        @Override
        public UserTweet createFromParcel(Parcel in) {
            return new UserTweet(in);
        }

        @Override
        public UserTweet[] newArray(int size) {
            return new UserTweet[size];
        }
    };
}
