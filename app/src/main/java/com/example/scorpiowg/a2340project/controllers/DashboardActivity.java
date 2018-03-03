package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;

/**
 * Created by wangjingbo on 3/3/18.
 */

public class DashboardActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        TextView user = findViewById(R.id.userinfo);
        user.setText(Model.getInstance().getDatabase().get(getIntent().getStringExtra("userId")).toString());

        Button shelter = findViewById(R.id.search);
        final Intent shelterPage = new Intent(this, ShelterListActivity.class);
        shelter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shelterPage.putExtra("userId", getIntent().getStringExtra("userId").toString());
                shelterPage.putExtra("filter", "0");
                startActivity(shelterPage);
                Log.d("debug", "to shelter list");
            }
        });

    }
}
