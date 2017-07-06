package com.drowsyatmidnight.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.drowsyatmidnight.client.RestClient;
import com.drowsyatmidnight.simpletwitter.MainTwitter;
import com.drowsyatmidnight.simpletwitter.R;

public class LoginActivity extends OAuthLoginActionBarActivity<RestClient> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addEvents();
    }

    private void addEvents() {
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> getClient().connect());
    }


    @Override
    public void onLoginSuccess() {
        doLogin();
    }

    private void doLogin() {
        startActivity(new Intent(this, MainTwitter.class));
    }

    @Override
    public void onLoginFailure(Exception e) {
        Toast.makeText(this,"fail", Toast.LENGTH_SHORT).show();
    }
}
