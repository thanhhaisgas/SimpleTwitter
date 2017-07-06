package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.drowsyatmidnight.simpletwitter.R;

/**
 * Created by haint on 11/17/2117.
 */

public class ItemTweetImage extends RecyclerView.ViewHolder {
    public ImageView imageAvatar1;
    public TextView txtUserName1;
    public TextView txtRealName1;
    public TextView txtTimeStamp1;
    public TextView txtText1;
    public ImageView imgTweet;
    public ImageView img_ic_reply1;
    public ImageView img_ic_retweet1;
    public ImageView img_ic_favourite1;
    public TextView txtCountComment1;
    public TextView txtCpuntReply1;
    public TextView txtCountFavourit1;
    public FrameLayout layoutTweetImage;
    
    public ItemTweetImage(View itemView) {
        super(itemView);
        imageAvatar1 = (ImageView) itemView.findViewById(R.id.imgAvatar1);
        txtUserName1 = (TextView) itemView.findViewById(R.id.txtUserName1);
        txtRealName1 = (TextView) itemView.findViewById(R.id.txtRealName1);
        txtTimeStamp1 = (TextView) itemView.findViewById(R.id.txtTimeStamp1);
        txtText1 = (TextView) itemView.findViewById(R.id.txtText1);
        imgTweet = (ImageView) itemView.findViewById(R.id.imgTweet);
        img_ic_reply1 = (ImageView) itemView.findViewById(R.id.img_ic_reply1);
        img_ic_retweet1 = (ImageView) itemView.findViewById(R.id.img_ic_retweet1);
        img_ic_favourite1 = (ImageView) itemView.findViewById(R.id.img_ic_favourite1);
        txtCountComment1 = (TextView) itemView.findViewById(R.id.txtCountComment1);
        txtCpuntReply1 = (TextView) itemView.findViewById(R.id.txtCpuntReply1);
        txtCountFavourit1 = (TextView) itemView.findViewById(R.id.txtCountFavourit1);
        layoutTweetImage = (FrameLayout) itemView.findViewById(R.id.layoutTweetImage);
    }
}
