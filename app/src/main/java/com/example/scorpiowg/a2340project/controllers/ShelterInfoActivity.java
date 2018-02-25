package com.example.scorpiowg.a2340project.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;


/**
 * Created by wangjingbo on 2/25/18.
 */

public class ShelterInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);

        String id = getIntent().getStringExtra("shelterId");
        TextView info = findViewById(R.id.info);

        info.setText(Model.getInstance().getShelters().get(id).toString());
    }
}
