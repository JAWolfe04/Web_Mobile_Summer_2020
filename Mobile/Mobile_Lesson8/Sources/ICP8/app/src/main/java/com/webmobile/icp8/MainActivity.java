package com.webmobile.icp8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean logged_in = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText username_view = (EditText)findViewById(R.id.editTextUsername);
        EditText password_view = (EditText)findViewById(R.id.editTextPassword);
        String username = username_view.getText().toString();
        String password = password_view.getText().toString();

        if(username.isEmpty()) {
            Toast empty_name = Toast.makeText(getApplicationContext(),
                    "Username Required", Toast.LENGTH_SHORT);
            empty_name.show();
        } else if(password.isEmpty()) {
            Toast empty_password = Toast.makeText(getApplicationContext(),
                    "Password Required", Toast.LENGTH_SHORT);
            empty_password.show();
        } else if(username.equals("admin") && password.equals("password")){
            startActivity(new Intent(this, Home.class));
        } else {
            Toast incorrect_login = Toast.makeText(getApplicationContext(),
                    "Invalid Username/Password", Toast.LENGTH_SHORT);
            incorrect_login.show();
        }
    }
}