package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by haint on 10/07/2017.
 */

public class Status implements Parcelable {
    private List<TimeLine> statuses;

    public List<TimeLine> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<TimeLine> statuses) {
        this.statuses = statuses;
    }

    protected Status(Parcel in) {
        statuses = in.createTypedArrayList(TimeLine.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(statuses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}
