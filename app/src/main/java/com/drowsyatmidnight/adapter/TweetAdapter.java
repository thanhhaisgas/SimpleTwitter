package com.drowsyatmidnight.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.drowsyatmidnight.simpletwitter.ProfileActivity;
import com.drowsyatmidnight.simpletwitter.R;
import com.drowsyatmidnight.simpletwitter.TweetDetail;

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

    public static String withSuffix(int count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp-1));
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
        holder.txtCpuntReply2.setText(withSuffix(tweet.getRetweet_count()));
        holder.txtCountFavourit2.setText(withSuffix(tweet.getFavorite_count()));
        holder.img_ic_reply2.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), null, linkVideo(tweet).get(0), tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,true,2));
        holder.txtText2.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), null, linkVideo(tweet).get(0), tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,false,2));
        holder.layoutTweetVideo.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), null, linkVideo(tweet).get(0), tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,false,2));
        holder.imageAvatar2.setOnClickListener(v -> goProfile(tweet.getUser().getId_str(), tweet.getUser().getName(), tweet.getUser().getScreen_name(),
                tweet.getUser().getLocation(), tweet.getUser().getDescription(), tweet.getUser().getFollowers_count(),
                tweet.getUser().getFriends_count(),tweet.getUser().getProfile_background_image_url(),tweet.getUser().getProfile_image_url(),
                context));
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
        holder.txtCpuntReply1.setText(withSuffix(tweet.getRetweet_count()));
        holder.txtCountFavourit1.setText(withSuffix(tweet.getFavorite_count()));
        holder.img_ic_reply1.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), tweet.getExtended_entities().getMedia().get(0).getMedia_url(), null, tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,true,1));
        holder.txtText1.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), tweet.getExtended_entities().getMedia().get(0).getMedia_url(), null, tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,false,1));
        holder.layoutTweetImage.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), tweet.getExtended_entities().getMedia().get(0).getMedia_url(), null, tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,false,1));
        holder.imageAvatar1.setOnClickListener(v -> goProfile(tweet.getUser().getId_str(), tweet.getUser().getName(), tweet.getUser().getScreen_name(),
                tweet.getUser().getLocation(), tweet.getUser().getDescription(), tweet.getUser().getFollowers_count(),
                tweet.getUser().getFriends_count(),tweet.getUser().getProfile_background_image_url(),tweet.getUser().getProfile_image_url(),
                context));
    }

    private void bindViewTweet(ItemTweet holder, TimeLine tweet) {
        Glide.with(context)
                .load(tweet.getUser().getProfile_image_url())
                .into(holder.imageAvatar0);
        holder.txtUserName0.setText(tweet.getUser().getName());
        holder.txtRealName0.setText("@"+tweet.getUser().getScreen_name());
        holder.txtTimeStamp0.setText(getRelativeTimeAgo(tweet.getCreated_at()));
        holder.txtText0.setText(tweet.getText());
        holder.txtCpuntReply0.setText(withSuffix(tweet.getRetweet_count()));
        holder.txtCountFavourit0.setText(withSuffix(tweet.getFavorite_count()));
        holder.img_ic_reply0.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                    tweet.getText(), null, null, tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                    ,true,0));
        holder.txtText0.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), null, null, tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,false,0));
        holder.layoutTweet.setOnClickListener(v -> goDetail(tweetData(tweet.getUser().getProfile_image_url(), tweet.getUser().getName(), "@"+tweet.getUser().getScreen_name(),
                tweet.getText(), null, null, tweet.getCreated_at(), String.valueOf(tweet.getRetweet_count()), String.valueOf(tweet.getFavorite_count()))
                ,false,0));
        holder.imageAvatar0.setOnClickListener(v -> goProfile(tweet.getUser().getId_str(), tweet.getUser().getName(), tweet.getUser().getScreen_name(),
                tweet.getUser().getLocation(), tweet.getUser().getDescription(), tweet.getUser().getFollowers_count(),
                tweet.getUser().getFriends_count(),tweet.getUser().getProfile_background_image_url(),tweet.getUser().getProfile_image_url(),
                context));
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

    private List<String> tweetData(String imgAvatarDetail, String txtUserNameDetail, String txtRealNameDetail,
                                   String txtTextDetail, String imgTweetDetail, String vdTweetDetail,
                                   String txtTimeStampDetail, String txtReTweetDetail, String txtFavouriteDetail){
        List<String> data = new ArrayList<>();
        data.add(imgAvatarDetail);
        data.add(txtUserNameDetail);
        data.add(txtRealNameDetail);
        data.add(txtTextDetail);
        if(imgTweetDetail == null){
            data.add(null);
        }else{
            data.add(imgTweetDetail);
        }
        if(vdTweetDetail == null){
            data.add(null);
        }else{
            data.add(vdTweetDetail);
        }
        data.add(txtTimeStampDetail);
        data.add(txtReTweetDetail);
        data.add(txtFavouriteDetail);
        return data;
    }


    private void goProfile(String id_str, String name, String screen_name,
                                   String location, String description, long followers_count,
                                   long friends_count, String profile_background_image_url, String profile_image_url, Context context){
        List<String> data = new ArrayList<>();
        data.add(id_str);
        data.add(name);
        data.add(screen_name);
        data.add(location);
        data.add(description);
        data.add(String.valueOf(followers_count));
        data.add(String.valueOf(friends_count));
        data.add(profile_background_image_url);
        data.add(profile_image_url);
        Intent goProfile = new Intent(context, ProfileActivity.class);
        goProfile.putStringArrayListExtra("data", (ArrayList<String>) data);
        context.startActivity(goProfile);
    }

    private void goDetail(List<String> data, boolean reply, int viewType){
        Intent goTweetDeatil = new Intent(context, TweetDetail.class);
        goTweetDeatil.putStringArrayListExtra("data", (ArrayList<String>) data);
        goTweetDeatil.putExtra("reply", reply);
        goTweetDeatil.putExtra("viewType", viewType);
        context.startActivity(goTweetDeatil);
    }
}
