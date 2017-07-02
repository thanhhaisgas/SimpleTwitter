package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.drowsyatmidnight.simpletwitter.R;

/**
 * Created by haint on 01/07/2017.
 */

public class ItemTweetVideo extends RecyclerView.ViewHolder {
    public ImageView imageAvatar2;
    public TextView txtUserName2;
    public TextView txtRealName2;
    public TextView txtTimeStamp2;
    public TextView txtText2;
    public VideoView vdTweet;
    public ItemTweetVideo(View itemView) {
        super(itemView);
        imageAvatar2 = (ImageView) itemView.findViewById(R.id.imageAvatar2);
        txtUserName2 = (TextView) itemView.findViewById(R.id.txtUserName2);
        txtRealName2 = (TextView) itemView.findViewById(R.id.txtRealName2);
        txtTimeStamp2 = (TextView) itemView.findViewById(R.id.txtTimeStamp2);
        txtText2 = (TextView) itemView.findViewById(R.id.txtText2);
        vdTweet = (VideoView) itemView.findViewById(R.id.vdTweet);
    }
}
