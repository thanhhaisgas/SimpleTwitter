package com.drowsyatmidnight.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.holder.ItemTweet;
import com.drowsyatmidnight.holder.ItemTweetImage;
import com.drowsyatmidnight.holder.ItemTweetVideo;
import com.drowsyatmidnight.model.TimelineTweet;
import com.drowsyatmidnight.simpletwitter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by haint on 01/07/2017.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TimelineTweet> data;
    private Context context;
    private Listener listener;

    private static final int tweet = 0;
    private static final int tweetImg = 1;
    private static final int tweetVideo =2;

    public interface Listener{
        void onLoadMore();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public TweetAdapter(List<TimelineTweet> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        TimelineTweet sample = data.get(position);
        if(sample.getMedia_url()==null&&sample.getVideoTweet()==null){
            return 0;
        }else {
            if(sample.getVideoTweet()==null){
                return 1;
            }else {
                return 2;
            }
        }
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
        TimelineTweet tweet = data.get(position);
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

    private void bindViewTweetVideo(ItemTweetVideo holder, TimelineTweet tweet) {
        Glide.with(context)
                .load(tweet.getUserTwee().getProfile_image_url())
                .into(holder.imageAvatar2);
        holder.txtUserName2.setText(tweet.getUserTwee().getName());
        holder.txtRealName2.setText("@"+tweet.getUserTwee().getScreen_name());
        holder.txtTimeStamp2.setText(getRelativeTimeAgo(tweet.getCreated_at()));
        holder.txtText2.setText(tweet.getText());
        Uri video = Uri.parse(tweet.getVideoTweet().getUrl());
        holder.vdTweet.setVideoURI(video);
        holder.vdTweet.start();
    }

    private void bindViewTweetImage(ItemTweetImage holder, TimelineTweet tweet) {
        Glide.with(context)
                .load(tweet.getUserTwee().getProfile_image_url())
                .into(holder.imageAvatar1);
        holder.txtUserName1.setText(tweet.getUserTwee().getName());
        holder.txtRealName1.setText("@"+tweet.getUserTwee().getScreen_name());
        holder.txtTimeStamp1.setText(getRelativeTimeAgo(tweet.getCreated_at()));
        holder.txtText1.setText(tweet.getText());
        Glide.with(context)
                .load(tweet.getMedia_url())
                .into(holder.imgTweet);
    }

    private void bindViewTweet(ItemTweet holder, TimelineTweet tweet) {
        Glide.with(context)
                .load(tweet.getUserTwee().getProfile_image_url())
                .into(holder.imageAvatar0);
        holder.txtUserName0.setText(tweet.getUserTwee().getName());
        holder.txtRealName0.setText("@"+tweet.getUserTwee().getScreen_name());
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

    public void appendData(List<TimelineTweet> timelineTweets){
        int nextPos = data.size();
        this.data.addAll(nextPos, timelineTweets);
        notifyDataSetChanged();
    }
}
