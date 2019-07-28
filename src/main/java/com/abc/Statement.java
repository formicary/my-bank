package com.abc;

import java.text.SimpleDateFormat;

import static java.lang.Math.abs;


/**
 * Class to handle building and formatting of account statements
 */
public class Statement {


    /**
     * Generate statement for each account a customer has, returning a string of all formatted statements
     */
    public static String generateCustomerStatement(Customer c){

        StringBuilder statement = new StringBuilder("Statement for " + c.getName() + "\n");

        double total = 0.0;
        // Create statement for each account and update total
        for (Account account : c.getAccounts()) {
            statement.append("\n").append(Statement.generateAccountStatement(account)).append("\n");
            total += account.sumTransactions();
        }
        statement.append("\nTotal in all accounts ").append(Statement.toDollars(total, false));
        return statement.toString();
    }


    /**
     * Generate a detailed account statement, with information about transactions and running account total.
     * Information is formatted into columns.
     */
    public static String generateAccountStatement(Account a) {

        // Build statement heading
        StringBuilder statement = new StringBuilder(a.getStringAccountType() + ":\n");
        int[] columnWidths = {20, 15, 15, 15, 15};
        String[] columnHeaders = {"Title", "Date", "Withdrawn", "Deposited", "New total"};

        statement.append(formatStatementLine(columnHeaders, columnWidths));
        statement.append(formatStatementLine(new String[]{"", "", "", "", ""}, columnWidths));

        // Build statement body from account transactions
        double total = 0;
        for (Transaction t : a.getTransactions()) {
            // Get all components of transaction and format
            total += t.getAmount();
            String date = new SimpleDateFormat("yyyy/MM/dd").format(t.getTransactionDate());
            String withdrawn = t.getAmount() < 0 ? toDollars(t.getAmount(), true) : "";
            String deposited = t.getAmount() > 0 ? toDollars(t.getAmount(), true) : "";
            String[] line = {t.getTitle(), date, withdrawn, deposited, toDollars(total, false)};

            statement.append(formatStatementLine(line, columnWidths));
        }
        statement.append("Total: ").append(toDollars(total, false));
        return statement.toString();
    }


    /**
     * Take a single item to be printed in the account statement and format the string to be a specific length. This
     * will ensure that everything lines up when concatenated.
     *
     * @param f string to format
     * @param length desired length
     */
    private static String formatStatementItem(String f, int length){
        if (f.length() > length) {
            return f.substring(0, length) + "|";
        }
        else{
            return String.format("%1$-" + length + "s", f) + "|";
        }
    }


    /**
     * Take a list of strings and concatenate them into a single line, with whitespace between items such that columns
     * will line up when lines are displayed.
     */
    private static String formatStatementLine(String[] items, int[] lengths){

        StringBuilder line = new StringBuilder();
        for (int i=0; i< items.length; i++){
            line.append(formatStatementItem(items[i], lengths[i]));
        }
        return line.toString() + "\n";
    }


    /**
     * Formats given number into currency form: $xxx.xx
     *
     * @param absoluteValue whether to put '-' in front of negatives. This is because withdrawals are negative numbers
     *                     but should not be displayed as such. Account total however should show the sign of the value
     */
    private static String toDollars(double d, Boolean absoluteValue){

        return String.format("$%,.2f", (absoluteValue ? abs(d):d));
    }
}
