package com.drowsyatmidnight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.holder.ItemFollow;
import com.drowsyatmidnight.model.Users;
import com.drowsyatmidnight.simpletwitter.R;

import java.util.List;

/**
 * Created by haint on 09/07/2017.
 */

public class FollowingAdapter extends RecyclerView.Adapter<ItemFollow> {
    private List<Users> data;
    private Context context;
    private View rootView;

    public FollowingAdapter(List<Users> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ItemFollow onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_follow, parent ,false);
        return new ItemFollow(rootView);
    }

    @Override
    public void onBindViewHolder(ItemFollow holder, int position) {
        Users users = data.get(position);
        Glide.with(context)
                .load(users.getProfile_image_url())
                .into(holder.imageAvatarFollow);
        holder.txtUserNameFollow.setText(users.getName());
        holder.txtRealNameFollow.setText(users.getScreen_name());
        holder.txtDescriptionFollow.setText(users.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
