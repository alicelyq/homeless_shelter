package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.scorpiowg.a2340project.R;

/**
 * Created by nancy on 2/17/18.
 */

public class RegUserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user_type);

        // choose user type
        Button homeless_user = findViewById(R.id.homeless_user);
        Button shelter_emp_user = findViewById(R.id.shelter_emp_user);
        Button admin_user = findViewById(R.id.admin_user);

        Button cancel_reg_user_type = findViewById(R.id.cancel_reg_user_type);

        // next actions after button clicked
        final Intent mainpage = new Intent(this, MainActivity.class);
        final Intent shelter_emp_page = new Intent(this, ShelterEmpRegActivity.class);
        final Intent homeless_user_page = new Intent(this, HomelessUserRegActivity.class);
        final Intent admin_user_page = new Intent(this, AdminUserRegActivity.class);

        cancel_reg_user_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(mainpage);
            }
        });

        shelter_emp_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(shelter_emp_page);
            }
        });

        admin_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(admin_user_page);
            }
        });

        homeless_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(homeless_user_page);
            }
        });


    }

}
