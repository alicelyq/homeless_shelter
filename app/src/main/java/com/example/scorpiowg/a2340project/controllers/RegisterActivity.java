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

import java.util.HashMap;

/**
 * Created by wangjingbo on 2/11/18.
 */

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_emp_reg);

        //database hashmap
        final HashMap<String, String> database = Model.getInstance().getDatabase();

        // check if this page was loaded from a registration error
        String error = getIntent().getStringExtra("error");
        if (error != null) {
            TextView passwordError = findViewById(R.id.passwordCheck);
            TextView usernameError = findViewById(R.id.useridtaken);
            if (error.equals("all")) {
                passwordError.setVisibility(View.VISIBLE);
                usernameError.setVisibility(View.VISIBLE);
            } else if (error.equals("password")) {
                passwordError.setVisibility(View.VISIBLE);
            } else if (error.equals("name")) {
                usernameError.setVisibility(View.VISIBLE);
            }
        }

        // two buttons
        Button cancelRegister = findViewById(R.id.cancel);
        Button register = findViewById(R.id.register);


        // init next actions
        final Intent mainPage = new Intent(this, MainActivity.class);
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, RegisterActivity.class);

        // click actions
        cancelRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mainPage);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // inputs
                    EditText userinput = findViewById(R.id.username);
                    EditText passinput = findViewById(R.id.password);
                    EditText confinput = findViewById(R.id.confirm);
                    String userId = userinput.getText().toString();
                    String password = passinput.getText().toString();
                    String confirm = confinput.getText().toString();

                    // next action
                    if (password.equals(confirm) && !database.containsKey(userId)) {
                        database.put(userId, password);
                        loginPage.putExtra("database",database);
                        Log.d("debug", Integer.toString(database.size()));
                        startActivity(loginPage);
                    } else {
                        registerPage.putExtra("database", database);
                        if (!password.equals(confirm) && database.containsKey(userId)) {
                            Bundle b = new Bundle();
                            b.putString("error", "all");
                            registerPage.putExtras(b);
                            startActivity(registerPage);
                        } else if (!password.equals(confirm)) {
                            Bundle b = new Bundle();
                            b.putString("error", "password");
                            registerPage.putExtras(b);
                            startActivity(registerPage);
                        } else {
                            Bundle b = new Bundle();
                            b.putString("error", "name");
                            registerPage.putExtras(b);
                            startActivity(registerPage);
                        }
                    }
                }
            });
    }
}
