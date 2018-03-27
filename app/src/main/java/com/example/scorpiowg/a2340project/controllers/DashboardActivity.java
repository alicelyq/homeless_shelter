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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by wangjingbo on 3/3/18.
 */

public class DashboardActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        TextView user = findViewById(R.id.userinfo);
        Model.getInstance().setUser((User) Model.getInstance().getDatabase().get(getIntent().getStringExtra("userId")));
        user.setText(Model.getInstance().getDatabase().get(getIntent().getStringExtra("userId")).toString());

        Button releaseButton = findViewById(R.id.release);
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        final User myuser = Model.getInstance().getUser();
        final Shelter myshelter = myuser.getClaim();
        if (myshelter != null) {
            releaseButton.setVisibility(View.VISIBLE);
            releaseButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    myuser.setClaim(null);
                    int releaseNum = myuser.getBeds();

                    //update firebase with new occupied value
                    int newOcc = myshelter.getOccupied() - releaseNum;
                    database.child("shelters").child(myshelter.getShelterId()).child("occupied").setValue(newOcc);
                    myshelter.setOccupied(newOcc);

                    //reload dashboard
                    dashboardPage.putExtra("userId", getIntent().getStringExtra("userId").toString());
                    startActivity(dashboardPage);
                }
            });
        }

        Button shelter = findViewById(R.id.search);
        final Intent shelterPage = new Intent(this, ShelterListActivity.class);
        shelter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shelterPage.putExtra("userId", getIntent().getStringExtra("userId").toString());
                shelterPage.putExtra("filter", "0");
                startActivity(shelterPage);
                Log.d("debug", "to shelter list");
            }
        });

    }
}


//note: get current available through firebase
/*
database.child("shelters").child(myshelter.getShelterId()).child("occupied").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
                String curoccupied = dataSnapshot.getValue(String.class);
                myshelter.setOccupied(curoccupied);
                }

        @Override
        public void onCancelled(DatabaseError databaseError) { }
  });
*/
