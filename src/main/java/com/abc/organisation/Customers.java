package com.abc.organisation;

import com.abc.constants.AccountConstantsNew;
import com.abc.constants.OperationConstants;
import com.abc.person.Customer;
import com.abc.person.Name;
import com.abc.utilities.Money;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by vahizan on 19/08/2017.
 */
public class Customers {
    private List<Customer> customers;
    public Customers(){ this.customers=new ArrayList<Customer>(); }
    public void add(Customer customer){ customers.add(customer); }
    public int size(){ return customers.size(); }
    public List<Customer> all(){return customers;}
    public String report(){
        StringBuilder sb = new StringBuilder();
        for(Customer customer: customers){
            createSection(sb, customer);
        }
        return sb.toString();
    }
    public String interestReport(AccountConstantsNew accountType){
        StringBuilder sb = new StringBuilder();
        accountTypeInString(sb, accountType);
        sb.append("\n")
                .append(totalInterestPaid(accountType).amount());
        return sb.toString();
    }
    private void accountTypeInString(StringBuilder sb,AccountConstantsNew accountType){
        if(accountType==AccountConstantsNew.MAXI_SAVINGS){
            sb.append("Total Interest Paid By Bank for Maxi Savings Accounts: ");
        }else if(accountType==AccountConstantsNew.CHECKING){
            sb.append("Total Interest Paid By Bank for Checking Accounts: ");
        }else if(accountType==AccountConstantsNew.SAVINGS){
            sb.append("Total Interest Paid By Bank for Savings Accounts: ");
        }
    }
    private Money totalInterestPaid(AccountConstantsNew accountType){
        Money finalMoney = new Money(0);
        for(Customer customer:customers){
             Money money = customer.totalInterest(accountType);
             finalMoney.calculate(money, OperationConstants.ADD);
        }
        return finalMoney;
    }
    private void createSection(StringBuilder sb,Customer customer){
        sb.append(fullName(customer))
                .append("\n")
                .append("Number Of Accounts: ")
                .append(customer.getNumberOfAccounts())
                .append("\n");
    }
    private String fullName(Customer customer){
        Name name = customer.name();
        return name.toString();
    }
}