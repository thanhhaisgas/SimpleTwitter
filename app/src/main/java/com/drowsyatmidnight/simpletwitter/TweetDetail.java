package com.drowsyatmidnight.simpletwitter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.adapter.TweetAdapter;
import com.drowsyatmidnight.client.RestApplication;
import com.drowsyatmidnight.client.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

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
    @BindView(R.id.edReply)
    EditText edReply;
    @BindView(R.id.txtScreenNameReply)
    TextView txtScreenNameReply;
    @BindView(R.id.edDialogReply)
    EditText edDialogReply;
    @BindView(R.id.txtAmountReply)
    TextView txtAmountReply;
    @BindView(R.id.btnReply)
    Button btnReply;
    @BindView(R.id.flReply)
    FrameLayout flReply;
    @BindView(R.id.flDoReply)
    FrameLayout flDoReply;

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
        addControls();
    }

    private void addControls() {
        edReply.setOnClickListener(v -> showReply());
        btnReply.setOnClickListener(v -> {
            doPostReply();
        });
        if(reply == true){
            edReply.performClick();
        }
    }

    private void doPostReply() {
        RestClient client = RestApplication.getRestClient();
        client.postReply(txtScreenNameReply.getText()+" "+edDialogReply.getText().toString(), data.get(9),new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                Toast.makeText(TweetDetail.this, "Reply success", Toast.LENGTH_SHORT).show();
                flReply.setVisibility(View.VISIBLE);
                flDoReply.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(TweetDetail.this,"Reply not success", Toast.LENGTH_SHORT).show();
                flReply.setVisibility(View.VISIBLE);
                flDoReply.setVisibility(View.GONE);
            }
        });
    }

    private void showReply() {
        flReply.setVisibility(View.GONE);
        flDoReply.setVisibility(View.VISIBLE);
        txtScreenNameReply.setText(data.get(2));
        edDialogReply.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edDialogReply, InputMethodManager.SHOW_IMPLICIT);
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
