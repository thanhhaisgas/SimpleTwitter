package com.drowsyatmidnight.simpletwitter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    private ImageView imgNavHeader;
    private TextView txtUserNameHeader, txtRealNameHeader;
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

    public static String test = "aloha";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_twitter);
        ButterKnife.bind(this);
        setUpView();
        getProfile();
        addControls();
    }

    private void addControls() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    Toast.makeText(MainTwitter.this,"search",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweet_searching, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void getProfile(){
        RestClient client = RestApplication.getRestClient();
        client.getProfile(0, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                try {
                    String urlImage = jsonObject.getString("profile_image_url");
                    String name = jsonObject.getString("name");
                    String screen_name = jsonObject.getString("screen_name");
                    Glide.with(MainTwitter.this)
                            .load(urlImage)
                            .into(imgAvatarProfile);
                    Glide.with(MainTwitter.this)
                            .load(urlImage)
                            .into(imgNavHeader);
                    txtUserNameHeader.setText(name);
                    txtRealNameHeader.setText("@"+screen_name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        client.getMention(0, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("mention", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("fail", throwable.toString());
            }
        });
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        imgNavHeader = (ImageView) headerLayout.findViewById(R.id.imgNavHeader);
        txtUserNameHeader = (TextView) headerLayout.findViewById(R.id.txtUserNameHeader);
        txtRealNameHeader = (TextView) headerLayout.findViewById(R.id.txtRealNameHeader);
        imgNavHeader.setOnClickListener(v -> {
            goProfile(MainTwitter.this);
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

    private void goProfile(Context context){
        RestClient client = RestApplication.getRestClient();
        client.getProfile(0, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                try {
                    Log.d("profile", jsonObject.toString());
                    List<String> data = new ArrayList<>();
                    data.add(jsonObject.getString("id_str"));
                    data.add(jsonObject.getString("name"));
                    data.add(jsonObject.getString("screen_name"));
                    data.add(jsonObject.getString("location"));
                    data.add(jsonObject.getString("description"));
                    data.add(String.valueOf(jsonObject.getLong("followers_count")));
                    data.add(String.valueOf(jsonObject.getLong("friends_count")));
                    data.add(jsonObject.getString("profile_background_image_url"));
                    data.add(jsonObject.getString("profile_image_url"));
                    Intent goProfile = new Intent(context, ProfileActivity.class);
                    goProfile.putStringArrayListExtra("data", (ArrayList<String>) data);
                    context.startActivity(goProfile);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
