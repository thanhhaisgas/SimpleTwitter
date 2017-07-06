package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.drowsyatmidnight.simpletwitter.R;

/**
 * Created by haint on 01/07/2017.
 */

public class ItemTweet extends RecyclerView.ViewHolder {
    public ImageView imageAvatar0;
    public TextView txtUserName0;
    public TextView txtRealName0;
    public TextView txtTimeStamp0;
    public TextView txtText0;
    public ImageView img_ic_reply0;
    public ImageView img_ic_retweet0;
    public ImageView img_ic_favourite0;
    public TextView txtCountComment0;
    public TextView txtCpuntReply0;
    public TextView txtCountFavourit0;
    public FrameLayout layoutTweet;


    public ItemTweet(View itemView) {
        super(itemView);
        imageAvatar0 = (ImageView) itemView.findViewById(R.id.imageAvatar0);
        txtUserName0 = (TextView) itemView.findViewById(R.id.txtUserName0);
        txtRealName0 = (TextView) itemView.findViewById(R.id.txtRealName0);
        txtTimeStamp0 = (TextView) itemView.findViewById(R.id.txtTimeStamp0);
        txtText0 = (TextView) itemView.findViewById(R.id.txtText0);
        img_ic_reply0 = (ImageView) itemView.findViewById(R.id.img_ic_reply0);
        img_ic_retweet0 = (ImageView) itemView.findViewById(R.id.img_ic_retweet0);
        img_ic_favourite0 = (ImageView) itemView.findViewById(R.id.img_ic_favourite0);
        txtCountComment0 = (TextView) itemView.findViewById(R.id.txtCountComment0);
        txtCpuntReply0 = (TextView) itemView.findViewById(R.id.txtCpuntReply0);
        txtCountFavourit0 = (TextView) itemView.findViewById(R.id.txtCountFavourit0);
        layoutTweet = (FrameLayout) itemView.findViewById(R.id.layoutTweet);
    }
}
