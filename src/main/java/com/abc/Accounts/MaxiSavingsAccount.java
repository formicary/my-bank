package com.abc.Accounts;

import com.abc.util.Money;

public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount() {
        super(Account.MAXI_SAVINGS);
    }

    public Money interestEarned() {
        Money amount = sumTransactions();

        if (amount.compareTo(new Money("1000")) <= 0) // if amount is less than or equal to 1000
            return amount.multiply(new Money("0.02")); // apply 2% interest

        else if (amount.compareTo(new Money("2000")) <= 0) { // if amount is less than or equal to 2000
            Money twenty = new Money("20"); // 1 from 1000*0.02 (interest on the first 1000)

            // difference in amount that exceeds 1000 but less than 2000
            Money amountOver1000 = amount.subtract(new Money("1000"));

            // apply 0.2% interest on the value over 1000
            Money interestOver1000 = amountOver1000.multiply(new Money("0.05"));

            return twenty.add(interestOver1000);

        } else{ // else go into the over 2000 bracket
            Money interestFromFirst2000 = new Money("70"); // 1 from 1000*0.02 (interest on the first 1000)

            // amount that exceeds 2000
            Money amountOver2000 = amount.subtract(new Money("2000"));

            // apply 10% interest on the value over 2000
            Money interestOver2000 = amountOver2000.multiply(new Money("0.1"));

            return interestFromFirst2000.add(interestOver2000);
        }

    }
}
