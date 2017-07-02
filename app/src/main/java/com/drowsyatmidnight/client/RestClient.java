package com.drowsyatmidnight.client;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.drowsyatmidnight.simpletwitter.R;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by haint on 29/06/2017.
 */

public class RestClient extends OAuthBaseClient{

    public static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "OJpihwgfmMoLTGBpzXvSFAcLk";
    public static final String REST_CONSUMER_SECRET = "TwC9QhDjmNsAAxzYmfe5h4wuyK862pVpsj5LKi11oorY2pupzN";

    public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public RestClient(Context context) {
        super(context, REST_API_INSTANCE,
                REST_URL,
                REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET,
                String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
                        context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
    }

    public void postTweet(String body, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", body);
        getClient().post(apiUrl, params, handler);
    }

    public void getHomeTimeline(int page, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        getClient().get(apiUrl, params, handler);
    }

    public void getProfile(int page, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        getClient().get(apiUrl, params, handler);
    }
}
