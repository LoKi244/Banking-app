package com.example.loginpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FourColumn_ListAdapter extends ArrayAdapter<User> {

    private LayoutInflater minflater;
    private ArrayList<User> users;
    private int mViewResourceId;

    public FourColumn_ListAdapter(Context context,int textViewResourceId,ArrayList<User> users){
        super(context,textViewResourceId,users);
        this.users = users;
        minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = minflater.inflate(mViewResourceId,null);

        User user = users.get(position);

        TextView Date = (TextView)convertView.findViewById(R.id.textDate);
        TextView Deposit = (TextView)convertView.findViewById(R.id.textDeposit);
        TextView Withdraw = (TextView)convertView.findViewById(R.id.textWithdraw);
        TextView Balance = (TextView)convertView.findViewById(R.id.textBalance);

        Date.setText("Date");
        Deposit.setText("Deposit");
        Withdraw.setText("Withdraw");
        Balance.setText("Balance");

        if(user != null){
            Date.setText((user.getDate()));
            Deposit.setText((user.getDeposit()));
            Withdraw.setText((user.getWithdraw()));
            Balance.setText((user.getBalance()));
        }
        return convertView;
    }
}
