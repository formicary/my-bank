package com.abc.history;

import com.abc.transactions.AbstractTransaction;
import com.abc.transactions.NullTransaction;
import com.abc.transactions.Withdraw;
import com.abc.utilities.DateProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vahizan on 18/08/2017.
 */
public class TransactionHistory implements History {

    private List<AbstractTransaction> transactions;

    public TransactionHistory(){
        transactions=new ArrayList<AbstractTransaction>();
    }
    public void addTransaction(AbstractTransaction transaction){
        transactions.add(transaction);
    }
    public String fullStatement() {
        StringBuilder sb = new StringBuilder();
        for(AbstractTransaction transaction:transactions){
            appendInformation(transaction.transactionInformation(), sb);
        }
        return sb.toString();
    }
    public String periodStatement(Date dateAfter,Date dateBefore) {
        StringBuilder sb = new StringBuilder();
        for(AbstractTransaction transaction:transactions){
            addInformationInPeriod(sb, transaction, dateAfter, dateBefore);
        }
        return sb.toString();
    }
    public Date lastWithdrawal() {
        String dateString = "";
        for(AbstractTransaction transaction:transactions){
            dateString=assignWithdrawDate(transaction);
        }
        return DateProvider.getInstance().stringToDate(dateString);
    }
    private String assignWithdrawDate(AbstractTransaction transaction){
        if(transaction instanceof Withdraw){
            return transaction.stringDate();
        }
        return "No Date";
    }
    private void addInformationInPeriod(StringBuilder sb,AbstractTransaction transaction,Date dateAfter,Date dateBefore){
        boolean correctPeriod=correctPeriod(transaction,dateAfter,dateBefore);
        if(correctPeriod) {
            appendInformation(transaction.transactionInformation(), sb);
        }
    }
    private boolean correctPeriod(AbstractTransaction transaction,Date dateAfter,Date dateBefore){
        String stringDate = transaction.stringDate();
        Date transactionDate = DateProvider.getInstance().stringToDate(stringDate);
        return (transactionDate.after(dateAfter) && transactionDate.before(dateBefore));
    }
    private void appendInformation(String information,StringBuilder sb){
        sb.append(information)
                .append("\n");
    }

}
