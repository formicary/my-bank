package com.abc.person;

import com.abc.accounts.*;
import com.abc.constants.AccountConstantsNew;
import com.abc.rates.Interest;
import com.abc.utilities.Days;
import com.abc.utilities.Money;
/**
 * Created by vahizan on 07/08/2017.
 */
public class Customer implements Person{
    private BankAccounts accounts;
    private Name name;
    public Customer(Name name){
        this.name=name;
        this.accounts=new BankAccounts();
    }
    private Account createAccount(AccountConstantsNew accountType){
        if(accountType==AccountConstantsNew.SAVINGS){
            SavingsAccount savings = new SavingsAccount();
            accounts.addAccount(savings);
            return savings;
        }else if(accountType==AccountConstantsNew.MAXI_SAVINGS){
            MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
            accounts.addAccount(maxiSavingsAccount);
            return maxiSavingsAccount;
        }else if(accountType==AccountConstantsNew.CHECKING){
            CheckingsAccount checkingsAccount = new CheckingsAccount();
            accounts.addAccount(checkingsAccount);
            return checkingsAccount;
        }
        return NullAccount.getInstance();
    }
    public boolean accountExists(AccountConstantsNew accountType){
       return accounts.exists(accountType);
    }
    public Account openAccount(AccountConstantsNew accountType){
        if(accounts.exists(accountType)){
            return NullAccount.getInstance();
        }
        return createAccount(accountType);
    }
    public void transferMoney(AccountConstantsNew from, AccountConstantsNew to, Money amount){
        accounts.transferMoney(from,to,amount);
    }
    public void withdrawMoney(AccountConstantsNew accountType,Money amount){
        accounts.withdrawMoney(accountType,amount);
    }
    public void depositMoney(AccountConstantsNew accountType,Money amount){
        accounts.depositMoney(accountType,amount);
    }
    public void gainInterest(Interest interest, AccountConstantsNew accountType){
        accounts.gainInterest(interest,accountType);
    }
    public boolean withdrawalInPeriod(Days withdrawalPeriod, AccountConstantsNew accountType){
        return accounts.withdrawnInPeriod(withdrawalPeriod, accountType);
    }
    public String accountStatement(AccountConstantsNew accountType){
        Account account = accounts.findAccount(accountType);
        return account.bankStatement();
    }
    public Money totalInterest(AccountConstantsNew accountType){
        Account account =accounts.findAccount(accountType);
        float totalInterest = account.totalInterest();
        return new Money(totalInterest);
    }
    public int getNumberOfAccounts(){
        return accounts.size();
    }
    public Name name() {
        return name;
    }
}
