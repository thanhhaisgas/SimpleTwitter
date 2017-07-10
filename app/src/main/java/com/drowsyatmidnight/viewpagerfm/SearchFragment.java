package com.drowsyatmidnight.viewpagerfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.drowsyatmidnight.adapter.TweetAdapter;
import com.drowsyatmidnight.client.RestApplication;
import com.drowsyatmidnight.client.RestClient;
import com.drowsyatmidnight.model.Status;
import com.drowsyatmidnight.simpletwitter.MainTwitter;
import com.drowsyatmidnight.simpletwitter.R;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.drowsyatmidnight.simpletwitter.MainTwitter.toolbar;

/**
 * Created by haint on 01/07/2017.
 */

public class SearchFragment extends Fragment {
    @BindView(R.id.lvTweetSearch)
    RecyclerView lvTweetSearch;

    private TweetAdapter tweetAdapter;
    private View rootView;
    private SearchView searchView;
    private Menu menu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.searchfm, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        MainTwitter.toolbar.setNavigationOnClickListener(v -> {
            searchView.onActionViewCollapsed();
            toolbar.setNavigationIcon(null);
            MainTwitter.view2.setVisibility(View.VISIBLE);
            MainTwitter.titleTab.setVisibility(View.VISIBLE);
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchViewItem.getActionView();
        openSearchView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void fetchData(String q) {
        RestClient client = RestApplication.getRestClient();
        client.getSearchResult(q, new JsonHttpResponseHandler() {
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

    private void openSearchView() {
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        searchView.setOnSearchClickListener(v -> {
            MainTwitter.view2.setVisibility(View.GONE);
            MainTwitter.titleTab.setVisibility(View.GONE);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        });
        searchView.setOnCloseListener(() -> {
            MainTwitter.view2.setVisibility(View.VISIBLE);
            MainTwitter.titleTab.setVisibility(View.VISIBLE);
            toolbar.setNavigationIcon(null);
            return false;
        });
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(android.R.color.white));
    }
}
