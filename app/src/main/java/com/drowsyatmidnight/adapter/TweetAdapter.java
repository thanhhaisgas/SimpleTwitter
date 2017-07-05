package com.drowsyatmidnight.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.holder.ItemTweet;
import com.drowsyatmidnight.holder.ItemTweetImage;
import com.drowsyatmidnight.holder.ItemTweetVideo;
import com.drowsyatmidnight.model.TimeLine;
import com.drowsyatmidnight.model.Variants;
import com.drowsyatmidnight.simpletwitter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by haint on 01/07/2017.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TimeLine> data;
    private Context context;
    private Listener listener;

    private static final int tweet = 0;
    private static final int tweetImg = 1;

    public interface Listener{
        void onLoadMore();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public TweetAdapter(List<TimeLine> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        TimeLine sample = data.get(position);
        if(sample.getExtended_entities()==null){
            return 0;
        }if(sample.getExtended_entities().getMedia().get(0).getVideo_info()==null){
            return 1;
        }else return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == tweet){
            return new ItemTweet(LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false));
        }else {
            if(viewType == tweetImg){
                return new ItemTweetImage(LayoutInflater.from(context).inflate(R.layout.item_tweet_image, parent, false));
            }else {
                return new ItemTweetVideo(LayoutInflater.from(context).inflate(R.layout.item_tweet_video, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeLine tweet = data.get(position);
        if(holder instanceof ItemTweet){
            bindViewTweet((ItemTweet) holder, tweet);
        }else {
            if (holder instanceof ItemTweetImage){
                bindViewTweetImage((ItemTweetImage) holder, tweet);
            }else {
                if (holder instanceof ItemTweetVideo){
                    bindViewTweetVideo((ItemTweetVideo) holder, tweet);
                }
            }
        }

        if(position == data.size() - 1 && listener != null){
            listener.onLoadMore();
        }
    }

    private void bindViewTweetVideo(ItemTweetVideo holder, TimeLine tweet) {
        Glide.with(context)
                .load(tweet.getUser().getProfile_image_url())
                .into(holder.imageAvatar2);
        holder.txtUserName2.setText(tweet.getUser().getName());
        holder.txtRealName2.setText("@"+tweet.getUser().getScreen_name());
        holder.txtTimeStamp2.setText(getRelativeTimeAgo(tweet.getCreated_at()));
        holder.txtText2.setText(tweet.getText());
        if (linkVideo(tweet).size()!=0){
            Uri video = Uri.parse(linkVideo(tweet).get(0));
            holder.vdTweet.setVideoURI(video);
            holder.vdTweet.start();
        }else {
            holder.vdTweet.setVisibility(View.GONE);
        }
    }

    private List<String> linkVideo(TimeLine tweet) {
        List<String> url = new ArrayList<>();
        for (Variants n: tweet.getExtended_entities().getMedia().get(0).getVideo_info().getVariants()){
            if(n.getBitrate()>0){
                url.add(n.getUrl());
            }
        }
        return url;
    }

    private void bindViewTweetImage(ItemTweetImage holder, TimeLine tweet) {
        Glide.with(context)
                .load(tweet.getUser().getProfile_image_url())
                .into(holder.imageAvatar1);
        holder.txtUserName1.setText(tweet.getUser().getName());
        holder.txtRealName1.setText("@"+tweet.getUser().getScreen_name());
        holder.txtTimeStamp1.setText(getRelativeTimeAgo(tweet.getCreated_at()));
        holder.txtText1.setText(tweet.getText());
        Glide.with(context)
                .load(tweet.getExtended_entities().getMedia().get(0).getMedia_url())
                .into(holder.imgTweet);
    }

    private void bindViewTweet(ItemTweet holder, TimeLine tweet) {
        Glide.with(context)
                .load(tweet.getUser().getProfile_image_url())
                .into(holder.imageAvatar0);
        holder.txtUserName0.setText(tweet.getUser().getName());
        holder.txtRealName0.setText("@"+tweet.getUser().getScreen_name());
        holder.txtTimeStamp0.setText(getRelativeTimeAgo(tweet.getCreated_at()));
        holder.txtText0.setText(tweet.getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public void appendData(List<TimeLine> timelineTweets){
        int nextPos = data.size();
        this.data.addAll(nextPos, timelineTweets);
        notifyDataSetChanged();
    }
}
