package com.drowsyatmidnight.viewpagerfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.drowsyatmidnight.adapter.TweetAdapter;
import com.drowsyatmidnight.client.RestApplication;
import com.drowsyatmidnight.client.RestClient;
import com.drowsyatmidnight.database.FetchTweet;
import com.drowsyatmidnight.simpletwitter.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by haint on 01/07/2017.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.lvTweet)
    RecyclerView lvTweet;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;

    private TweetAdapter tweetAdapter;
    private int page = 0;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.homefm, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeToRefresh.setOnRefreshListener(() -> fetchData());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    public void fetchData() {
        RestClient client = RestApplication.getRestClient();
        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                FetchTweet fetchTweet = new FetchTweet(jsonArray);
                tweetAdapter = new TweetAdapter(fetchTweet.getTimelines(), rootView.getContext());
                lvTweet.setAdapter(tweetAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
                lvTweet.setLayoutManager(layoutManager);
                tweetAdapter.setListener(() -> loadTweetMore());
                swipeToRefresh.setRefreshing(false);
            }
        });
    }

    private void loadTweetMore() {
        page+=1;
        RestClient client = RestApplication.getRestClient();
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                if(jsonArray == null){
                    Toast.makeText(getActivity(),"Limit api",Toast.LENGTH_SHORT).show();
                }else {
                    FetchTweet fetchTweet = new FetchTweet(jsonArray);
                    tweetAdapter.appendData(fetchTweet.getTimelines());
                }
            }
        });
    }
}
