package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("debug", Integer.toString(Model.getInstance().getDatabase().size()));

        // two options:login and registration
        Button login = findViewById(R.id.login);
        Button registration = findViewById(R.id.registration);

        // next actions after button clicked
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, RegisterActivity.class);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(loginPage);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(registerPage);
            }
        });

    }
}
