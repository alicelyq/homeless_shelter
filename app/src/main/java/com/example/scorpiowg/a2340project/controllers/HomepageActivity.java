package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;

/**
 * Created by nancy on 2/11/18.
 */


public class HomepageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Model modelInstance = Model.getInstance();

        // user home page
        TextView type = findViewById(R.id.homepage);
        type.setText(modelInstance.getDatabase().get(getIntent()
                .getStringExtra("userId")).toString());

        Button logout = findViewById(R.id.logout);
        // next actions after button clicked
        final Intent back = new Intent(this, MainActivity.class);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(back);
            }
        });

    }
}
