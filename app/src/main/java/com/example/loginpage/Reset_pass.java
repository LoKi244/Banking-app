package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Reset_pass extends AppCompatActivity {

    EditText pass1,pass2;
    Button reset;
    detailsdb ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        pass1 = (EditText)findViewById(R.id.reset_pass1);
        pass2 = (EditText)findViewById(R.id.reset_pass2);
        reset = (Button)findViewById(R.id.resetbtn);
        ob = new detailsdb(this);
        String user = getIntent().getStringExtra("Username");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rpass1 = pass1.getText().toString();
                String rpass2 = pass2.getText().toString();

                if(rpass1.equals(rpass2)){
                    boolean result = ob.update(user,rpass1);
                    if(result){
                        Toast.makeText(getApplicationContext(),"Password reset successful",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Reset_pass.this,MainActivity.class);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();

            }
        });
    }
}