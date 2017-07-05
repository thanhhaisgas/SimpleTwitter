package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by haint on 05/07/2017.
 */

public class ExtendedEntities implements Parcelable {
    private List<Media> media;

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    protected ExtendedEntities(Parcel in) {
        media = in.createTypedArrayList(Media.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(media);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExtendedEntities> CREATOR = new Creator<ExtendedEntities>() {
        @Override
        public ExtendedEntities createFromParcel(Parcel in) {
            return new ExtendedEntities(in);
        }

        @Override
        public ExtendedEntities[] newArray(int size) {
            return new ExtendedEntities[size];
        }
    };
}
