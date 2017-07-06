package com.drowsyatmidnight.simpletwitter;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.adapter.TweetAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetDetail extends AppCompatActivity {

    @BindView(R.id.toolBarDetail)
    Toolbar toolBarDetail;
    @BindView(R.id.imgAvatarDetail)
    ImageView imgAvatarDetail;
    @BindView(R.id.txtUserNameDetail)
    TextView txtUserNameDetail;
    @BindView(R.id.txtRealNameDetail)
    TextView txtRealNameDetail;
    @BindView(R.id.txtTextDetail)
    TextView txtTextDetail;
    @BindView(R.id.imgTweetDetail)
    ImageView imgTweetDetail;
    @BindView(R.id.vdTweetDetail)
    VideoView vdTweetDetail;
    @BindView(R.id.txtTimeStampDetail)
    TextView txtTimeStampDetail;
    @BindView(R.id.txtReTweetDetail)
    TextView txtReTweetDetail;
    @BindView(R.id.txtFavouriteDetail)
    TextView txtFavouriteDetail;

    private int viewType;
    private boolean reply;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_detail_activity);
        ButterKnife.bind(this);
        getData();
        setView();
    }

    private void setView() {
        toolBarDetail.setTitle("Tweet");
        setSupportActionBar(toolBarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this)
                .load(data.get(0))
                .into(imgAvatarDetail);
        txtUserNameDetail.setText(data.get(1));
        txtRealNameDetail.setText(data.get(2));
        txtTextDetail.setText(data.get(3));
        if(viewType == 0){
            imgTweetDetail.setVisibility(View.GONE);
            vdTweetDetail.setVisibility(View.GONE);
        }else {
            if (viewType == 1){
                vdTweetDetail.setVisibility(View.GONE);
                Glide.with(this)
                        .load(data.get(4))
                        .into(imgTweetDetail);
            }else {
                if (data.get(5).isEmpty()){
                    imgTweetDetail.setVisibility(View.GONE);
                    vdTweetDetail.setVisibility(View.GONE);
                }else {
                    imgTweetDetail.setVisibility(View.GONE);
                    Uri video = Uri.parse(data.get(5));
                    vdTweetDetail.setVideoURI(video);
                    vdTweetDetail.start();
                }
            }
        }
        txtTimeStampDetail.setText(data.get(6));
        txtReTweetDetail.setText(TweetAdapter.withSuffix(Integer.parseInt(data.get(7)))+" Retweets");
        txtFavouriteDetail.setText(TweetAdapter.withSuffix(Integer.parseInt(data.get(8)))+" Likes");
    }

    private void getData() {
        data = new ArrayList<>();
        data = getIntent().getStringArrayListExtra("data");
        reply = getIntent().getBooleanExtra("reply", false);
        viewType = getIntent().getIntExtra("viewType", 0);
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
