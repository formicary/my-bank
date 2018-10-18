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
            Money one = new Money("1"); // 1 from 1000*0.001 (interest on the first 1000)

            // difference in amount that exceeds 1000
            Money amountOver1000 = amount.subtract(new Money("1000"));

            // apply 0.2% interest on the value over 1000
            Money interestOver1000 = amountOver1000.multiply(new Money("0.002"));

            return one.add(interestOver1000);
        }
    }

    public Money dailyInterestEarned() {
        Money amount = sumTransactions();
        Money interestEarned = Money.ZERO;

        // NOTE: interest rate is calculated in descending order (starting with interest rate of $1000+ then interest rate of $0-1000)

        // interest bracket of 1000+
        Money bracket1Amount = new Money("1000");
        if(amount.compareTo(bracket1Amount) > 0){ // if amount is greater than 1000
            Money interestRate2 = new Money("0.002"); // 0.2% interest
            Money amountOver1000 = amount.subtract(bracket1Amount); // amount of funds that the second interest will be applied to

            interestEarned = interestEarned.add(dailyInterestAtRate(amountOver1000, interestRate2)); // adds interest earned in this bracket to the total interest rate earned

            // remove the amount that interest was applied to from total amount left
            amount = amount.subtract(amountOver1000);
        }

        // interest bracket of 0-1000
        Money interestRate1 = new Money("0.002"); // 0.1% interest

        interestEarned = interestEarned.add(dailyInterestAtRate(amount, interestRate1)); // adds interest earned in this bracket to the total interest rate earned

        return interestEarned;
    }
}
