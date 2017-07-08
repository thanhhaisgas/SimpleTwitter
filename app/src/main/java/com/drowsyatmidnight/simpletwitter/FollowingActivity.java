package com.drowsyatmidnight.simpletwitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.drowsyatmidnight.adapter.FollowingAdapter;
import com.drowsyatmidnight.client.RestApplication;
import com.drowsyatmidnight.client.RestClient;
import com.drowsyatmidnight.model.Following;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FollowingActivity extends AppCompatActivity {

    @BindView(R.id.toolBarFollowing)
    Toolbar toolBarFollowing;
    @BindView(R.id.lvFollowing)
    RecyclerView lvFollowing;
    private FollowingAdapter followingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.following_list);
        ButterKnife.bind(this);
        setUpView();
        getData(ProfileActivity.dataType);
    }

    private void setUpView() {
        setSupportActionBar(toolBarFollowing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void getData(int dataType) {
        if(dataType == 1){
            RestClient client = RestApplication.getRestClient();
            client.getFollower(0,ProfileActivity.screen_name,ProfileActivity.id_str, new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    Gson gson = new Gson();
                    Following following = gson.fromJson(String.valueOf(jsonObject), Following.class);
                    followingAdapter = new FollowingAdapter(following.getUsers(), FollowingActivity.this);
                    lvFollowing.setAdapter(followingAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(FollowingActivity.this, LinearLayoutManager.VERTICAL, false);
                    lvFollowing.setLayoutManager(layoutManager);
                }
            });
        }else{
            RestClient client = RestApplication.getRestClient();
            client.getFollowing(0, ProfileActivity.screen_name, ProfileActivity.id_str, new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    Gson gson = new Gson();
                    Following following = gson.fromJson(String.valueOf(jsonObject), Following.class);
                    followingAdapter = new FollowingAdapter(following.getUsers(), FollowingActivity.this);
                    lvFollowing.setAdapter(followingAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(FollowingActivity.this, LinearLayoutManager.VERTICAL, false);
                    lvFollowing.setLayoutManager(layoutManager);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
