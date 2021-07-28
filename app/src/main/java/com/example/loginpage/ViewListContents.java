package com.example.loginpage;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class     ViewListContents extends AppCompatActivity {

    transaction tob;
    ArrayList<User> userList;
    ListView listView;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        String username = Homepage.getuser();
        tob = new transaction(this);

        userList = new ArrayList<>();

        Cursor data = tob.getdata(username);
        int numrows = data.getCount();
        if(numrows == 0)
            Toast.makeText(ViewListContents.this, "No transactions", Toast.LENGTH_SHORT).show();
        else{
            user = new User("Date","Deposit","Withdraw","Balance");
            userList.add(user);
            while(data.moveToNext()){
                user = new User(data.getString(1),data.getString(2),data.getString(3),data.getString(4));
                userList.add(user);
            }
            FourColumn_ListAdapter adapter = new FourColumn_ListAdapter(this,R.layout.list_adapter_view,userList);
            listView = (ListView)findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }
}
