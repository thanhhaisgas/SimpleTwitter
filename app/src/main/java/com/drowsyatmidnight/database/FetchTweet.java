package com.drowsyatmidnight.database;

import com.drowsyatmidnight.model.TimeLine;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haint on 01/07/2017.
 */

public class FetchTweet {
    private List<TimeLine> timeLineList;
    private JSONArray jsonArray;

    public FetchTweet(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        timeLineList = new ArrayList<>();
    }

    public List<TimeLine> getTimelines(){
        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                TimeLine timeLine = gson.fromJson(String.valueOf(object), TimeLine.class);
                timeLineList.add(timeLine);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return timeLineList;
    }
}
