package com.abc.account;

import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account {

    protected double getRate(double amount) {
        if (amount <= 1000) {
            return 0.001;
        }
        double interest = 1 + ( (amount - 1000) / 0.002);
        return amount / interest;
    }

    public String accountStatement() {
        String stmt = "Savings Account\n";
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
        return Account.AccountType.SAVINGS;
    }
}
