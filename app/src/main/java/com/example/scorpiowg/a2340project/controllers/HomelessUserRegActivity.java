package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Spinner;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Homeless;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by nancy on 2/17/18.
 */

public class HomelessUserRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeless_user_reg);

        //get the spinner from the xml
        final Spinner genderSpinner = findViewById(R.id.gender);
        final Spinner veteranSpinner = findViewById(R.id.veteran);

        String[] genderchoice = new String[]{"Male", "Female", "Other"};
        String[] veteranchoice = new String[]{"Yes", "No"};

        SpinnerAdapter genderadapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genderchoice);
        genderSpinner.setAdapter(genderadapter);

        SpinnerAdapter veteranadapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, veteranchoice);
        veteranSpinner.setAdapter(veteranadapter);

        //database hashmap
        final Map<String, User> database = Model.getInstance().getDatabase();
//        final DatabaseReference firebaseref = FirebaseDatabase.getInstance().getReference();

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
        Button register = findViewById(R.id.register);


        // init next actions
        final Intent reg_user_typePage = new Intent(this, RegUserTypeActivity.class);
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, HomelessUserRegActivity.class);

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
                String userId = userinput.getText().toString();
                String password = passinput.getText().toString();
                String confirm = confinput.getText().toString();
                String username = ((EditText)findViewById(R.id.input_name)).getText().toString();
                String govId = ((EditText)findViewById(R.id.govid)).getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();
                int age = Integer.parseInt(((EditText)findViewById(R.id.age)).getText().toString());
                int familyNum = Integer.parseInt(
                        ((EditText)findViewById(R.id.familynum)).getText().toString());
                boolean isFamily = true;
                if (familyNum <= 1) {
                    isFamily = false;
                }
                boolean isVeteran = Boolean.valueOf(veteranSpinner.getSelectedItem().toString());

                // next action
                if (password.equals(confirm) && !database.containsKey(userId)) {
                    // success
                    User curUser = new Homeless(username, userId, password,
                            true, govId, gender, isVeteran, isFamily, familyNum, age);
                    database.put(userId, curUser);
                    DatabaseReference realDB = FirebaseDatabase.getInstance().getReference();
                    //noinspection ChainedMethodCall,ChainedMethodCall
                    realDB.child("users").child(userId).setValue(curUser);
                    //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                    realDB.child("users").child(userId).child("claim").setValue(curUser.getClaim());
                    //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                    realDB.child("users").child(userId).child("beds").setValue(curUser.getBeds());
                    //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                    realDB.child("users").child(userId).child("type").setValue("Homeless");
                    startActivity(loginPage);
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
