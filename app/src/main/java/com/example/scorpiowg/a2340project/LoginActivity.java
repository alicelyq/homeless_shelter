package com.example.scorpiowg.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by wangjingbo on 2/11/18.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (getIntent().getStringExtra("error") != null) {
            TextView error = findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
        }

        Button cancellogin = findViewById(R.id.cancellogin);
        Button submitlogin = findViewById(R.id.submitlogin);
        final EditText usernameinput = findViewById(R.id.usernameinput);
        final EditText passwordinput = findViewById(R.id.passwordinput);

        final Intent mainPage = new Intent(this, MainActivity.class);
        final Intent homePage = new Intent(this, HomepageActivity.class);
        final Intent loginErrorPage = new Intent(this, LoginActivity.class);

        cancellogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mainPage);
            }
        });
        submitlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (usernameinput.getText().toString().equals("user") && passwordinput.getText().toString().equals("pass")) {
                        startActivity(homePage);
                    } else {
                        Bundle b = new Bundle();
                        b.putString("error", "true");
                        loginErrorPage.putExtras(b);
                        startActivity(loginErrorPage);
                    }
                }
        });
    }
}
