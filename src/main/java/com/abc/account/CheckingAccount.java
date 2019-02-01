package com.abc.account;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends Account {

    protected double getRate(double balance) {
        return 0.001;
    }

    public String accountStatement() {
        String stmt = "Checking Account\n";
        //Display transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            stmt += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Transaction.toDollars(t.amount) + "\n";
            total += t.amount;
        }
        stmt += "Total " + Transaction.toDollars(total);

        return stmt;
    }

    public Account.AccountType getAccountType() {
        return Account.AccountType.CHECKING;
    }
}
