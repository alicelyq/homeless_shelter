package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.CSVFile;
import com.example.scorpiowg.a2340project.model.Model;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // two options:login and registration
        Button login = findViewById(R.id.login);
        Button registration = findViewById(R.id.registration);

        // next actions after button clicked
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, RegUserTypeActivity.class);

        //read csv file
        InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_db);
        CSVFile shelterFile = new CSVFile(inputStream);
        Map<Integer, String[]> shelterinfo = shelterFile.read();


        //put data in shelterdb
        Model.getInstance().setShelterdb(shelterinfo);


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
