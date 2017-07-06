package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.drowsyatmidnight.simpletwitter.R;

/**
 * Created by haint on 21/27/2217.
 */

public class ItemTweetVideo extends RecyclerView.ViewHolder {
    public ImageView imageAvatar2;
    public TextView txtUserName2;
    public TextView txtRealName2;
    public TextView txtTimeStamp2;
    public TextView txtText2;
    public VideoView vdTweet;
    public ImageView img_ic_reply2;
    public ImageView img_ic_retweet2;
    public ImageView img_ic_favourite2;
    public TextView txtCountComment2;
    public TextView txtCpuntReply2;
    public TextView txtCountFavourit2;
    public FrameLayout layoutTweetVideo;
    
    public ItemTweetVideo(View itemView) {
        super(itemView);
        imageAvatar2 = (ImageView) itemView.findViewById(R.id.imageAvatar2);
        txtUserName2 = (TextView) itemView.findViewById(R.id.txtUserName2);
        txtRealName2 = (TextView) itemView.findViewById(R.id.txtRealName2);
        txtTimeStamp2 = (TextView) itemView.findViewById(R.id.txtTimeStamp2);
        txtText2 = (TextView) itemView.findViewById(R.id.txtText2);
        vdTweet = (VideoView) itemView.findViewById(R.id.vdTweet);
        img_ic_reply2 = (ImageView) itemView.findViewById(R.id.img_ic_reply2);
        img_ic_retweet2 = (ImageView) itemView.findViewById(R.id.img_ic_retweet2);
        img_ic_favourite2 = (ImageView) itemView.findViewById(R.id.img_ic_favourite2);
        txtCountComment2 = (TextView) itemView.findViewById(R.id.txtCountComment2);
        txtCpuntReply2 = (TextView) itemView.findViewById(R.id.txtCpuntReply2);
        txtCountFavourit2 = (TextView) itemView.findViewById(R.id.txtCountFavourit2);
        layoutTweetVideo = (FrameLayout) itemView.findViewById(R.id.layoutTweetVideo);
    }
}
