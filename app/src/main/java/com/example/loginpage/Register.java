package com.example.loginpage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText name,mobile,password1,password2,username,mail,uotp;
    Button regbtn;
    static String user;
    String ruser,rmail;
    Boolean fresult = false;
    detailsdb ob;
    transaction tob;
    String usermail= "lokesh123global@gmail.com";
    String password = "sd14bs3207";
    int otp;

    GMailSender sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ob = new detailsdb(this);
        name = (EditText)findViewById(R.id.regname);
        mobile = (EditText)findViewById(R.id.regmob);
        password1 = (EditText)findViewById(R.id.regpass);
        password2 = (EditText)findViewById(R.id.regrepass);
        username = (EditText)findViewById(R.id.reguser);
        mail = (EditText)findViewById(R.id.regmail);
        regbtn = (Button)findViewById(R.id.regbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname = name.getText().toString();
                String rmob = mobile.getText().toString();
                ruser = username.getText().toString();
                String rpass1 = password1.getText().toString();
                String rpass2 = password2.getText().toString();
                rmail = mail.getText().toString();

                sender = new GMailSender(usermail, password);

                Intent i = new Intent(Register.this, MainActivity.class);

                if (rname.equals("") || rmob.equals("") || rpass1.equals("") || rpass2.equals("") || ruser.equals("") || rmail.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
                } else {
                    if (rpass1.equals(rpass2)) {
                        boolean ver = ob.verify_register(ruser);
                        if(ver) {
                            Toast.makeText(getApplicationContext(), "User Already Exists. Please Login", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                        else{
                            otp = getotp();

                            new MyAsyncClass().execute();

                            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this,R.style.Theme_Loginpage);
                            final View customLayout = getLayoutInflater().inflate(R.layout.custom_otplayout,null);
                            builder.setView(customLayout);
                            builder.setTitle("OTP has been sent to your Email");
                            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    uotp = customLayout.findViewById(R.id.et_otp);
                                    String user_otp = uotp.getText().toString();
                                    int u_otp = Integer.parseInt(user_otp);
                                    if(otp == u_otp){
                                        boolean result = ob.insertdata(rname, ruser, rmob, rpass1, rmail);
                                        fresult = result;
                                        if (result) {
                                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                            user = ruser;
                                            createtable(fresult);
                                            startActivity(i);
                                        } else
                                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(Register.this,"OTP is Incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            builder.setNegativeButton("Cancel",((dialog, which) -> {
                                Toast.makeText(Register.this,"Cancel", Toast.LENGTH_SHORT);
                            }));

                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }else
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void createtable(Boolean fresult){
        if(fresult){
            tob = new transaction(this);
            try{
                tob.createuser();
                tob.inserttransaction("","","",user);
            }catch(Exception e){
                tob.inserttransaction("","","",user);
            }
        }
    }

    public static int getotp(){                                                                   //STATIC
        int randompin = (int) (Math.random()*9000)+1000;
        String otps = String.valueOf(randompin);
        int otp = Integer.parseInt(otps);
        return otp;
    }

    static String newuser(){
        return user;
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail("OTP", "Your OTP is "+otp, usermail, rmail);
                Log.d("send", "done");
            }
            catch (Exception ex) {
                Log.d("exceptionsending", ex.toString());
            }
            return null;
        }

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            pDialog.cancel();

        }
    }
}


