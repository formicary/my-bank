package main.java.com.abc.Accounts;

import java.util.TimerTask;

class GiveInterestTask extends TimerTask {

    private final AccountBase account;


    GiveInterestTask ( AccountBase accountBase )
    {
    	this.account = accountBase;
    }

    public void run() {
    	this.account.GiveInterest();
    }
}