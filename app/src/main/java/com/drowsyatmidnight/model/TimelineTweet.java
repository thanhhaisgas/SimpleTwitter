package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haint on 01/07/2017.
 */

public class TimelineTweet implements Parcelable {
    private long id;
    private String created_at;
    private String text;
    private String media_url;
    private UserTweet userTwee;
    private VideoTweet videoTweet;
    private long retweet_count;
    private long favorite_count;

    public TimelineTweet(long id, String created_at, String text, String media_url, UserTweet userTwee, VideoTweet videoTweet, long retweet_count, long favorite_count) {
        this.id = id;
        this.created_at = created_at;
        this.text = text;
        this.media_url = media_url;
        this.userTwee = userTwee;
        this.videoTweet = videoTweet;
        this.retweet_count = retweet_count;
        this.favorite_count = favorite_count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public UserTweet getUserTwee() {
        return userTwee;
    }

    public void setUserTwee(UserTweet userTwee) {
        this.userTwee = userTwee;
    }

    public VideoTweet getVideoTweet() {
        return videoTweet;
    }

    public void setVideoTweet(VideoTweet videoTweet) {
        this.videoTweet = videoTweet;
    }

    public long getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(long retweet_count) {
        this.retweet_count = retweet_count;
    }

    public long getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(long favorite_count) {
        this.favorite_count = favorite_count;
    }

    protected TimelineTweet(Parcel in) {
        id = in.readLong();
        created_at = in.readString();
        text = in.readString();
        media_url = in.readString();
        userTwee = in.readParcelable(UserTweet.class.getClassLoader());
        videoTweet = in.readParcelable(VideoTweet.class.getClassLoader());
        retweet_count = in.readLong();
        favorite_count = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(created_at);
        dest.writeString(text);
        dest.writeString(media_url);
        dest.writeParcelable(userTwee, flags);
        dest.writeParcelable(videoTweet, flags);
        dest.writeLong(retweet_count);
        dest.writeLong(favorite_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimelineTweet> CREATOR = new Creator<TimelineTweet>() {
        @Override
        public TimelineTweet createFromParcel(Parcel in) {
            return new TimelineTweet(in);
        }

        @Override
        public TimelineTweet[] newArray(int size) {
            return new TimelineTweet[size];
        }
    };
}
