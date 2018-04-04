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

        // database
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // check for bed claiming
        if (getIntent().getStringExtra("claim") != null) {
            Log.d("process", "claim clicked");
            if (Model.getInstance().getUser().getClaim() != null) {
                TextView error = findViewById(R.id.error);
                error.setVisibility(View.VISIBLE);
            } else if (Integer.parseInt(getIntent().getStringExtra("claim").toString()) > Integer.parseInt(Model.getInstance().getShelter().getCapacity()) - Model.getInstance().getShelter().getOccupied()) {
                TextView limit = findViewById(R.id.limit);
                limit.setVisibility(View.VISIBLE);
            } else {
                Log.d("process", "successful claim");
                Model.getInstance().getUser().setClaim(Model.getInstance().getShelter());
                Model.getInstance().getUser().setBeds(Integer.parseInt(getIntent().getStringExtra("claim").toString()));
                Log.d("process", "this user claimed shelter " + Model.getInstance().getUser().getClaim().toString());
                Log.d("process", "this user claimed " + Integer.toString(Model.getInstance().getUser().getBeds()) + "bedspaces");
                TextView success = findViewById(R.id.success);
                success.setVisibility(View.VISIBLE);

                //update firebase
                final Shelter myshelter = Model.getInstance().getShelter();
                int claimed = Integer.parseInt(getIntent().getStringExtra("claim"));
                int newOcc = Model.getInstance().getShelter().getOccupied() + claimed;
                myshelter.setOccupied(newOcc);
                Log.d("process", "updating database");
                database.child("shelters").child(myshelter.getShelterId()).child("occupied").setValue(newOcc);
                database.child("users").child(Model.getInstance().getUser().getUserId()).child("claim").setValue(myshelter);
                database.child("users").child(Model.getInstance().getUser().getUserId()).child("beds").setValue(newOcc);
            }
        }

        // handle refresh from claim button
        if (getIntent().getStringExtra("shelterId") != null) {
            String id = getIntent().getStringExtra("shelterId");
            TextView info = findViewById(R.id.info);
            Model.getInstance().setCurrentShelter((Shelter)Model.getInstance().getShelters().get(id));
            info.setText(Model.getInstance().getShelter().toString());
        } else {
            TextView info = findViewById(R.id.info);
            info.setText(Model.getInstance().getShelter().toString());
        }

        // claim button functionaloty
        final Intent claimBedspace = new Intent(this, ShelterInfoActivity.class);
        Button claim = findViewById(R.id.claim);
        final TextView beds = findViewById(R.id.beds);
        claim.setOnClickListener(new View.OnClickListener() {
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
            public void onClick(View v) {
                dashboardPage.putExtra("userId", Model.getInstance().getUser().getUserId());
                startActivity(dashboardPage);
            }
        });

        Button toList = findViewById(R.id.backToList);
        final Intent shelterListPage = new Intent(this, ShelterListActivity.class);
        toList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shelterListPage.putExtra("filter", "0");
                startActivity(shelterListPage);
            }
        });
    }
}
