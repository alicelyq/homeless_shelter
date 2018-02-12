package com.example.scorpiowg.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by wangjingbo on 2/11/18.
 */

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        if (getIntent().getStringExtra("error") != null) {
            TextView error = findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
        }

        Button cancelRegister = findViewById(R.id.cancel);
        Button register = findViewById(R.id.register);

        final EditText userinput = findViewById(R.id.username);
        final EditText passinput = findViewById(R.id.password);
        final EditText confinput = findViewById(R.id.confirm);

        final Intent mainPage = new Intent(this, MainActivity.class);
        final Intent loginPage = new Intent(this, LoginActivity.class);
        final Intent registerPage = new Intent(this, RegisterActivity.class);

        cancelRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mainPage);
            }
        });

         register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (passinput.getText().toString().equals(confinput.getText().toString())) {
                        startActivity(loginPage);
                    } else {
                        Bundle b = new Bundle();
                        b.putString("error", "true");
                        registerPage.putExtras(b);
                        startActivity(registerPage);
                    }
                }
            });
    }
}
