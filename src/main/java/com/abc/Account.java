package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


/*This class implements an account for the bank application.
An account can only be created from the subclasses of the class com.abc.Account.
Each account that is created corresponds to a customer meaning that an
account must always be linked to a customer upon creation, it can not
exist on it's own.

 */
public abstract class Account {

    private double amount;
    private UUID accountId;
    private Customer customer;
    private List<Transaction> transactions;
    private Date accountCreationDate;

    public enum InteType{DAILY, YEARLY}

    public Account(Customer customer, double amount, UUID accountId) {
        if(amount<0){
            throw new IllegalArgumentException("Can not create an account with negative balance");
        }
        this.customer = customer;
        this.amount = amount;
        this.accountId = accountId;
        this.transactions = new ArrayList<Transaction>();
        this.accountCreationDate = DateProvider.getInstance().now();
        customer.getAccounts().add(this);
    }

    //Introduced this setter for testing purposes
    //It should not be used in real life bank applications
    public void setAccountCreationDate(Date date){
        this.accountCreationDate= date;
    }
//Method which makes a deposit to an account
    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.amount += amount;
            //UUID id= UUID.randomUUID();
            transactions.add(new Transaction(UUID.randomUUID().toString(), Transaction.TranType.DEPOSIT, amount));
        }
    }

    public synchronized void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            if (this.amount >= amount) {
                this.amount -= amount;
                transactions.add(new Transaction(UUID.randomUUID().toString(), Transaction.TranType.WITHDRAW, -amount));
            }else{
                throw new IllegalArgumentException("Insufficient Balance" +"\n" + "Withdrawal unsuccessful, please try again");
            }
        }
    }
// The synchronized statement is redundant at the moment but it was used because
    //a present real life bank application would definitely be in a multithreading environment.
    //Tried to make the code reusable and
    public synchronized void interest(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
                this.amount += amount;
            transactions.add(new Transaction(UUID.randomUUID().toString(), Transaction.TranType.INTEREST, amount));
        }
    }
// transfers the daily interest earned to a customer's accounts.
    public void transferInterestEarnedDaily(){
                interest(interestEarned(InteType.DAILY));
    }
 //Method which returns the interest earned for a specific account based on the
    //type DAILY OR YEARLY
    public double interestEarned(InteType type){
        if(type.equals(InteType.DAILY)){
            return calculateInterestEarned(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR));
        }else{
            return calculateInterestEarned(1);
        }
    }

    //calculates the daily interest earned for an account
    public double interestCalculationDaily(){
        return interestEarned(InteType.DAILY);
    }
//calculates the yearly interest earned for an account
    public double interestCalculationYearly(){
        return  interestEarned(InteType.YEARLY);
    }
//calculates the interest by taking as input an int which corresponds to days or years in our case.
    public double calculateInterestEarned(int type){
        if (this instanceof Savings_Account) {
            ((Savings_Account) this).setInterestRate(0.001);
            if (amount <= 1000) {
                return amount * (new BigDecimal(((Savings_Account) this).getInterestRate() / type).setScale(10, RoundingMode.HALF_UP).doubleValue());
            }else {
                return 1 + (amount - 1000) * (new BigDecimal(0.002 / type).setScale(10, RoundingMode.HALF_UP).doubleValue());
            }
        }
        if (this instanceof Maxi_Savings_Account) {
                if (findDateDiffOfLastWithdraw() >= 10) {
                    return amount * (new BigDecimal( 0.05 / type).setScale(10,RoundingMode.HALF_UP).doubleValue());
                } else {
                    return amount * (new BigDecimal(0.01 / type).setScale(10,RoundingMode.HALF_UP).doubleValue());
                }
        } else {
            return amount * (new BigDecimal(0.001/type).setScale(10, RoundingMode.HALF_UP).doubleValue());
        }
    }

    @Override
    public String toString() {
        return "com.abc.Account{" +
                "amount=" + amount +
                ", accountId=" + accountId +
                ", customer=" + customer +
                ", transactions=" + transactions +
                ", accountCreationDate=" + accountCreationDate +
                '}';
    }

    //No need to sum the transactions of an account to get the total amount, it is an inefficient way to calculate the amount of an account
    //and unnecessary.
    //Introduced a new variable amount for each account and every time a transaction is made,
    //the amount of the account is updated respectively.
    // The method is never used but I left it here to compare the old version with my version and to show that
    // a stream could be used to do the exact same operation.
    public double sumTransactions() {
       return this.transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    //This method checks if a specific transaction exists in the transactions list
    //This method is never used and it can be removed because you can simply
    //check if the a transaction exist in the list transactions by calling
    //the contains method
    private boolean checkIfTransactionsExist(Transaction transaction) {
        if(this.transactions.isEmpty()){
            throw new IllegalArgumentException("The list of transactions is empty");
        }
        return this.transactions.contains(transaction);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    //This method calculates the difference in days of the date of the last withdrawal and the current date.
    //If no withdrawal have been made on this account or no transaction at all, the method returns the difference in days of the
    //current date and the account's creation date.
    public long findDateDiffOfLastWithdraw(){
        try {
            Date maxDate = this.transactions.stream().filter(transaction -> transaction.getTransactionType().equals(Transaction.TranType.WITHDRAW)).map(transaction -> transaction.getTransactionDate()).max(Date::compareTo).get();
                  Duration d = Duration.between(maxDate.toInstant(), Instant.now());
                  return d.toDaysPart();
        }catch(Exception e){
            Duration duration= Duration.between(this.accountCreationDate.toInstant(),Instant.now());
            return duration.toDaysPart();
        }
    }
    }

