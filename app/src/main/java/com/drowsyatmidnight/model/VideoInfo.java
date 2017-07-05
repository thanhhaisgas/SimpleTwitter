package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by haint on 05/07/2017.
 */

public class VideoInfo implements Parcelable {
    private List<Variants> variants;

    public List<Variants> getVariants() {
        return variants;
    }

    public void setVariants(List<Variants> variants) {
        this.variants = variants;
    }

    protected VideoInfo(Parcel in) {
        variants = in.createTypedArrayList(Variants.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(variants);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        @Override
        public VideoInfo createFromParcel(Parcel in) {
            return new VideoInfo(in);
        }

        @Override
        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };
}
