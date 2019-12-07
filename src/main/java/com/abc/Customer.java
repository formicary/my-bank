package com.abc;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    String customerName;
    int customerID;
    Account accounts[] = new Account[3];
    List<Transaction> transactions = new ArrayList();

    public Customer(String name) {
        this.customerName = name;

    }

    public void addAccount(int accountType) {
        if (accountType >= 0 && accountType <= 2) {
            if (accounts[accountType] == null) {
                accounts[accountType] = new Account(accountType);
                
            } else {
                System.out.println("This account has already been created");
            }
        } else {
            System.out.println("This is an invalid account type");
        }
    }

    public void deposit(double amount, int accountType) {
        if (checkExists(accountType)) {
            if (amount > 0) {
                accounts[accountType].accountValue += amount;
                Transaction t = new Transaction(accountType, amount, "Deposit");
                transactions.add(t);
            } else {
                System.out.println("Invalid deposit quantity");
            }
        } else {
            System.out.println("This account doesn't exist");
        }
    }

    public void withdraw(double amount, int accountType) {
        if (checkExists(accountType)) {
            if (amount > 0) {
                if (amount < accounts[accountType].accountValue) {
                    accounts[accountType].accountValue -= amount;
                    Transaction t = new Transaction(accountType, amount, "Withdrawal");
                    transactions.add(t);
                } else {
                    System.out.println("Insufficient funds");
                }
            } else {
                System.out.println("Invalid amount");
            }
        } else {
            System.out.println("Invalid account");
        }
    }

    public double getBalance(int accountType) {
        if (checkExists(accountType)) {
            
            return (accounts[accountType].accountValue);
        } else {
            System.out.println("Account " + accountType + " doesn't exist");
            return (-1);
        }
    }

    public boolean checkExists(int accountType) {
        if (accounts[accountType] == null) {

            return (false);
        } else {
            return (true);
        }
    }

    public void transfer(int account1, int account2, double amount) {
        if (checkExists(account1) && checkExists(account2)) {
            if (accounts[account1].accountValue >= amount) {
                withdraw(amount, account1);
                deposit(amount, account2);
                
            }
        }
    }

    public double getTotalBalance() {
        double total = 0;
        for (int i = 0; i < accounts.length; i++) {
            if (checkExists(i)) {
                total += getBalance(i);
            }
        }
        return(total);
    }

    public String getTransactions() {
        String s = "";
        for (int i = 0; i < transactions.size(); i++) {
            s = s + "\n" + transactions.get(i).type + " of " + transactions.get(i).amount + " concerning account type " + transactions.get(i).account;
        }
        return(s);
    }

    public String getStatement() {
        String s1 = getTransactions();
        double s2 = getTotalBalance();
        System.out.println(s1 + "\nTotal Balance: " + s2);
        return(s1 + "\nTotal Balance: " + s2);
    }

    /**
     *
     * @return
     */
    public int getNumberOfAccounts() {
        int numberOfAccounts = 0;
        for (int i = 0; i < accounts.length; i++) {
            if (checkExists(i)) {
                numberOfAccounts++;
            }
        }
        return (numberOfAccounts);
    }

    public double daysSinceLastWithdraw(Account account) {
        double daysBetween = ChronoUnit.DAYS.between(transactions.get(transactions.size() - 1).date, java.time.LocalDate.now());
        return (daysBetween);
    }

    public double getInterest() {
        double totalInterest = 0;
        for (int i = 0; i < 3; i++) {
            if (accounts[i] != null) {
                if (i == 0) {
                    accounts[i].accountValue += 0.001 / 365.25 * accounts[i].accountValue;
                    totalInterest += 0.001 / 365.25 * accounts[i].accountValue;
                }
                if (i == 1) {
                    if (accounts[i].accountValue <= 1000) {
                        accounts[i].accountValue += 0.001 / 365.25 * accounts[i].accountValue;
                        totalInterest += 0.001 / 365.25 * accounts[i].accountValue;
                    } else {
                        accounts[i].accountValue += (0.001 / 365.25 * 1000) + ((0.002 / 365) * (accounts[i].accountValue - 1000));
                        totalInterest += (0.001 / 365.25 * 1000) + ((0.002 / 365) * (accounts[i].accountValue - 1000));
                    }
                }
                if (i == 2) {
                    if (daysSinceLastWithdraw(accounts[i]) > 10) {
                        accounts[i].accountValue += (0.05 / 365.25 * accounts[i].accountValue);
                        totalInterest += (0.05 / 365.25 * accounts[i].accountValue);
                    } else {
                        accounts[i].accountValue += (0.01 / 365.25) * accounts[i].accountValue;
                        totalInterest += (0.01 / 365.25) * accounts[i].accountValue;
                    }
                }

            }
        }
        return (totalInterest);
    }
}
