package com.abc.Accounts;

import com.abc.util.Money;

public class SavingsAccount extends Account{

    public SavingsAccount(){
        super(Account.SAVINGS);
    }

    public Money interestEarned() {
        Money amount = sumTransactions();

        if (amount.compareTo(new Money("1000")) <= 0) // if amount is less than or equal to 1000
            return amount.multiply(new Money("0.001"));
        else{
            Money one = new Money("1"); // 1 from 1000*0.1 (interest on the first 1000

            // difference in amount that exeeds 1000
            Money amountOver1000 = amount.subtract(new Money("1000"));

            // apply 0.2% interest on the value over 1000
            Money interestOver1000 = amountOver1000.multiply(new Money("0.002"));

            return one.add(interestOver1000);
        }
    }
}
