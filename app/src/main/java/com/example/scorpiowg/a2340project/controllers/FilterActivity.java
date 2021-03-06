package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.scorpiowg.a2340project.R;

/**
 * Created by wangjingbo on 3/3/18.
 */


public class FilterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

//        final String genderFilter = "";
//        final String ageRangeFilter = "";
//        final String shelterNameFilter = "";

        final Spinner genderSpinner = findViewById(R.id.gender);
        String[] genderchoice = new String[]{"Any", "Men", "Women"};
        SpinnerAdapter genderadapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genderchoice);
        genderSpinner.setAdapter(genderadapter);

        final Spinner ageRangeSpinner = findViewById(R.id.ageRange);
        String[] ageRangechoice =
                new String[]{"Anyone", "Families with newborns", "Children", "Young Adults"};
        SpinnerAdapter ageRangeadapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, ageRangechoice);
        ageRangeSpinner.setAdapter(ageRangeadapter);



        Button search = findViewById(R.id.search);
        final Intent shelterPage = new Intent(this, ShelterListActivity.class);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //noinspection ChainedMethodCall,ChainedMethodCall
                shelterPage.putExtra("userId", getIntent().getStringExtra("userId"));
                //noinspection ChainedMethodCall
                shelterPage.putExtra("gender", genderSpinner.getSelectedItem().toString());
                //noinspection ChainedMethodCall
                shelterPage.putExtra("ageRange", ageRangeSpinner.getSelectedItem().toString());
                EditText nameInput = findViewById(R.id.name);
                String name = nameInput.getText().toString();
                shelterPage.putExtra("shelterName", name);
                shelterPage.putExtra("filter", "1");
                startActivity(shelterPage);
            }
        });



    }
}
