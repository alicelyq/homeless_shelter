package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;
import com.example.scorpiowg.a2340project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wangjingbo on 3/3/18.
 */


public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Model modelInstance = Model.getInstance();

        TextView user = findViewById(R.id.userinfo);
        user.setText(modelInstance.getUser().toString());
        // button
        Button releaseButton = findViewById(R.id.release);
        Button shelter = findViewById(R.id.search);
        Button logout = findViewById(R.id.logout);

        // intents
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        final Intent shelterPage = new Intent(this, ShelterListActivity.class);
        final Intent mainPage = new Intent(this, MainActivity.class);
//        final Intent mapPage = new Intent(this, MapActivity.class);

        // real database
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // current state
        final User myuser = modelInstance.getUser();

        final Shelter myshelter = myuser.getClaim();

        // check if current user has shelter booked
        if (myshelter != null) {
            // add a release button to release his booking
            releaseButton.setVisibility(View.VISIBLE);
            releaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newOcc = myshelter.getOccupied() - myuser.getBeds();
                  
                    // update shelter side info
                    //  1. change occupied number on database
                    //  2. change occupied number locally
                    //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                    database.child("shelters")
                            .child(myshelter.getShelterId()).child("occupied").setValue(newOcc);
                    myshelter.setOccupied(newOcc);

                    // update user side info
                    //  1. change booking status on database
                    //  2. change booking status locally
                    //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall

                    database.child("users").child(myuser.getUserId()).child("claim").setValue(null);
                    myuser.setClaim(null);

                    // reload dashboard
                    startActivity(dashboardPage);
                }
            });
        }

        // go to shelter search page
        shelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shelterPage.putExtra("filter", "0");
                startActivity(shelterPage);
                Log.d("process", "click search");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(mainPage);
            }
        });

    }
}
