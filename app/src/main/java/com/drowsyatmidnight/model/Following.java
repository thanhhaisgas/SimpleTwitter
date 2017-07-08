package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by haint on 09/07/2017.
 */

public class Following implements Parcelable {
    List<Users> users;

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    protected Following(Parcel in) {
        users = in.createTypedArrayList(Users.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(users);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Following> CREATOR = new Creator<Following>() {
        @Override
        public Following createFromParcel(Parcel in) {
            return new Following(in);
        }

        @Override
        public Following[] newArray(int size) {
            return new Following[size];
        }
    };
}
