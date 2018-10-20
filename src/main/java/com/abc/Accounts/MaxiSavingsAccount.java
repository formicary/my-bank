package com.abc.Accounts;

import com.abc.Transaction;
import com.abc.util.DateProvider;
import com.abc.util.Money;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class MaxiSavingsAccount extends Account {

    /**
     * stored the last time a customer has withdrawn money in order to incur a penalty interest rate
     */
    private Instant lastWithdraw = null;

    public MaxiSavingsAccount() {
        super(Account.MAXI_SAVINGS);
    }


    // yearly potential non-compounded interest
//    public Money interestEarned() {
//        Money amount = sumTransactions();
//
//        if (amount.compareTo(new Money("1000")) <= 0) // if amount is less than or equal to 1000
//            return amount.multiply(new Money("0.02")); // apply 2% interest
//
//        else if (amount.compareTo(new Money("2000")) <= 0) { // if amount is less than or equal to 2000
//            Money twenty = new Money("20"); // 1 from 1000*0.02 (interest on the first 1000)
//
//            // difference in amount that exceeds 1000 but less than 2000
//            Money amountOver1000 = amount.subtract(new Money("1000"));
//
//            // apply 0.2% interest on the value over 1000
//            Money interestOver1000 = amountOver1000.multiply(new Money("0.05"));
//
//            return twenty.add(interestOver1000);
//
//        } else{ // else go into the over 2000 bracket
//            Money interestFromFirst2000 = new Money("70"); // 1 from 1000*0.02 (interest on the first 1000)
//
//            // amount that exceeds 2000
//            Money amountOver2000 = amount.subtract(new Money("2000"));
//
//            // apply 10% interest on the value over 2000
//            Money interestOver2000 = amountOver2000.multiply(new Money("0.1"));
//
//            return interestFromFirst2000.add(interestOver2000);
//        }
//
//    }

    @Override
    public void withdraw(Money amount, int transactionType) {
        super.withdraw(amount, transactionType);

        // if a customer has withdrawn money
        if (amount.compareTo(Money.ZERO) < 0 && transactionType == Transaction.CUSTOMER){
            // set last withdraw to time of last transaction
            lastWithdraw = transactions.get(transactions.size() - 1).getTransactionDate();
        }
    }

    public Money dailyInterestEarned() {

        Instant now = Instant.now();

        // check difference in days between now and last withdraw
        Duration duration = null;
        if (lastWithdraw != null) { // check if customer has never withdrawn
             duration = Duration.between(now, lastWithdraw);
        }

        Money interestRate = null;

        if (duration != null && duration.toDays() < 10){ // if still in the penalty period of withdrawn funds within the last 10 days
            interestRate = new Money("0.001"); // interest rate is 0.1%
        }else { // else interest rate is normal 5%
            interestRate = new Money("0.05");
        }

        return dailyInterestAtRate(sumTransactions(), interestRate);
    }
}
