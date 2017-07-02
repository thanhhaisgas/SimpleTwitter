package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drowsyatmidnight.simpletwitter.R;

/**
 * Created by haint on 01/07/2017.
 */

public class ItemTweetImage extends RecyclerView.ViewHolder {
    public ImageView imageAvatar1;
    public TextView txtUserName1;
    public TextView txtRealName1;
    public TextView txtTimeStamp1;
    public TextView txtText1;
    public ImageView imgTweet;
    public ItemTweetImage(View itemView) {
        super(itemView);
        imageAvatar1 = (ImageView) itemView.findViewById(R.id.imgAvatar1);
        txtUserName1 = (TextView) itemView.findViewById(R.id.txtUserName1);
        txtRealName1 = (TextView) itemView.findViewById(R.id.txtRealName1);
        txtTimeStamp1 = (TextView) itemView.findViewById(R.id.txtTimeStamp1);
        txtText1 = (TextView) itemView.findViewById(R.id.txtText1);
        imgTweet = (ImageView) itemView.findViewById(R.id.imgTweet);
    }
}
