package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.User;

import java.util.Map;

/**
 * Created by wangjingbo on 2/11/18.
 */


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Model modelInstance = Model.getInstance();

        // all users dictionary
        // a little confusing because it's not actually database
        final Map<String, User> database = modelInstance.getDatabase();

        // check if this page was loaded from a login error

        if (getIntent().getStringExtra("error") != null) {
            TextView error = findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
            if (Model.getInstance().loginAttempt >= 3 ||
                    !((User)Model.getInstance().getDatabase()
                            .get(Model.getInstance().accountAttempt)).getAccountState()) {
                error.setText("account is locked");
            }
        }

        // buttons
        Button cancellogin = findViewById(R.id.cancellogin);
        Button submitlogin = findViewById(R.id.submitlogin);

        // intents
        final Intent mainPage = new Intent(this, MainActivity.class);
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        final Intent loginErrorPage = new Intent(this, LoginActivity.class);

        // button function
        cancellogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainPage);
            }
        });
        submitlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // fetch login data from user input
                    EditText usernameinput = findViewById(R.id.usernameinput);
                    EditText passwordinput = findViewById(R.id.passwordinput);
                    String userId = usernameinput.getText().toString();
                    String password = passwordinput.getText().toString();

                    // login info validation
                    //  1. userId exists
                    //  2. password matches this userId

                    boolean successful = modelInstance.loginUser(database, userId, password);
                    if (successful) {
                        startActivity(dashboardPage);
                    } else {
                        if (!Model.getInstance().accountAttempt.equals(userId)) {
                            Model.getInstance().accountAttempt = userId;
                            Model.getInstance().loginAttempt = 0;
                        }
                        Model.getInstance().loginAttempt++;

                        if (Model.getInstance().loginAttempt >= 3) {
                            ((User)Model.getInstance().getDatabase().get(userId)).setAccountState(false);
                            Log.d("debug", Boolean.toString(((User)Model.getInstance().getDatabase().get(userId)).getAccountState()));
                        }
                        loginErrorPage.putExtra("error", "true");
                        startActivity(loginErrorPage);
                    }
                }
        });
    }
}

