package com.example.scorpiowg.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by wangjingbo on 2/11/18.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button cancellogin = findViewById(R.id.cancellogin);
        Button submitlogin = findViewById(R.id.submitlogin);
        final EditText usernameinput = findViewById(R.id.usernameinput);
        final EditText passwordinput = findViewById(R.id.passwordinput);

        final Intent mainPage = new Intent(this, MainActivity.class);
        final Intent homePage = new Intent(this, HomepageActivity.class);

        cancellogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mainPage);
            }
        });
        submitlogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(homePage);
                }
        });
    }
}
