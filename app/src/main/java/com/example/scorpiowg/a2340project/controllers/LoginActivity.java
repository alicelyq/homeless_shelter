package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.User;

import java.util.HashMap;

/**
 * Created by wangjingbo on 2/11/18.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //database hashmap
        final HashMap<String, User> database = Model.getInstance().getDatabase();

        // check if this page was loaded from a login error
        if (getIntent().getStringExtra("error") != null) {
            TextView error = findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
        }

        // two buttons
        Button cancellogin = findViewById(R.id.cancellogin);
        Button submitlogin = findViewById(R.id.submitlogin);

        // next actions after button clicked
        final Intent mainPage = new Intent(this, MainActivity.class);
        final Intent homePage = new Intent(this, HomepageActivity.class);
        final Intent loginErrorPage = new Intent(this, LoginActivity.class);

        // button action
        cancellogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mainPage);
            }
        });
        submitlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // two login text box
                    EditText usernameinput = findViewById(R.id.usernameinput);
                    EditText passwordinput = findViewById(R.id.passwordinput);
                    // login info data
                    String userId = usernameinput.getText().toString();
                    String password = passwordinput.getText().toString();

                    // login info validation
                    if (database.get(userId) != null && database.get(userId).getPassword().equals(password)) {
                        homePage.putExtra("userId", userId);
                        startActivity(homePage);
                    } else {
                        Bundle b = new Bundle();
                        b.putString("error", "true");
                        loginErrorPage.putExtras(b);
                        startActivity(loginErrorPage);
                    }
                }
        });
    }
}
