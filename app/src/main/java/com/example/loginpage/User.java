package com.example.loginpage;

public class User {
    private String Date;
    private String Deposit;
    private String Withdraw;
    private String Balance;

    public User(String date,String depo,String with,String bal){
        Date = date;
        Deposit = depo;
        Withdraw = with;
        Balance= bal;
    }

    public String getDate(){
        return Date;
    }

    public String getDeposit(){
        return Deposit;
    }

    public String getWithdraw(){
        return Withdraw;
    }

    public String getBalance(){
        return Balance;
    }
}
