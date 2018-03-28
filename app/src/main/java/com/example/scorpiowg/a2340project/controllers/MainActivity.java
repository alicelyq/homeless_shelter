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
import com.example.scorpiowg.a2340project.model.Admin;
import com.example.scorpiowg.a2340project.model.CSVFile;
import com.example.scorpiowg.a2340project.model.Homeless;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;
import com.example.scorpiowg.a2340project.model.ShelterEmployee;
import com.example.scorpiowg.a2340project.model.User;
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

        final DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference firebaseSheltersRef = firebase.child("shelters");
        final DatabaseReference firebaseUsersRef = firebase.child("users");

//        save for populating database
        for (String s: shelterinfo.keySet()) {
            String[] shelterVal = shelterinfo.get(s);
            Model.getInstance().addNewShelter(s, shelterVal[0], shelterVal[1], shelterVal[2], shelterVal[3], shelterVal[4], shelterVal[5], shelterVal[6], shelterVal[7], 0);
        }

        firebaseSheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

        firebaseUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Log.d("testloademp",userSnapshot.child("name").getValue(String.class));
                        String name = userSnapshot.child("name").getValue(String.class);
                        String userId = userSnapshot.child("userId").getValue(String.class);
                        String password = userSnapshot.child("password").getValue(String.class);
                        boolean accountState = userSnapshot.child("accountState").getValue(boolean.class);
                        String claimid = userSnapshot.child("claim").getValue(String.class);
                        Shelter claim = (Shelter)Model.getInstance().getShelters().get(claimid);
                        int beds = userSnapshot.child("beds").getValue(int.class);

                        if (((String)(userSnapshot.child("type").getValue())).equals("shelterEmployee")) {
                            String shelterId = userSnapshot.child("shelterId").getValue(String.class);
                            User myuser = new ShelterEmployee(name, userId, password, accountState, shelterId);
                            myuser.setClaim(claim);
                            myuser.setBeds(beds);
                            Model.getInstance().getDatabase().put(userId, myuser);

                        } else if (((String)(userSnapshot.child("type").getValue())).equals("admin")) {
                            User myuser = new Admin(name, userId, password, accountState);
                            myuser.setClaim(claim);
                            myuser.setBeds(beds);
                            Model.getInstance().getDatabase().put(userId, myuser);

                        } else if (((String)(userSnapshot.child("type").getValue())).equals("homeless")) {
                            String govId = userSnapshot.child("govId").getValue(String.class);
                            String gender = userSnapshot.child("gener").getValue(String.class);
                            boolean isVeteran = userSnapshot.child("isVeteran").getValue(boolean.class);
                            boolean isFamily = userSnapshot.child("isFamily").getValue(boolean.class);
                            int familyNum = userSnapshot.child("familyNum").getValue(int.class);
                            int age = userSnapshot.child("age").getValue(int.class);

                            User myuser = new Homeless(name, userId, password, accountState, govId,
                                                    gender, isVeteran, isFamily, familyNum, age);
                            myuser.setClaim(claim);
                            myuser.setBeds(beds);
                            Model.getInstance().getDatabase().put(userId, myuser);

                        }
                    }
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
