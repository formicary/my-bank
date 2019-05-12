/*Edited by: Foyaz Hasnath*/
package com.abc;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    private DateProvider dateProvider = new DateProvider();
    private Date dateOpened;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
        dateOpened = dateProvider.now();
        System.out.println("date account opened: "+dateProvider.dateFormat(dateOpened));
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
    }
}

public void transfer(Account to, double amount){
      // boolean success = false;
            try{
                withdraw(amount);
                to.deposit(amount);
                //success=true;
            }catch (Exception e){
                e.printStackTrace();
                //success = false;
            }
     // return success;
}

    public double interestEarned(Date businessDate) {
        double amount = sumTransactions();
        DecimalFormat df = new DecimalFormat("#.##");
        switch(accountType){
            case SAVINGS :
                double interest = 0;
                    if (amount <= 1000){
                        interest = Double.valueOf(df.format(dailyCompoundedFormula(businessDate,amount,0.001)));//0.1%
                    }else {
                        interest = Double.valueOf(df.format(dailyCompoundedFormula(businessDate,1000,0.001)));//0.1% for first 1000
                        interest+= Double.valueOf(df.format(dailyCompoundedFormula(businessDate,amount-1000,0.002)));//0.2% for rest
                    }
                return Double.valueOf(df.format(interest));
            case MAXI_SAVINGS:
                double totalInterest = 0;
                    if (amount <= 1000){
                        totalInterest = Double.valueOf(df.format(dailyCompoundedFormula(businessDate,amount,0.02)));//2%
                    }else if (amount>1000 && amount<=2000){
                        totalInterest = Double.valueOf(df.format(dailyCompoundedFormula(businessDate,1000,0.02)));//2% for first 1000
                        totalInterest+= Double.valueOf(df.format(dailyCompoundedFormula(businessDate,amount-1000,0.05)));//5% until 2000
                    }else{
                        System.out.println("more than 2k");
                        totalInterest = Double.valueOf(df.format(dailyCompoundedFormula(businessDate,1000,0.02)));//2% for first 1000
                        totalInterest+= Double.valueOf(df.format(dailyCompoundedFormula(businessDate,1000,0.05)));//5% until 2000
                        totalInterest+= Double.valueOf(df.format(dailyCompoundedFormula(businessDate,amount-2000,0.10)));//10% for rest
                    }
                return Double.valueOf(df.format(totalInterest));

            //Checking Account (0.1%)
            default:
                return (Double.valueOf(df.format(dailyCompoundedFormula(businessDate,amount,0.001))));
        }
    }

    //daily compounded interest formula
    private double dailyCompoundedFormula(Date businessDate, double amount,double rate){
        double x = 1 + (rate/365);

        //accurately get days between two dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1 = LocalDate.parse(dateProvider.dateFormat(businessDate), formatter);
        LocalDate date2 = LocalDate.parse(dateProvider.dateFormat(dateOpened), formatter);
        long days = ChronoUnit.DAYS.between(date2,date1);
        int daysPassed = (int) days;

        System.out.println("REAL business days passed from bank opening till simulated business date: "+daysPassed+" days");
        double multiplier = (Math.pow(x,daysPassed)) - 1;
        return amount * multiplier;
        //return Double.valueOf((amount + (amount*multiplier)));
    }

    /*
    //TODO: daily compounded interest formula for MAXI SAVINGS
    private double dailyCompoundedFormula(Date businessDate,ArrayList<Date> withdrawalDates, double amount){
        double balance = 0;

        double fivePercentRate = 1 + (0.05/365);
        double pointOnePercentRate = 1 + (0.001/365);
        Collections.sort(withdrawalDates);

        Date dateFrom = dateOpened;
        Date dateTo = businessDate;

            for (int i=0;i<withdrawalDates.size();i++){
                System.out.println("withdrawal date "+ i +" " + dateProvider.dateFormat(withdrawalDates.get(i)));
                //5% from account opening until first withdrawal
                long diff = dateTo.getTime() - dateFrom.getTime();
                int daysPassed = (int) (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                System.out.println(daysPassed);
                double multiplier = (Math.pow(fivePercentRate,daysPassed)) - 1;
                balance += Double.valueOf((amount + (amount*multiplier)));
            }
        return balance;
    }
    */

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
