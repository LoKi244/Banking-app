package com.example.loginpage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Homepage extends AppCompatActivity {
    Button depo,with,logout,chkbalance,previous_tran;
    TextView text,balance;
    EditText tdeposit,twithdraw;
    String username,deposit,withdraw;
    static String uname;
    transaction tob = new transaction(this);
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy\nHH:mm");
    Date obj = new Date();
    String date = df.format(obj);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        depo = (Button)findViewById(R.id.deposit);
        with = (Button)findViewById(R.id.withdraw);
        chkbalance = (Button)findViewById(R.id.checkbalance);
        previous_tran = (Button)findViewById(R.id.prev_transaction);
        logout = (Button)findViewById(R.id.logoutbtn);
        text = (TextView)findViewById(R.id.vtext);
        balance = (TextView)findViewById(R.id.viewbalance);
        username = getIntent().getStringExtra("Username");
        uname = username;

        text.setText("Welcome "+username);

        depo.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this,R.style.Theme_Loginpage);
            final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout,null);
            builder.setView(customLayout);
            builder.setTitle("Deposit");
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   tdeposit = customLayout.findViewById(R.id.et_text);
                   deposit = tdeposit.getText().toString();
                   boolean res = tob.inserttransaction(date,deposit,"",username);
                   if(deposit.equals(""))
                       res = false;
                   if(res){
                       Toast.makeText(Homepage.this, "Rs."+deposit+" deposited", Toast.LENGTH_SHORT).show();
                   }
                }
            });

            builder.setNegativeButton("Cancel",((dialog, which) -> {
                Toast.makeText(Homepage.this,"Cancel", Toast.LENGTH_SHORT);
            }));

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        with.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this,R.style.Theme_Loginpage);
            final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout,null);
            builder.setView(customLayout);
            builder.setTitle("Withdraw");
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    twithdraw = customLayout.findViewById(R.id.et_text);
                    withdraw = twithdraw.getText().toString();
                    boolean res = tob.inserttransaction(date,"",withdraw,username);
                    if(withdraw.equals(""))
                        res = false;
                    if(res){
                        Toast.makeText(Homepage.this, "Rs."+deposit+" withdrawn", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(Homepage.this,"Invalid Amount", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel",((dialog, which) -> {
                Toast.makeText(Homepage.this,"Cancel", Toast.LENGTH_SHORT);
            }));

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        chkbalance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int bal = tob.checkbalance(username);
                balance.setText("Your Balance is :"+bal);
            }
        });

        previous_tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this,ViewListContents.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    static String getuser(){
        return uname;
    }
}