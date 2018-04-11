package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.ShelterEmployee;
import com.example.scorpiowg.a2340project.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by nancy on 2/17/18.
 */


public class ShelterEmpRegActivity extends AppCompatActivity {

    @SuppressWarnings("FeatureEnvy")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_emp_reg);

        //database hashmap
        final Map<String, User> database = Model.getInstance().getDatabase();
        final DatabaseReference firebaseref = FirebaseDatabase.getInstance().getReference();

        // check if this page was loaded from a registration error
        String error = getIntent().getStringExtra("error");
        if (error != null) {
            TextView passwordError = findViewById(R.id.passwordError);
            TextView useridError = findViewById(R.id.userIDError);
            if ("all".equals(error)) {
                passwordError.setVisibility(View.VISIBLE);
                useridError.setVisibility(View.VISIBLE);
            } else if ("password".equals(error)) {
                passwordError.setVisibility(View.VISIBLE);
            } else if ("name".equals(error)) {
                useridError.setVisibility(View.VISIBLE);
            }
        }

        // two buttons
        Button cancelRegister = findViewById(R.id.cancel);
        final Button register = findViewById(R.id.register);


        // init next actions
        final Intent reg_user_typePage = new Intent(this, RegUserTypeActivity.class);
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, ShelterEmpRegActivity.class);

        // click actions
        cancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(reg_user_typePage);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inputs
                EditText userinput = findViewById(R.id.userid);
                EditText passinput = findViewById(R.id.password);
                EditText confinput = findViewById(R.id.confirm);
                EditText nameinput = findViewById(R.id.input_name);
                String userId = userinput.getText().toString();
                String password = passinput.getText().toString();
                String confirm = confinput.getText().toString();
                String username = nameinput.getText().toString();

                // next action

                boolean registerSuccess = Model.getInstance().registerUser(database, userId
                        , password, confirm, username, true, "1");

                if (registerSuccess) {
                    //success
                    User curUser = new ShelterEmployee(username, userId, password, true, "1");
                    DatabaseReference realDB = FirebaseDatabase.getInstance().getReference();

                    realDB.child("users").child(userId).setValue(curUser);
                    
                    realDB.child("users").child(userId).child("claim").setValue(curUser.getClaim());
                    
                    realDB.child("users").child(userId).child("beds").setValue(curUser.getBeds());
                    
                    realDB.child("users").child(userId).child("type").setValue("ShelterEmployee");
                  
                    startActivity(loginPage);
                } else {
                    //error
                    if (!password.equals(confirm) && database.containsKey(userId)) {
                        Bundle b = new Bundle();
                        b.putString("error", "all");
                        registerPage.putExtras(b);
                        startActivity(registerPage);
                    } else if (!password.equals(confirm)) {
                        Bundle b = new Bundle();
                        b.putString("error", "password");
                        registerPage.putExtras(b);
                        startActivity(registerPage);
                    } else {
                        Bundle b = new Bundle();
                        b.putString("error", "name");
                        registerPage.putExtras(b);
                        startActivity(registerPage);
                    }
                }
            }
        });
    }
}
