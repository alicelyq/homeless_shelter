package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;


/**
 * Created by wangjingbo on 2/25/18.
 */

public class ShelterListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_list);

        // buttons
        Button filter = findViewById(R.id.filter);
        Button back = findViewById(R.id.back);
        Button clear = findViewById(R.id.clear);
        Button map = findViewById(R.id.map);

        // intents
        final Intent filterPage = new Intent(this, FilterActivity.class);
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        final Intent currentPage = new Intent(this, ShelterListActivity.class);
        final Intent shelterInfo = new Intent(this, ShelterInfoActivity.class);
        final Intent mapPage = new Intent(this, MapActivity.class);

        // for filtering purposes
        String gender = "";
        String ageRange = "";
        String shelterName = "";
        Model.getInstance().clearCurrentShelterList();


        // if filter is used
        if ("1".equals(getIntent().getStringExtra("filter"))) {
            gender = getIntent().getStringExtra("gender");
            ageRange = getIntent().getStringExtra("ageRange");
            shelterName = getIntent().getStringExtra("shelterName");
        }

        // filter button function
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPage.putExtra("userId", Model.getInstance().getUser().getUserId());
                startActivity(filterPage);

            }
        });

        // back button function
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(dashboardPage);

            }
        });

        // clear button function
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage.putExtra("filter", "0");
                startActivity(currentPage);

            }
        });

        // map button function
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mapPage);
                Log.d("process", "click map");
            }
        });

        // set view: user info
        TextView user = findViewById(R.id.user);
        user.setText(Model.getInstance().getUser().toString());

        // set view: shelter list
        LinearLayout parentLayout = findViewById(R.id.parentLayout);
        parentLayout.setGravity(Gravity.CENTER);

        // iterate over shelters and put each into view as button
        for (Object key : Model.getInstance().getShelters().keySet()) {
            if (checkFilter((String)key, gender, ageRange, shelterName)) {
                Model.getInstance().addCurrentShelter(
                        (Shelter) Model.getInstance().getShelters().get(key));
                final String id = (String) key;
                final Button shelter = new Button(this);
                LinearLayout.LayoutParams shelterListParams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                                , LinearLayout.LayoutParams.WRAP_CONTENT);

                shelterListParams.setMargins(0, 20, 0, 0);

                shelter.setLayoutParams(shelterListParams);
                shelter.setText(((Shelter) Model.getInstance().getShelters().get(key)).getName());
                int idSetter = Integer.parseInt((String) key);
                shelter.setId(idSetter);
                parentLayout.addView(shelter);

                shelter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shelterInfo.putExtra("shelterId", id);
                        startActivity(shelterInfo);

                    }
                });
            }
        }
    }

    // to see if this shelter is filtered out
    private boolean checkFilter(String key, CharSequence gender, String ageRange, String sheltername) {

        String constraint = ((Shelter)Model.getInstance().getShelters().get(key)).getRestriction();

        // if no fiilter chosen, return true
        if ("0".equals(getIntent().getStringExtra("filter"))) {
            return true;
        }

        // filter is chosen
        // gender filter

        Log.d("debug", "filter");
        boolean genderCheck = Model.getInstance().filterByGender(gender, constraint);

        // age range filter
        boolean ageRangeCheck = Model.getInstance().filterByAge(ageRange, constraint);


        // if shelter has no constraint, pass gender check and age range check
        if (constraint.contains("Anyone")) {
            genderCheck = true;
            ageRangeCheck = true;
        }

        // shelter name check
        Shelter shelter = (Shelter) Model.getInstance().getShelters().get(key);
        boolean shelterNameCheck = Model.getInstance().filterByName(sheltername, shelter);

        return genderCheck && ageRangeCheck && shelterNameCheck;
    }
}


