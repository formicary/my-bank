package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public static final Map<Integer, String> accountTypes = new HashMap<>();

    // Interest price bands
    private final BigDecimal lowestBand = new BigDecimal(1000);
    private final BigDecimal mediumBand = new BigDecimal(2000);
    private final BigDecimal highestBand = new BigDecimal(3000);

    // Savings Account Price Bands - multipliers and adders
    private final BigDecimal lowestBandMultiplier = new BigDecimal(0.001);
    private final BigDecimal mediumBandMultiplier = new BigDecimal(0.002);
    private final BigDecimal highBandMultiplier = new BigDecimal(0.005);
    private final BigDecimal highestBandMultiplier = new BigDecimal(0.010);

    private final BigDecimal lowestBandAdder = new BigDecimal(1);
    private final BigDecimal mediumBandAdder = new BigDecimal(2);
    private final BigDecimal highestBandAdder = new BigDecimal(5);

    // Maxi Savings Account - multipliers and adders
    private final BigDecimal maxiLowestBandMultiplier = new BigDecimal(0.02);
    private final BigDecimal maxiMediumBandMultiplier = new BigDecimal(0.05);
    private final BigDecimal maxiHighestBandMultiplier = new BigDecimal(0.10);

    private final BigDecimal maxiLowestBandAdder = new BigDecimal(20);
    private final BigDecimal maxiMediumBandAdder = new BigDecimal(50);
    private final BigDecimal maxiHighestBandAdder = new BigDecimal(100);

    private final int accountType;
    public List<Transaction> transactions;
    private BigDecimal accountBalance;
    private Conversion numberToConvert;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountBalance = BigDecimal.valueOf(0);
        accountTypes.put( 0, "Checking Account");
        accountTypes.put(1, "Savings Account");
        accountTypes.put(2, "Maxi-Savings Account");
    }



    public String accountTypeNumberToName(int accountType){
        return accountTypes.get(accountType);
    }

    public String getAccountSummary(){
        String s = accountTypeNumberToName(this.getAccountType());
        for (Transaction t : transactions) {
            s+= ("\n") + t.toString();
        }
        s += "\nTotal " + Conversion.toDollars(this.getAccountBalance());
        return s;
    }

    public String statementForAccount(Account a) {
        String s = "";

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        BigDecimal total = new BigDecimal(0.0);
        for (Transaction t : a.transactions) {
            s += "  " + (t.transactionType) + " " + Conversion.toDollars(t.amount) + "\n";
            total = a.accountBalance;
        }
        s += "Total " + Conversion.toDollars(total);
        return s;
    }



    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("amount deposited must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, "deposit"));
            accountBalance = accountBalance.add(amount);
            System.out.println(accountBalance);
        }
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("amount withdrawn must be greater than zero");
        }
        if (amount.compareTo(accountBalance) == 1) {
            System.out.println(accountBalance);
            throw new IllegalArgumentException("There are insufficient funds in this account. Please try withdrawing less.");
        }
        else {
            transactions.add(new Transaction(amount, "withdrawal"));
            accountBalance = accountBalance.subtract(amount);
        }
    }

    public BigDecimal getAccountBalance() {
        return this.accountBalance;
    }


    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount.compareTo(lowestBand) <= 0)
                    return amount.multiply(lowestBandMultiplier);
                else
                    return lowestBand.multiply(lowestBandMultiplier).add((amount.subtract(lowestBand).multiply(mediumBandMultiplier)));
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount.compareTo(lowestBand) <= 0)
                    return amount.multiply(maxiLowestBandMultiplier);
                else if (amount.compareTo(mediumBand) <= 0)
                    // +20 changed to +2
                    return (amount.subtract(lowestBand)).multiply(maxiMediumBandMultiplier).add(maxiMediumBandAdder);
                else if (amount.compareTo(highestBand) >= 0)
                    return (amount.subtract(lowestBand).subtract(lowestBand)).multiply(maxiHighestBandMultiplier).add(maxiLowestBandAdder).add(maxiMediumBandAdder);

            default:
                return amount.multiply(lowestBandMultiplier);
        }
    }

    public BigDecimal sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = new BigDecimal(0.0);
        for (Transaction t : transactions)
            amount = amount.add(t.amount);
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

//    public void transferToAccount(Account targetAccount, BigDecimal amount){
//        String outgoingTransfer = "Outgoing transfer to " + accountTypeNumberToName(targetAccount.getAccountType());
//
//        transactions.add(new Transaction(amount, outgoingTransfer));
//
//        transferReceivedByAccount(targetAccount, amount);
//
//
//        sourceAccount.accountBalance = sourceAccount.accountBalance.subtract(amount);
//
//
//    }


    public void transferReceivedByAccount(Account targetAccount, BigDecimal amount){

        String incomingTransfer = "Incomming transfer from " + accountTypeNumberToName(targetAccount.getAccountType());
        transactions.add(new Transaction(amount, incomingTransfer));

        targetAccount.accountBalance = targetAccount.accountBalance.add(amount);
    }

}
