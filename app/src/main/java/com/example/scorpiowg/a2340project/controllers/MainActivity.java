package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The major activity of the app.
 */

public class MainActivity extends AppCompatActivity {

    SignInButton signInButton;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Model modelInstance = Model.getInstance();

        // buttons
        Button login = findViewById(R.id.login);
        Button registration = findViewById(R.id.registration);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        mAuth = FirebaseAuth.getInstance();

        // intents
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, RegUserTypeActivity.class);

        // read csv file, put initial shelters in local device
        InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_db);

        CSVFile shelterFile = new CSVFile(inputStream);
        Map<String, String[]> shelterinfo = shelterFile.read();

        Map<String, Shelter> newPair = new HashMap<>();
        for (String s: shelterinfo.keySet()) {
            String[] shelterVal = shelterinfo.get(s);
            Shelter newShelter = new Shelter(s,shelterVal[0], shelterVal[1],
                    shelterVal[2], shelterVal[3], shelterVal[4], shelterVal[5],
                    shelterVal[6], shelterVal[7]);
            newPair.put(s, newShelter);
        }
        modelInstance.setShelters(newPair);


        // uncomment when refreshing database
        // refreshDatabase(shelterinfo);

        // shelters from database to local device
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference DBsheltersRef = database.child("shelters");
        DBsheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("process" ,"Gets Database Shelter Data");
                 Map<String, Shelter> shelters = modelInstance.getShelters();
                for (String s: shelters.keySet()) {
                    DataSnapshot occupiedCell =
                            dataSnapshot.child(shelters.get(s).getShelterId()).child("occupied");
                    int occupied = 0;
                    if (occupiedCell != null) {
                        occupied = occupiedCell.getValue(Integer.class);
                    }
                    shelters.get(s).setOccupied(occupied);
                    Log.d("process", "Shelter ID: " + s + ": " + "Occupied: " +
                            shelters.get(s).getOccupied());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // users from database to local device
        final DatabaseReference DBusersRef = database.child("users");
        DBusersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("process" ,"Gets Database User Data");
                 Map localUsers = modelInstance.getDatabase();
                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    User curUser = null;
                    
                    if ("ShelterEmployee".equals(user.child("type").getValue(String.class))) {
                        curUser = new ShelterEmployee(user.child("name").getValue(String.class)
                                , user.child("userId").getValue(String.class)
                                , user.child("password").getValue(String.class)
                                , user.child("shelterId").getValue(String.class)
                                , user.child("accountState").getValue(Boolean.class));
                    } else 
                        if ("Admin".equals(user.child("type").getValue(String.class))) {
                            curUser = new Admin(user.child("name").getValue(String.class)
                                , user.child("userId").getValue(String.class)
                                , user.child("password").getValue(String.class)
                                , user.child("accountState").getValue(Boolean.class));
                    } else {
                        if (user.child("accountState") != null && user.child("isVeteran") != null
                                && user.child("isFamily") != null
                                && user.child("familyNum") != null
                                && user.child("age") != null) {
                            curUser = new Homeless(user.child("name").getValue(String.class)
                                    , user.child("userId").getValue(String.class)
                                    , user.child("password").getValue(String.class)
                                    , user.child("govId").getValue(String.class)
                                    , user.child("accountState").getValue(boolean.class)
                                    , user.child("gender").getValue(String.class)
                                    , user.child("isVeteran").getValue(boolean.class)
                                    , user.child("isFamily").getValue(boolean.class)
                                    , user.child("familyNum").getValue(int.class)
                                    , user.child("age").getValue(int.class));
                        }
                    }
                    if (user.child("beds") != null) {
                        curUser.setBeds(user.child("beds").getValue(int.class));
                    }
                    curUser.setClaim(user.child("claim").getValue(Shelter.class));
                    localUsers.put(curUser.getUserId(), curUser);
                    Log.d("process", "User ID: " + curUser.getUserId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // button functions
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("process", "click login button");
                startActivity(loginPage);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("process", "click register button");
                startActivity(registerPage);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("process", "click google sing in button");
                signIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("NancyCheck", "onauthstatechanged");
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(registerPage);
                }

            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("googleLogin", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("googleLogin", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("googleLogin", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("googleLogin", "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }

    // refresh database
//    private void refreshDatabase(Map<String, String[]> shelterinfo) {
//        for (String s: shelterinfo.keySet()) {
//            String[] shelterVal = shelterinfo.get(s);
//            modelInstance.addNewShelter(s, shelterVal[0], shelterVal[1], shelterVal[2],
//                    shelterVal[3], shelterVal[4], shelterVal[5], shelterVal[6],
//                    shelterVal[7], 0);
//        }
//    }

}
