package com.drowsyatmidnight.simpletwitter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.client.RestApplication;
import com.drowsyatmidnight.client.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by haint on 02/07/2017.
 */

public class NewTweetDialog extends DialogFragment {
    private View rootView;
    @BindView(R.id.toolBarNewTweet)
    Toolbar toolbar;
    @BindView(R.id.edTweet)
    EditText edTweet;
    @BindView(R.id.txtAmount)
    TextView txtAmount;
    @BindView(R.id.btnTweet)
    Button btnTweet;
    @BindView(R.id.imageView1)
    ImageView imgProfile;
    private int amountText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_newtweet, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, rootView);
        setupView();
        countText();
        btnTweet.setOnClickListener(v -> postTweet());
    }

    private void setupView() {
        getImgProfile();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);
        edTweet.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edTweet, InputMethodManager.SHOW_IMPLICIT);
    }

    private void getImgProfile() {
        RestClient client = RestApplication.getRestClient();
        client.getProfile(0, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                try {
                    String urlImage = jsonObject.getString("profile_image_url");
                    Glide.with(getContext())
                            .load(urlImage)
                            .into(imgProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void countText() {
        edTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amountText = Integer.parseInt(txtAmount.getText().toString());
                if (before<count){
                    amountText -= 1;
                    txtAmount.setText(String.valueOf(amountText));
                }else {
                    if (before>count){
                        amountText += 1;
                        txtAmount.setText(String.valueOf(amountText));
                    }
                }

                if (amountText<0){
                    txtAmount.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }else {
                    txtAmount.setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void postTweet() {
        if(amountText>=0){
            RestClient client = RestApplication.getRestClient();
            client.postTweet(edTweet.getText().toString(), new JsonHttpResponseHandler(){
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                    Toast.makeText(getContext(),"Post tweet success", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return dialog;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
            dismiss();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
