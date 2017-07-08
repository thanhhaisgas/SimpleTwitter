package com.drowsyatmidnight.simpletwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.adapter.TabsPagerAdapter;
import com.drowsyatmidnight.viewpagerfm.HomeFragment;
import com.drowsyatmidnight.viewpagerfm.UserTimeLineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.circleImage)
    CardView circleImage;
    @BindView(R.id.tabsProfile)
    TabLayout taTwitter;
    @BindView(R.id.viewpagerProfile)
    ViewPager viewPager;
    @BindView(R.id.toolbarProfile)
    Toolbar toolbar;
    @BindView(R.id.header_cover_image)
    ImageView header_cover_image;
    @BindView(R.id.imgAvatarProfile)
    ImageView imgAvatarProfile;
    @BindView(R.id.txtUserNameProfile)
    TextView txtUserNameProfile;
    @BindView(R.id.txtRealNameProfile)
    TextView txtRealNameProfile;
    @BindView(R.id.txtDescriptionProfile)
    TextView txtDescriptionProfile;
    @BindView(R.id.txtLocationProfile)
    TextView txtLocationProfile;
    @BindView(R.id.txtFollowingProfile)
    TextView txtFollowingProfile;
    @BindView(R.id.txtFollowerProfile)
    TextView txtFollowerProfile;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.txtFollowing)
    TextView txtFollowing;
    @BindView(R.id.txtFollower)
    TextView txtFollower;
    private TabsPagerAdapter adapter;
    private List<String> data;
    public static int dataType = 0;
    public static String id_str;
    public static String screen_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setUpView();
        addControls();
        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if(verticalOffset<=-675){
                circleImage.setVisibility(View.GONE);
                txtUserNameProfile.setText("");
                txtRealNameProfile.setText("");
                collapsingToolbarLayout.setTitle(data.get(1));
                collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
            }else {
                circleImage.setVisibility(View.VISIBLE);
                txtUserNameProfile.setText(data.get(1));
                txtRealNameProfile.setText("@"+data.get(2));
                collapsingToolbarLayout.setTitle("");
            }
        });
    }

    private void addControls() {
        txtFollowingProfile.setOnClickListener(v -> {
            dataType = 0;
            Intent goFollowing = new Intent(ProfileActivity.this, FollowingActivity.class);
            startActivity(goFollowing);
        });
        txtFollowing.setOnClickListener(v -> txtFollowingProfile.callOnClick());
        txtFollowerProfile.setOnClickListener(v -> {
            dataType = 1;
            Intent goFollowing = new Intent(ProfileActivity.this, FollowingActivity.class);
            startActivity(goFollowing);
        });
        txtFollower.setOnClickListener(v -> txtFollowerProfile.callOnClick());
    }

    private void setUpView() {
        getData();
        setupViewPager(viewPager);
        taTwitter.setupWithViewPager(viewPager);
        setupTabIcons();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Glide.with(this)
                .load(data.get(7))
                .into(header_cover_image);
        Glide.with(this)
                .load(data.get(8))
                .into(imgAvatarProfile);
        txtUserNameProfile.setText(data.get(1));
        txtRealNameProfile.setText("@"+data.get(2));
        txtDescriptionProfile.setText(data.get(4));
        txtLocationProfile.setText(data.get(3));
        txtFollowingProfile.setText(data.get(6));
        txtFollowerProfile.setText(data.get(5));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Stop", "Stop");
    }

    private void getData() {
        data = new ArrayList<>();
        data = getIntent().getStringArrayListExtra("data");
        id_str = data.get(0);
        screen_name = data.get(2);
    }

    private void setupTabIcons() {
        taTwitter.getTabAt(0).setText("Tweets");
        taTwitter.getTabAt(1).setText("Timeline");
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(UserTimeLineFragment.newInstance(data.get(0), data.get(2)));
        adapter.addFragment(new HomeFragment());
        viewPager.setAdapter(adapter);
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
