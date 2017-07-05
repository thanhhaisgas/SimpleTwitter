package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haint on 05/07/2017.
 */

public class Variants implements Parcelable {
    private int bitrate;
    private String content_type;
    private String url;

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected Variants(Parcel in) {
        bitrate = in.readInt();
        content_type = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bitrate);
        dest.writeString(content_type);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Variants> CREATOR = new Creator<Variants>() {
        @Override
        public Variants createFromParcel(Parcel in) {
            return new Variants(in);
        }

        @Override
        public Variants[] newArray(int size) {
            return new Variants[size];
        }
    };
}
