package com.asifhashmi.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    boolean loginModeActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redirect();
    }

    public void signupOrLogin(View view) {


        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        if (loginModeActive) {

            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        String message = e.getMessage();
                        if (message.toLowerCase().contains("java")) {
                            Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                            redirect();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else {

            ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Signed Up", Toast.LENGTH_SHORT).show();
                    } else {
                        String message = e.getMessage();
                        if (message.toLowerCase().contains("java")) {
                            Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                            redirect();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }

    public void modeSwitch(View view) {
        Button switchB = (Button) findViewById(R.id.loginOrSignupB);
        TextView switchT = (TextView) findViewById(R.id.loginOrSignupT);

        if (loginModeActive) {
            loginModeActive = false;
            switchB.setText("Sign Up");
            switchT.setText("Log In");
        } else {
            loginModeActive = true;
            switchB.setText("Log In");
            switchT.setText("Sign Up");
        }
    }


    public void redirect() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), userlist.class);
            startActivity(intent);
        }
    }
}
