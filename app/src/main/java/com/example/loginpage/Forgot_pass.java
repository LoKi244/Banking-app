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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot_pass extends AppCompatActivity {

    EditText user_name,E_mail,uotp;
    Button confirm;
    detailsdb ob;
    GMailSender sender;
    int otp;
    String mail;
    String usermail= "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        user_name = (EditText)findViewById(R.id.forgot_user);
        E_mail = (EditText)findViewById(R.id.forgot_mail);
        confirm = (Button)findViewById(R.id.forgot_confirm);
        ob = new detailsdb(this);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = user_name.getText().toString();
                mail = E_mail.getText().toString();

                if(ob.verify_forgot(user,mail)){

                    otp = Register.getotp();
                    sender = new GMailSender(usermail, password);
                    new MyAsyncClass().execute();

                    AlertDialog.Builder builder = new AlertDialog.Builder(Forgot_pass.this,R.style.Theme_Loginpage);
                    final View customLayout = getLayoutInflater().inflate(R.layout.custom_otplayout,null);
                    builder.setView(customLayout);
                    builder.setTitle("OTP has been sent to your Email");
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uotp = customLayout.findViewById(R.id.et_otp);
                            String user_otp = uotp.getText().toString();
                            int u_otp = Integer.parseInt(user_otp);
                            if (otp == u_otp) {
                                Intent i = new Intent(Forgot_pass.this,Reset_pass.class);
                                i.putExtra("Username",user);
                                startActivity(i);
                            }
                        }
                        });

                    builder.setNegativeButton("Cancel",((dialog, which) -> {
                        Toast.makeText(Forgot_pass.this,"Cancel", Toast.LENGTH_SHORT);
                    }));

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Forgot_pass.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(Forgot_pass.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail("OTP", "Your OTP is "+otp, usermail, mail);
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
