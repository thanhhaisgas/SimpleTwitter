package com.drowsyatmidnight.viewpagerfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.adapter.TweetAdapter;
import com.drowsyatmidnight.client.RestApplication;
import com.drowsyatmidnight.client.RestClient;
import com.drowsyatmidnight.model.Status;
import com.drowsyatmidnight.simpletwitter.R;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by haint on 01/07/2017.
 */

public class SearchFragment extends Fragment {
    @BindView(R.id.lvTweetSearch)
    RecyclerView lvTweetSearch;

    private TweetAdapter tweetAdapter;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.searchfm, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void fetchData(String q) {
        RestClient client = RestApplication.getRestClient();
        client.getSearchResult("trump", new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Gson gson = new Gson();
                Status status = gson.fromJson(String.valueOf(jsonObject), Status.class);
                tweetAdapter = new TweetAdapter(status.getStatuses(), rootView.getContext());
                lvTweetSearch.setAdapter(tweetAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
                lvTweetSearch.setLayoutManager(layoutManager);
            }
        });
    }
}
