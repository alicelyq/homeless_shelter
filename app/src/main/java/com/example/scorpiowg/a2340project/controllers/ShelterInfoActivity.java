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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by wangjingbo on 2/25/18.
 */

public class ShelterInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);
        final Model modelInstance = Model.getInstance();
        

        // database
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // check for bed claiming
        if (getIntent().getStringExtra("claim") != null) {
            Log.d("process", "claim clicked");
            if (modelInstance.getUser().getClaim() != null) {
                TextView error = findViewById(R.id.error);
                error.setVisibility(View.VISIBLE);
            } else if (Integer.parseInt(getIntent().getStringExtra("claim"))
                    > (Integer.parseInt(modelInstance.getShelter().getCapacity())
                    - modelInstance.getShelter().getOccupied())) {
                TextView limit = findViewById(R.id.limit);
                limit.setVisibility(View.VISIBLE);
            } else {
                Log.d("process", "successful claim");
                modelInstance.getUser().setClaim(modelInstance.getShelter());
                modelInstance.getUser().setBeds(
                        Integer.parseInt(getIntent().getStringExtra("claim")));
                Log.d("process", "this user claimed shelter "
                        + modelInstance.getUser().getClaim().toString());
                Log.d("process", "this user claimed "
                        + Integer.toString(modelInstance.getUser().getBeds()) + "bedspaces");
                TextView success = findViewById(R.id.success);
                success.setVisibility(View.VISIBLE);

                //update firebase
                final Shelter myshelter = modelInstance.getShelter();
                int claimed = Integer.parseInt(getIntent().getStringExtra("claim"));
                int newOcc = modelInstance.getShelter().getOccupied() + claimed;
                myshelter.setOccupied(newOcc);
                Log.d("process", "updating database");
                database.child("shelters")
                        .child(myshelter.getShelterId())
                        .child("occupied").setValue(newOcc);
                database.child("users")
                        .child(modelInstance.getUser().getUserId())
                        .child("claim").setValue(myshelter);
                database.child("users")
                        .child(modelInstance.getUser().getUserId())
                        .child("beds").setValue(newOcc);
            }
        }

        // handle refresh from claim button
        if (getIntent().getStringExtra("shelterId") != null) {
            String id = getIntent().getStringExtra("shelterId");
            TextView info = findViewById(R.id.info);
            modelInstance.setCurrentShelter(
                    (Shelter)modelInstance.getShelters().get(id));
            info.setText(modelInstance.getShelter().toString());
        } else {
            TextView info = findViewById(R.id.info);
            info.setText(modelInstance.getShelter().toString());
        }

        // claim button functionaloty
        final Intent claimBedspace = new Intent(this, ShelterInfoActivity.class);
        Button claim = findViewById(R.id.claim);
        final TextView beds = findViewById(R.id.beds);
        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bedNum = beds.getText().toString();
                Log.d("debug", "claim");
                claimBedspace.putExtra("claim", bedNum);
                Log.d("debug", "claim " + bedNum + " bed spaces");
                startActivity(claimBedspace);
            }
        });

        // cancel button
        Button toDashboardButton = findViewById(R.id.toDashboard);
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        toDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardPage.putExtra("userId", modelInstance.getUser().getUserId());
                startActivity(dashboardPage);
            }
        });

        Button toList = findViewById(R.id.backToList);
        final Intent shelterListPage = new Intent(this, ShelterListActivity.class);
        toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shelterListPage.putExtra("filter", "0");
                startActivity(shelterListPage);
            }
        });
    }
}
