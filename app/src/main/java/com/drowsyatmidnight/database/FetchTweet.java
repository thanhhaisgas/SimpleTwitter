package com.drowsyatmidnight.database;

import android.util.Log;

import com.drowsyatmidnight.model.TimelineTweet;
import com.drowsyatmidnight.model.UserTweet;
import com.drowsyatmidnight.model.VideoTweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haint on 01/07/2017.
 */

public class FetchTweet {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<String> urlImage;
    private List<VideoTweet> videoTweetList;
    private List<UserTweet> userTweetList;
    private List<TimelineTweet> timelineTweetList;

    public FetchTweet(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        urlImage = new ArrayList<>();
        videoTweetList = new ArrayList<>();
        userTweetList = new ArrayList<>();
        timelineTweetList = new ArrayList<>();
    }

    public List<TimelineTweet> getTimeline(){
        for(int i = 0; i<jsonArray.length();i++){
            try {
                   jsonObject = jsonArray.getJSONObject(i);
                   getUrlVideoImage();
                   getUser();
            } catch (JSONException e) {
                   e.printStackTrace();
            }
        }
        for (int i = 0; i<jsonArray.length(); i++){
            try {
                jsonObject = jsonArray.getJSONObject(i);
                timelineTweetList.add(new TimelineTweet(jsonObject.getLong("id"),
                        jsonObject.getString("created_at"),
                        jsonObject.getString("text"),
                        urlImage.get(i),
                        userTweetList.get(i),
                        videoTweetList.get(i),
                        jsonObject.getLong("retweet_count"),
                        jsonObject.getLong("favorite_count")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return timelineTweetList;
    }

    private List<VideoTweet> getUrlVideoImage(){
        try {
            if (jsonObject.isNull("extended_entities")){
                Log.d("NULL", "nulllll");
                videoTweetList.add(null);
                urlImage.add(null);
            }
            else {
                JSONObject extended_entities = jsonObject.getJSONObject("extended_entities");
                JSONArray media = extended_entities.getJSONArray("media");
                for(int i=0; i<media.length();i++){
                    JSONObject image = media.getJSONObject(i);
                    urlImage.add(image.getString("media_url"));
                    if(image.getString("type").compareTo("video")==0){
                        JSONObject video_info = image.getJSONObject("video_info");
                        JSONArray variants = video_info.getJSONArray("variants");
                        JSONObject video = variants.getJSONObject(0);
                        if(video.length()>2){
                            videoTweetList.add(new VideoTweet(video.getLong("bitrate"),video.getString("content_type"), video.getString("url")));
                        }
                    }else {
                        videoTweetList.add(null);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videoTweetList;
    }

    private List<UserTweet> getUser(){
        try {
            JSONObject user = jsonObject.getJSONObject("user");
            userTweetList.add(new UserTweet(user.getLong("id"), user.getString("name"), user.getString("screen_name"), user.getString("profile_image_url")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userTweetList;
    }


}
