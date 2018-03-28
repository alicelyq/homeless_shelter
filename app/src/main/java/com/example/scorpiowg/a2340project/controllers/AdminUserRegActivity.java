package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Admin;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.ShelterEmployee;
import com.example.scorpiowg.a2340project.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by nancy on 2/17/18.
 */

public class AdminUserRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_reg);

        //database hashmap
        final HashMap<String, User> database = Model.getInstance().getDatabase();
        final DatabaseReference firebaseref = FirebaseDatabase.getInstance().getReference();

        // check if this page was loaded from a registration error
        String error = getIntent().getStringExtra("error");
        if (error != null) {
            TextView passwordError = findViewById(R.id.passwordError);
            TextView useridError = findViewById(R.id.userIDError);
            TextView codeError = findViewById(R.id.codeError);
            if (error.equals("all")) {
                passwordError.setVisibility(View.VISIBLE);
                useridError.setVisibility(View.VISIBLE);
            } else if (error.equals("password")) {
                passwordError.setVisibility(View.VISIBLE);
            } else if (error.equals("name")) {
                useridError.setVisibility(View.VISIBLE);
            }
            if (error.equals("code")) {
                codeError.setVisibility(View.VISIBLE);
            }
        }

        // two buttons
        Button cancelRegister = findViewById(R.id.cancel);
        Button register = findViewById(R.id.register);


        // init next actions
        final Intent reg_user_typePage = new Intent(this, RegUserTypeActivity.class);
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, AdminUserRegActivity.class);

        // click actions
        cancelRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(reg_user_typePage);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // inputs
                EditText userinput = findViewById(R.id.userid);
                EditText passinput = findViewById(R.id.password);
                EditText confinput = findViewById(R.id.confirm);
                EditText admincodeinput = findViewById(R.id.admincode);
                String userId = userinput.getText().toString();
                String password = passinput.getText().toString();
                String confirm = confinput.getText().toString();
                String admincode = admincodeinput.getText().toString();

                // next action
                if (password.equals(confirm) && !database.containsKey(userId)) {
                    EditText nameinput = findViewById(R.id.input_name);
                    String username = nameinput.getText().toString();
                    //code check only if all other conditions passed
                    if (!admincode.equals("code")) {
                        Bundle b = new Bundle();
                        b.putString("error", "code");
                        registerPage.putExtras(b);
                        startActivity(registerPage);
                    } else {
                        //success
                        User curUser = new Admin(username, userId, password, true);
                        database.put(userId, curUser);
                        DatabaseReference realDB = FirebaseDatabase.getInstance().getReference();
                        realDB.child("users").child(userId).setValue(curUser);
                        realDB.child("users").child(userId).child("claim").setValue(curUser.getClaim());
                        realDB.child("users").child(userId).child("beds").setValue(curUser.getBeds());
                        realDB.child("users").child(userId).child("type").setValue("Admin");
                     
                        startActivity(loginPage);
                    }
                } else {
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
