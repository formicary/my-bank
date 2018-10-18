package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public ArrayList<Transaction> transactions;
    public  DateProvider dateProvider;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        dateProvider = new DateProvider();
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount, dateProvider));
        }
    }

    public void withdraw(BigDecimal amount) {
        if(amount.compareTo(sumTransactions())<=0){
            if(amount.compareTo(BigDecimal.ZERO) <= 0){
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            else{
                transactions.add(new Transaction(amount.negate(), dateProvider));
            }
        }
        else{
            System.out.println("Not enough funds.");
        }
    }

    // Gets the total compound interest rate for the account.
    public BigDecimal interestEarned() {
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        BigDecimal totalInterest = BigDecimal.valueOf(0);
        BigDecimal dailyInterest = BigDecimal.valueOf(0);

        int count = -1;
        List<BigDecimal> dailySums = sumDailyTransactions();
        for (BigDecimal amount : dailySums) {
            System.out.println(dailySums.size());
            System.out.println( amount);

            totalAmount = amount.add(totalAmount);
            System.out.println("Total" + totalAmount);
            count++;
            switch (accountType) {
                case SAVINGS:
                    if (totalAmount.compareTo(BigDecimal.valueOf(1000)) <= 0) {
                        dailyInterest = totalAmount.multiply(BigDecimal.valueOf(0.001));
                        break;
                    }
                    else
                        dailyInterest = BigDecimal.valueOf(1).add((totalAmount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.002)));
                        break;
                case MAXI_SAVINGS:
                    if (!check10DayWithdrawal(checkLastDailyTransaction().get(count), dateProvider)) {
                        dailyInterest = totalAmount.multiply(BigDecimal.valueOf(0.05));
                        break;
                    }
                case CHECKING:
                    dailyInterest = totalAmount.multiply(BigDecimal.valueOf(0.001));
                    break;

            }
        totalAmount = totalAmount.add(dailyInterest);
        totalInterest = totalInterest.add(dailyInterest);

        }
        return CurrencyManager.roundBigDecimal(totalInterest);
    }

    // Returns a a list with the indexes of each of the last daily transactions for the account.
    public List<Integer> checkLastDailyTransaction() {
        ListIterator<Transaction> it = transactions.listIterator();
        List<Integer> lastDailyTransactions = new ArrayList<Integer>();
        Transaction current;
        Transaction next;

        int count = -1;
        while(it.hasNext()) {
            count++;
            current = it.next();
            if (it.hasNext()) {
                next = it.next();
                it.previous();

                if (!DateProvider.isSameDate(next.getDate(), current.getDate())) {
                    lastDailyTransactions.add(count);
                }
            }
            else
                lastDailyTransactions.add(count);
        }
        return lastDailyTransactions;
    }

    // Checks whether there has been a withdrawal within 10 days from the account. The index provided must be the index
    // of the last daily transaction for any given day.
    public boolean check10DayWithdrawal(int index, DateProvider date){
        index+=1;
        ListIterator li = transactions.listIterator(index);
        while(li.hasPrevious()) {
            Transaction t = (Transaction)li.previous();
            if (date.isOlderThan(t.getDate(), 10)){
                return false;
            }
            if (t.getType()==Transaction.WITHDRAWAL){
                return true;
            }
        }
        System.out.println("No withdrawals");
        return false;
    }

    // Returns a list with each element representing the sum total of the transactions on each day where a transaction
    // was made.
    public List<BigDecimal> sumDailyTransactions() {
        ListIterator<Transaction> it = transactions.listIterator();
        List<BigDecimal> dailysums = new ArrayList<BigDecimal>();
        Transaction current;
        Transaction next;

        int count = 0;
        dailysums.add(BigDecimal.ZERO);
        BigDecimal amount = BigDecimal.valueOf(0);
        while(it.hasNext()) {
            current = it.next();
            if (it.hasNext()){
                next = it.next();
                it.previous();

                // If the next date is the same as current, add to current element.
                if (DateProvider.isSameDate(next.getDate(), current.getDate())) {
                    amount = amount.add(current.getAmount());
                    dailysums.set(count, amount);
                }
                // Else add to current element and then create a new element for the next date, increasing the count.
                else{
                    amount = amount.add(current.getAmount());
                    dailysums.set(count, amount);
                    dailysums.add(amount);
                    count++;
                    amount = BigDecimal.valueOf(0);
                }
            }

            else {
                amount = current.getAmount().add(amount);
                dailysums.set(count, amount);
            }

        }
        return dailysums;
    }

    public BigDecimal sumTransactions() {
       return CurrencyManager.roundBigDecimal(getTransactionsSum());
    }

    public int getNumberOfTransactions() {
        return transactions.size();
    }

    private BigDecimal getTransactionsSum() {
        BigDecimal amount = BigDecimal.valueOf(0);
        for (Transaction t: transactions)
            amount = t.getAmount().add(amount);
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
