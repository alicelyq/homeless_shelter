package com.example.scorpiowg.a2340project.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.CSVFile;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

//        DatabaseReference nancytestdb = FirebaseDatabase.getInstance().getReference();

        //read csv file
        InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_db);
        CSVFile shelterFile = new CSVFile(inputStream);
        Map<String, String[]> shelterinfo = shelterFile.read();


        HashMap<String, Shelter> newPair = new HashMap<>();
        for (String s: shelterinfo.keySet()) {
            Log.d("debug", s);
            String[] shelterVal = shelterinfo.get(s);
            Shelter newShelter = new Shelter(s,shelterVal[0], shelterVal[1], shelterVal[2], shelterVal[3], shelterVal[4], shelterVal[5], shelterVal[6], shelterVal[7]);
            newPair.put(s, newShelter);
        }
        Model.getInstance().setShelters(newPair);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference DBsheltersRef = database.child("shelters");

        DBsheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("debug" ,"calls database data");
                HashMap<String, Shelter> shelters = Model.getInstance().getShelters();
                for (String s: shelters.keySet()) {
                    int occupied = dataSnapshot.child(shelters.get(s).getShelterId()).child("occupied").getValue(Integer.class);
                    shelters.get(s).setOccupied(occupied);
                    Log.d("debug", s + ": " + shelters.get(s).getOccupied());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d("debug", "login button");
                startActivity(loginPage);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d("debug", "register button");
                startActivity(registerPage);
            }
        });

    }
}
