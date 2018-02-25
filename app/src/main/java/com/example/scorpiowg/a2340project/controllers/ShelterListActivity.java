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
        TextView user = findViewById(R.id.user);
        user.setText(Model.getInstance().getDatabase().get(getIntent().getStringExtra("userId")).toString());

        // find parent frame
        LinearLayout parentLayout = findViewById(R.id.parentLayout);
        parentLayout.setGravity(Gravity.CENTER);
//        Log.d("debug","inShelter");
//        HashMap s = Model.getInstance().getShelters();
//        Log.d("debug", Model.getInstance().toString());
//        Log.d("debug", Integer.toString(s.size()));

        final Intent shelterInfo = new Intent(this, ShelterInfoActivity.class);

        //iterate over model hashmap, and put data into view as button
        for (Object key : Model.getInstance().getShelters().keySet()) {
            final String id = (String) key;
            Log.d("debug", (String) key);
            final Button shelter = new Button(this);
            LinearLayout.LayoutParams shelterListParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            shelterListParams.setMargins(0, 20, 0, 0);
            shelter.setLayoutParams(shelterListParams);
            shelter.setText(((Shelter)Model.getInstance().getShelters().get(key)).getName());
            int idSetter = Integer.parseInt((String)key);
            shelter.setId(idSetter);
//            buttons.add(shelter);
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
