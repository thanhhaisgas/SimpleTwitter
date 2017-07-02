package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    public ItemTweet(View itemView) {
        super(itemView);
        imageAvatar0 = (ImageView) itemView.findViewById(R.id.imageAvatar0);
        txtUserName0 = (TextView) itemView.findViewById(R.id.txtUserName0);
        txtRealName0 = (TextView) itemView.findViewById(R.id.txtRealName0);
        txtTimeStamp0 = (TextView) itemView.findViewById(R.id.txtTimeStamp0);
        txtText0 = (TextView) itemView.findViewById(R.id.txtText0);
    }
}
