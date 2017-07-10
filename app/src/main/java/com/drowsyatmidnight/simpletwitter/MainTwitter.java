package com.drowsyatmidnight.simpletwitter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.fabNewTweet)
    FloatingActionButton fabNewTweet;
    @BindView(R.id.imgAvatarProfile)
    ImageView imgAvatarProfile;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    private ImageView imgNavHeader;
    private TextView txtUserNameHeader, txtRealNameHeader;
    private TabsPagerAdapter adapter;
    public static Toolbar toolbar;
    public static CardView view2;
    public static TextView titleTab;
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

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_twitter);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        view2 = (CardView) findViewById(R.id.view2);
        titleTab = (TextView) findViewById(R.id.titleTab);
        ButterKnife.bind(this);
        setUpView();
        getProfile();
        addControls();
    }

    private void addControls() {
        imgAvatarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(Gravity.LEFT);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem itemSearch = menu.findItem(R.id.action_search);
                if (position==0){
                    titleTab.setText("Home");
                }
                if(position==1){
                    titleTab.setText("Search Twitter");
                    itemSearch.setVisible(true);
                    view2.setVisibility(View.GONE);
                    titleTab.setVisibility(View.GONE);
                }else {
                    itemSearch.setVisible(false);
                    view2.setVisibility(View.VISIBLE);
                    titleTab.setVisibility(View.VISIBLE);
                    toolbar.setNavigationIcon(null);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                }
                if (position==2){
                    titleTab.setText("Mentions");
                }
                if (position==3){
                    titleTab.setText("Messages");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.tweet_searching, menu);
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return false;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_Person:
                goProfile(MainTwitter.this);
                break;
        }
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
