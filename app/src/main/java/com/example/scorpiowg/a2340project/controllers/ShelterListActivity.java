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

import java.util.ArrayList;


/**
 * Created by wangjingbo on 2/25/18.
 */

@SuppressWarnings("ALL")
public class ShelterListActivity extends AppCompatActivity {
    @SuppressWarnings("MagicNumber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_list);

        /** buttons */
        Button filter = findViewById(R.id.filter);
        Button back = findViewById(R.id.back);
        Button clear = findViewById(R.id.clear);
        Button map = findViewById(R.id.map);

        /** intents */
        final Intent filterPage = new Intent(this, FilterActivity.class);
        final Intent dashboardPage = new Intent(this, DashboardActivity.class);
        final Intent currentPage = new Intent(this, ShelterListActivity.class);
        final Intent shelterInfo = new Intent(this, ShelterInfoActivity.class);
        final Intent mapPage = new Intent(this, MapActivity.class);

        /** for filtering purposes */
        String gender = "";
        String ageRange = "";
        String shelterName = "";
        //noinspection ChainedMethodCall
        Model.getInstance().clearCurrentShelterList();

        /** if filter is used */
        //noinspection ChainedMethodCall,ChainedMethodCall
        if ("1".equals(getIntent().getStringExtra("filter"))) {
            //noinspection ChainedMethodCall
            gender = getIntent().getStringExtra("gender");
            //noinspection ChainedMethodCall
            ageRange = getIntent().getStringExtra("ageRange");
            //noinspection ChainedMethodCall
            shelterName = getIntent().getStringExtra("shelterName");
        }

        /** filter button function */
        filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //noinspection ChainedMethodCall,ChainedMethodCall
                filterPage.putExtra("userId", Model.getInstance().getUser().getUserId());
                startActivity(filterPage);

            }
        });

        /** back button function */
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(dashboardPage);

            }
        });

        /** clear button function */
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPage.putExtra("filter", "0");
                startActivity(currentPage);

            }
        });

        /** map button function */
        map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mapPage);
                Log.d("process", "click map");
            }
        });

        /** set view: user info */
        TextView user = findViewById(R.id.user);
        //noinspection ChainedMethodCall,ChainedMethodCall
        user.setText(Model.getInstance().getUser().toString());

        /** set view: shelter list */
        LinearLayout parentLayout = findViewById(R.id.parentLayout);
        parentLayout.setGravity(Gravity.CENTER);
        /** iterate over shelters and put each into view as button */
        //noinspection ChainedMethodCall,ChainedMethodCall
        for (Object key : Model.getInstance().getShelters().keySet()) {
            if (checkFilter((String)key, gender, ageRange, shelterName)) {
                //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
                Model.getInstance().addCurrentShelter((Shelter) Model.getInstance().getShelters().get(key));
                final String id = (String) key;
                final Button shelter = new Button(this);
                LinearLayout.LayoutParams shelterListParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //noinspection MagicNumber
                shelterListParams.setMargins(0, 20, 0, 0);
                shelter.setLayoutParams(shelterListParams);
                //noinspection ChainedMethodCall,ChainedMethodCall
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

    /** to see if this shelter is filtered out */
    private boolean checkFilter(String key, String gender, String ageRange, String sheltername) {
        boolean genderCheck = false;
        boolean ageRangeCheck = false;
        boolean shelterNameCheck = false;

        @SuppressWarnings("ChainedMethodCall") String constraint = ((Shelter)Model.getInstance().getShelters().get(key)).getRestriction();

        /** if no fiilter chosen, return true */
        //noinspection ChainedMethodCall,ChainedMethodCall
        if ("0".equals(getIntent().getStringExtra("filter"))) {
            return true;
        }

        /** filter is chosen */
        /** gender filter */
        if ("Any".equals(gender)) {
            genderCheck = true;
        } else {
            if (constraint.indexOf(gender) != -1) {
                genderCheck = true;
            }
        }

        /** age range filter */
        if ("Anyone".equals(ageRange)) {
            ageRangeCheck = true;
        } else {
            //noinspection ChainedMethodCall
            if (constraint.toLowerCase().indexOf(ageRange.toLowerCase()) != -1) {
                ageRangeCheck = true;
            } else if ("Families with newborns".equals(ageRange) && (constraint.indexOf("Families w") != -1)) {
                ageRangeCheck = true;
            }
        }

        /** if shelter has no constraint, pass gender check and age range check */
        if (constraint.indexOf("Anyone") != -1) {
            genderCheck = true;
            ageRangeCheck = true;
        }

        Log.d("debug", "shelterNameCheck always set to true1");
        /** shelter name check */
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        if (((Shelter)Model.getInstance().getShelters().get(key)).getName().toLowerCase().indexOf(sheltername.toLowerCase()) != -1) {
            shelterNameCheck = true;
        }

        return genderCheck && ageRangeCheck && shelterNameCheck;
    }
}


