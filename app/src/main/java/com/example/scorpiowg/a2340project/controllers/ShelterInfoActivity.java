package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;


/**
 * Created by wangjingbo on 2/25/18.
 */

public class ShelterInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);

        if (getIntent().getStringExtra("claim") != null) {
            Log.d("debug", "successfully detect claim");
            if (Model.getInstance().getUser().getClaim() != null) {
                TextView error = findViewById(R.id.error);
                error.setVisibility(View.VISIBLE);
            }
            if (Model.getInstance().getUser().getClaim() == null) {
                Log.d("debug", "successful claim");
                Model.getInstance().getUser().setClaim(Model.getInstance().getShelter());
                //Model.getInstance().getUser().setBeds(Integer.parseInt(getIntent().getStringExtra("claim").toString()));
                TextView success = findViewById(R.id.success);
                success.setVisibility(View.VISIBLE);
            }
        }

        if (getIntent().getStringExtra("shelterId") != null) {
            String id = getIntent().getStringExtra("shelterId");
            TextView info = findViewById(R.id.info);
            Model.getInstance().setCurrentShelter((Shelter)Model.getInstance().getShelters().get(id));
            info.setText(Model.getInstance().getShelter().toString());
        } else {
            TextView info = findViewById(R.id.info);
            info.setText(Model.getInstance().getShelter().toString());
        }

        final Intent claimBedspace = new Intent(this, ShelterInfoActivity.class);
        Button claim = findViewById(R.id.claim);
        TextView beds = findViewById(R.id.beds);
        final String bedNum = beds.getText().toString();
        claim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("debug", "claim");
                claimBedspace.putExtra("claim", bedNum);
                startActivity(claimBedspace);
            }
        });
    }
}
