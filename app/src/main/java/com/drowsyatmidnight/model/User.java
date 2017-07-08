package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haint on 05/07/2017.
 */

public class User implements Parcelable {
    private String id_str;
    private String name;
    private String screen_name;
    private String location;
    private String description;
    private long followers_count;
    private long friends_count;
    private String profile_background_image_url;
    private String profile_image_url;
    private boolean following;

    protected User(Parcel in) {
        id_str = in.readString();
        name = in.readString();
        screen_name = in.readString();
        location = in.readString();
        description = in.readString();
        followers_count = in.readLong();
        friends_count = in.readLong();
        profile_background_image_url = in.readString();
        profile_image_url = in.readString();
        following = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_str);
        dest.writeString(name);
        dest.writeString(screen_name);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeLong(followers_count);
        dest.writeLong(friends_count);
        dest.writeString(profile_background_image_url);
        dest.writeString(profile_image_url);
        dest.writeByte((byte) (following ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(long followers_count) {
        this.followers_count = followers_count;
    }

    public long getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(long friends_count) {
        this.friends_count = friends_count;
    }

    public String getProfile_background_image_url() {
        return profile_background_image_url;
    }

    public void setProfile_background_image_url(String profile_background_image_url) {
        this.profile_background_image_url = profile_background_image_url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
