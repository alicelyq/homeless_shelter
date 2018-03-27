package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by wangjingbo on 2/25/18.
 */

public class ShelterInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        if (getIntent().getStringExtra("claim") != null) {
            Log.d("debug", "successfully detect claim");
            if (Model.getInstance().getUser().getClaim() != null) {
                TextView error = findViewById(R.id.error);
                error.setVisibility(View.VISIBLE);
            }
            if (Model.getInstance().getUser().getClaim() == null) {
                Log.d("debug", "successful claim");
                Model.getInstance().getUser().setClaim(Model.getInstance().getShelter());
                //Model.getInstance().getUser().setBeds(Integer.parseInt(getIntent().getStringExtra("claim").toString()));
                TextView success = findViewById(R.id.success);
                success.setVisibility(View.VISIBLE);

                final Shelter myshelter = Model.getInstance().getShelter();
                // get occupied space
//                Log.d("testdb", database.child("shelters").child(myshelter.getShelterId()).child("occupied").);
                database.child("shelters").child(myshelter.getShelterId()).child("occupied").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String curoccupied = dataSnapshot.getValue(String.class);
                        Log.d("snapshot", curoccupied);
                        Model.getInstance().getShelter().setOccupied(curoccupied);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

                //update firebase with new occupied value
                Log.d("nancytestclaim", "hi" + getIntent().getStringExtra("claim"));
                String claimed = getIntent().getStringExtra("claim");
//                String newOcc = Integer.toString(Integer.parseInt(myshelter.getOccupied()) + Integer.parseInt(claimed));
                Log.d("myshelter.getOccupied()", Model.getInstance().getShelter().getOccupied());
                String newOcc = Integer.toString(Integer.parseInt(Model.getInstance().getShelter().getOccupied()) + 5);
                database.child("shelters").child(myshelter.getShelterId()).child("occupied").setValue(newOcc);
                myshelter.setOccupied(newOcc);
                Log.d("myshelter.getOccupied()", Model.getInstance().getShelter().getOccupied());
            }
        }

        if (getIntent().getStringExtra("shelterId") != null) {
            String id = getIntent().getStringExtra("shelterId");
            TextView info = findViewById(R.id.info);
            Model.getInstance().setCurrentShelter((Shelter)Model.getInstance().getShelters().get(id));
            info.setText(Model.getInstance().getShelter().toString());
        } else {
            TextView info = findViewById(R.id.info);
            info.setText(Model.getInstance().getShelter().toString());
        }

        final Intent claimBedspace = new Intent(this, ShelterInfoActivity.class);
        Button claim = findViewById(R.id.claim);
        TextView beds = findViewById(R.id.beds);
        final String bedNum = beds.getText().toString();
        claim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("debug", "claim");
                claimBedspace.putExtra("claim", bedNum);
                startActivity(claimBedspace);
            }
        });

        // Jingbo need to change!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Button toDashboardButton = findViewById(R.id.toDashboard);
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        toDashboardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dashboardPage.putExtra("userID", Model.getInstance().getUser().getUserId());
                Log.d("nancytest_intent",Model.getInstance().getUser().getUserId());
                startActivity(dashboardPage);
            }
        });
    }
}
