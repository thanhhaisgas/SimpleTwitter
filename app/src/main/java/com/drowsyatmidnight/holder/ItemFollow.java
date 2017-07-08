package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drowsyatmidnight.simpletwitter.R;

/**
 * Created by haint on 09/07/2017.
 */

public class ItemFollow extends RecyclerView.ViewHolder {
    public ImageView imageAvatarFollow;
    public TextView txtUserNameFollow;
    public TextView txtRealNameFollow;
    public TextView txtDescriptionFollow;

    public ItemFollow(View itemView) {
        super(itemView);
        imageAvatarFollow = (ImageView) itemView.findViewById(R.id.imageAvatarFollow);
        txtUserNameFollow = (TextView) itemView.findViewById(R.id.txtUserNameFollow);
        txtRealNameFollow = (TextView) itemView.findViewById(R.id.txtRealNameFollow);
        txtDescriptionFollow = (TextView) itemView.findViewById(R.id.txtDescriptionFollow);
    }
}
