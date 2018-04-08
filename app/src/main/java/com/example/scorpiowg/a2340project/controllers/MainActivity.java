package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FeatureEnvy")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** buttons */
        Button login = findViewById(R.id.login);
        Button registration = findViewById(R.id.registration);


        /** intents */
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, RegUserTypeActivity.class);

        /** read csv file, put initial shelters in local device */
        @SuppressWarnings("ChainedMethodCall") InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_db);
        CSVFile shelterFile = new CSVFile(inputStream);
        Map<String, String[]> shelterinfo = shelterFile.read();

        HashMap<String, Shelter> newPair = new HashMap<>();
        for (String s: shelterinfo.keySet()) {
            String[] shelterVal = shelterinfo.get(s);
            Shelter newShelter = new Shelter(s,shelterVal[0], shelterVal[1], shelterVal[2], shelterVal[3], shelterVal[4], shelterVal[5], shelterVal[6], shelterVal[7]);
            newPair.put(s, newShelter);
        }
        //noinspection ChainedMethodCall
        Model.getInstance().setShelters(newPair);


        /** uncomment when refreshing database */
//        refreshDatabase(shelterinfo);

        /** shelters from database to local device */
        @SuppressWarnings("ChainedMethodCall") final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference DBsheltersRef = database.child("shelters");
        //noinspection FeatureEnvy
        DBsheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressWarnings("FeatureEnvy")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("process" ,"Gets Database Shelter Data");
                @SuppressWarnings("ChainedMethodCall") Map<String, Shelter> shelters = Model.getInstance().getShelters();
                for (String s: shelters.keySet()) {
                    @SuppressWarnings("ChainedMethodCall") int occupied = dataSnapshot.child(shelters.get(s).getShelterId()).child("occupied").getValue(Integer.class);
                    //noinspection ChainedMethodCall
                    shelters.get(s).setOccupied(occupied);
                    //noinspection ChainedMethodCall
                    Log.d("process", "Shelter ID: " + s + ": " + "Occupied: " + shelters.get(s).getOccupied());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        /** users from database to local device */
        final DatabaseReference DBusersRef = database.child("users");
        //noinspection FeatureEnvy
        DBusersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressWarnings("FeatureEnvy")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("process" ,"Gets Database User Data");
                @SuppressWarnings("ChainedMethodCall") Map localUsers = Model.getInstance().getDatabase();
                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    User curUser = null;
                    //noinspection ChainedMethodCall,ChainedMethodCall
                    if ("ShelterEmployee".equals(user.child("type").getValue(String.class))) {
                        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                        curUser = new ShelterEmployee(user.child("name").getValue(String.class)
                                , user.child("userId").getValue(String.class)
                                , user.child("password").getValue(String.class)
                                , user.child("accountState").getValue(boolean.class)
                                , user.child("shelterId").getValue(String.class));
                    } else //noinspection ChainedMethodCall,ChainedMethodCall
                        if ("Admin".equals(user.child("type").getValue(String.class))) {
                            //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                            curUser = new Admin(user.child("name").getValue(String.class)
                                , user.child("userId").getValue(String.class)
                                , user.child("password").getValue(String.class)
                                , user.child("accountState").getValue(boolean.class));
                    } else {
                            //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                        curUser = new Homeless(user.child("name").getValue(String.class)
                                , user.child("userId").getValue(String.class)
                                , user.child("password").getValue(String.class)
                                , user.child("accountState").getValue(boolean.class)
                                , user.child("govId").getValue(String.class)
                                , user.child("gender").getValue(String.class)
                                , user.child("isVeteran").getValue(boolean.class)
                                , user.child("isFamily").getValue(boolean.class)
                                , user.child("familyNum").getValue(int.class)
                                , user.child("age").getValue(int.class));
                    }
                    if (curUser == null) {
                        Log.d("debug", "type name might be wrong");
                    } else {
                        //noinspection ChainedMethodCall
                        curUser.setBeds(user.child("beds").getValue(int.class));
                        //noinspection ChainedMethodCall
                        curUser.setClaim(user.child("claim").getValue(Shelter.class));
                        localUsers.put(curUser.getUserId(), curUser);
                    }
                    Log.d("process", "User ID: " + curUser.getUserId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        /** button functions */
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("process", "click login button");
                startActivity(loginPage);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("process", "click register button");
                startActivity(registerPage);
            }
        });
    }

    /** refresh database */
    private void refreshDatabase(Map<String, String[]> shelterinfo) {
        for (String s: shelterinfo.keySet()) {
            String[] shelterVal = shelterinfo.get(s);
            //noinspection ChainedMethodCall
            Model.getInstance().addNewShelter(s, shelterVal[0], shelterVal[1], shelterVal[2], shelterVal[3], shelterVal[4], shelterVal[5], shelterVal[6], shelterVal[7], 0);
        }
    }

}
