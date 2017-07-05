package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haint on 05/07/2017.
 */

public class Media implements Parcelable {
    private String media_url;
    private String url;
    private String type;
    private VideoInfo video_info;

    protected Media(Parcel in) {
        media_url = in.readString();
        url = in.readString();
        type = in.readString();
        video_info = in.readParcelable(VideoInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(media_url);
        dest.writeString(url);
        dest.writeString(type);
        dest.writeParcelable(video_info, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VideoInfo getVideo_info() {
        return video_info;
    }

    public void setVideo_info(VideoInfo video_info) {
        this.video_info = video_info;
    }
}
