package com.drowsyatmidnight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haint on 05/07/2017.
 */

public class TimeLine implements Parcelable{
    private String created_at;
    private long id;
    private String text;
    private ExtendedEntities extended_entities;
    private User user;
    private int retweet_count;
    private int favorite_count;
    private boolean favorited;
    private boolean retweeted;

    protected TimeLine(Parcel in) {
        created_at = in.readString();
        id = in.readLong();
        text = in.readString();
        extended_entities = in.readParcelable(ExtendedEntities.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
        retweet_count = in.readInt();
        favorite_count = in.readInt();
        favorited = in.readByte() != 0;
        retweeted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);
        dest.writeLong(id);
        dest.writeString(text);
        dest.writeParcelable(extended_entities, flags);
        dest.writeParcelable(user, flags);
        dest.writeInt(retweet_count);
        dest.writeInt(favorite_count);
        dest.writeByte((byte) (favorited ? 1 : 0));
        dest.writeByte((byte) (retweeted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeLine> CREATOR = new Creator<TimeLine>() {
        @Override
        public TimeLine createFromParcel(Parcel in) {
            return new TimeLine(in);
        }

        @Override
        public TimeLine[] newArray(int size) {
            return new TimeLine[size];
        }
    };

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ExtendedEntities getExtended_entities() {
        return extended_entities;
    }

    public void setExtended_entities(ExtendedEntities extended_entities) {
        this.extended_entities = extended_entities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }
}
