package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    detailsdb ob;
    Homepage hp;
    EditText username,password;
    TextView forgotpswd;
    Button btnlgn,btnreg;
    String mobile,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ob = new detailsdb(this);
        username = (EditText)findViewById(R.id.loginuser);
        password = (EditText)findViewById(R.id.loginPassword);
        btnlgn = (Button)findViewById(R.id.loginbutton);
        btnreg = (Button)findViewById(R.id.registerbutton);
        forgotpswd = (TextView)findViewById(R.id.fgpassword);

        btnlgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Boolean val = validate(user,pass);
                if(val){
                    Boolean result = ob.verify(user,pass);
                    if(result) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this,Homepage.class);
                        i.putExtra("Username",user);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });

        forgotpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Forgot_pass.class);
                startActivity(i);
            }
        });
    }

    public boolean validate(String user,String pass){
        if(user.equals("") || pass.equals("")){
            Toast.makeText(getApplicationContext(),"Please enter all the credentials",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }
}