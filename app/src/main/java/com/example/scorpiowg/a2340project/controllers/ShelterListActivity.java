package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangjingbo on 2/25/18.
 */

public class ShelterListActivity extends AppCompatActivity {
//    private ArrayList<Button> buttons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_list);

        String gender = "";
        String ageRange = "";
        String shelterName = "";

        //filter info
        if (getIntent().getStringExtra("filter").equals("1")) {
            gender = getIntent().getStringExtra("gender");
            ageRange = getIntent().getStringExtra("ageRange");
            shelterName = getIntent().getStringExtra("shelterName");
        }

        //filter function
        final Intent filterPage = new Intent(this, FilterActivity.class);
        Button filter = findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filterPage.putExtra("userId", getIntent().getStringExtra("userId").toString());
                startActivity(filterPage);

            }
        });

        //back function
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dashboardPage.putExtra("userId", getIntent().getStringExtra("userId").toString());
                startActivity(dashboardPage);

            }
        });

        //clear function
        final Intent currentPage = new Intent(this, ShelterListActivity.class);
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPage.putExtra("userId", getIntent().getStringExtra("userId").toString());
                currentPage.putExtra("filter", "0");
                startActivity(currentPage);

            }
        });


        TextView user = findViewById(R.id.user);
        if (getIntent().getStringExtra("userId").equals("test")) {
            user.setText("test");
        } else {
            user.setText(Model.getInstance().getDatabase().get(getIntent().getStringExtra("userId")).toString());
        }

        // find parent frame
        LinearLayout parentLayout = findViewById(R.id.parentLayout);
        parentLayout.setGravity(Gravity.CENTER);

        final Intent shelterInfo = new Intent(this, ShelterInfoActivity.class);
        //iterate over model hashmap, and put data into view as button
        for (Object key : Model.getInstance().getShelters().keySet()) {
            if (checkFilter((String)key, gender, ageRange, shelterName)) {
                final String id = (String) key;
                final Button shelter = new Button(this);
                LinearLayout.LayoutParams shelterListParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                shelterListParams.setMargins(0, 20, 0, 0);
                shelter.setLayoutParams(shelterListParams);
                shelter.setText(((Shelter) Model.getInstance().getShelters().get(key)).getName());
                int idSetter = Integer.parseInt((String) key);
                shelter.setId(idSetter);
                parentLayout.addView(shelter);

                shelter.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        shelterInfo.putExtra("shelterId", id);
                        startActivity(shelterInfo);

                    }
                });
            }
        }
    }

    private boolean checkFilter(String key, String gender, String ageRange, String sheltername) {
        boolean genderCheck = false;
        boolean ageRangeCheck = false;
        boolean shelterNameCheck = false;

        String constraint = ((Shelter)Model.getInstance().getShelters().get(key)).getRestriction();

        // gender filter
        if (gender.equals("")) {
            return true;
        }
        if (gender.equals("Any")) {
            genderCheck = true;
        } else {
            if (constraint.indexOf(gender) != -1) {
                genderCheck = true;
            }
        }

        //age range filter
        if (ageRange.equals("Anyone")) {
            ageRangeCheck = true;
        } else {
            if (constraint.toLowerCase().indexOf(ageRange.toLowerCase()) != -1) {
                ageRangeCheck = true;
            }
            if (ageRange.equals("Families with newborns") && constraint.indexOf("Families w") != -1) {
                ageRangeCheck = true;
            }
        }

        // if shelter has no constraint, pass gender check and age range check
        if (constraint.indexOf("Anyone") != -1) {
            genderCheck = true;
            ageRangeCheck = true;
        }

        //shelter name check
        if (sheltername.equals("")) {
            shelterNameCheck = true;
        } else {
            if (((Shelter)Model.getInstance().getShelters().get(key)).getName().toLowerCase().indexOf(sheltername.toLowerCase()) != -1) {
                shelterNameCheck = true;
            }
        }

        return genderCheck && ageRangeCheck && shelterNameCheck;
    }
}


