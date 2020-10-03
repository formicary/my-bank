package com.abc.service;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.entity.Transaction;
import java.math.BigDecimal;
import static java.lang.Math.abs;

/**
 * Customer statement service to generate statements of transactions for customers and accounts
 * @author aneesh
 */
public class CustomerStatementService {


    /**
     * Generate a report of the customers transactions and holdings for each account
     * @param customer customer to generate a report for
     * @return a report of the customer transactions for each account and final holdings
     */
    public static String generateStatement(Customer customer) {
        StringBuilder statement = new StringBuilder()
            .append("Statement for " + customer.getName() + "\n");

        BigDecimal total = new BigDecimal(0.0);
        for (Account a : customer.getAccounts().values()) {
            statement.append("\n" + accountStatement(a) + "\n");
            total = total.add(a.calculateBalance());
        }
        statement.append("\nTotal In all Accounts " + toDollars(total));
        return statement.toString();
    }

    private static String accountStatement(Account a) {
        StringBuilder line = new StringBuilder()
                .append(a.getAccountType().toString() + ": ");

        BigDecimal total = new BigDecimal(0);
        for (Transaction t : a.getTransactions()) {
            line.append("\n  " + (t.getAmount().intValue() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()));
            total = total.add(t.getAmount());
        }
        line.append("\nTotal " + toDollars(total));
        return line.toString();
    }

    private static String toDollars(BigDecimal amount){
        return String.format("$%,.2f", abs(amount.doubleValue()));
    }

}
