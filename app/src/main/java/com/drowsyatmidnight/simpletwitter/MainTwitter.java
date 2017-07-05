package com.drowsyatmidnight.simpletwitter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.adapter.TabsPagerAdapter;
import com.drowsyatmidnight.client.RestApplication;
import com.drowsyatmidnight.client.RestClient;
import com.drowsyatmidnight.viewpagerfm.HomeFragment;
import com.drowsyatmidnight.viewpagerfm.MessageFragment;
import com.drowsyatmidnight.viewpagerfm.NotificationFragment;
import com.drowsyatmidnight.viewpagerfm.SearchFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainTwitter extends AppCompatActivity{
    @BindView(R.id.tabs)
    TabLayout taTwitter;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.fabNewTweet)
    FloatingActionButton fabNewTweet;
    @BindView(R.id.imgAvatarProfile)
    ImageView imgAvatarProfile;
    private TabsPagerAdapter adapter;
    private static final int[] tabIconsSelected = {
            R.drawable.homew,
            R.drawable.searchw,
            R.drawable.notificationw,
            R.drawable.messagew
    };
    private static final int[] tabIconsUnSelected = {
            R.drawable.homeg,
            R.drawable.searchg,
            R.drawable.notificationg,
            R.drawable.messageg,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_twitter);
        ButterKnife.bind(this);
        getProfile();
        setUpView();
    }

    private void getProfile(){
        RestClient client = RestApplication.getRestClient();
        client.getProfile(0, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                try {
                    String urlImage = jsonObject.getString("profile_image_url");
                    Glide.with(MainTwitter.this)
                            .load(urlImage)
                            .into(imgAvatarProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
    }

    private void setUpView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupViewPager(viewPager);

        taTwitter.setupWithViewPager(viewPager);
        setupTabIcons();

        fabNewTweet.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NewTweetDialog newFragment = new NewTweetDialog();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
            adapter.getItem(0).getId();
        });
    }

    private void setupTabIcons() {
        taTwitter.getTabAt(0).setIcon(tabIconsSelected[0]);
        taTwitter.getTabAt(1).setIcon(tabIconsUnSelected[1]);
        taTwitter.getTabAt(2).setIcon(tabIconsUnSelected[2]);
        taTwitter.getTabAt(3).setIcon(tabIconsUnSelected[3]);
        taTwitter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i<4; i++){
                    if(i!=tab.getPosition()){
                        taTwitter.getTabAt(i).setIcon(tabIconsUnSelected[i]);
                    }else {
                        taTwitter.getTabAt(i).setIcon(tabIconsSelected[i]);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new SearchFragment());
        adapter.addFragment(new NotificationFragment());
        adapter.addFragment(new MessageFragment());
        viewPager.setAdapter(adapter);
    }

}
