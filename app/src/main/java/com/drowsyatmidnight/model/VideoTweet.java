package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haint on 01/07/2017.
 */

public class VideoTweet implements Parcelable {
    private long bitrate;
    private String content_type;
    private String url;

    public long getBitrate() {
        return bitrate;
    }

    public String getContent_type() {
        return content_type;
    }

    public String getUrl() {
        return url;
    }

    public VideoTweet(long bitrate, String content_type, String url) {
        this.bitrate = bitrate;
        this.content_type = content_type;
        this.url = url;
    }

    protected VideoTweet(Parcel in) {
        bitrate = in.readLong();
        content_type = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(bitrate);
        dest.writeString(content_type);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoTweet> CREATOR = new Creator<VideoTweet>() {
        @Override
        public VideoTweet createFromParcel(Parcel in) {
            return new VideoTweet(in);
        }

        @Override
        public VideoTweet[] newArray(int size) {
            return new VideoTweet[size];
        }
    };
}
