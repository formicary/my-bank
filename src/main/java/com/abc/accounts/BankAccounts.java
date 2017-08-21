package com.abc.accounts;

import com.abc.constants.AccountConstantsNew;
import com.abc.rates.Interest;
import com.abc.utilities.Days;
import com.abc.utilities.Money;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by vahizan on 18/08/2017.
 */
public class BankAccounts {
    private List<Account> accounts;
    public BankAccounts(){accounts = new ArrayList<Account>();}
    public void addAccount(Account account){accounts.add(account);}
    public Account findAccount(AccountConstantsNew accountType){
        Account foundAccount=NullAccount.getInstance();
        for(Account acc:accounts){
            boolean isAccount = isAccount(acc,accountType);
            foundAccount=correctAccount(isAccount,acc);
        }
        return foundAccount;
    }
    public boolean exists(AccountConstantsNew accountType){
        Account accounts = findAccount(accountType);
        boolean accountExist=accounts.accountType()!=AccountConstantsNew.NULL;
        return accountExist;
    }
    public int size(){
        return accounts.size();
    }
    public void transferMoney(AccountConstantsNew from, AccountConstantsNew to, Money amount){
        Account fromAccount = findAccount(from);
        Account toAccount= findAccount(to);
        fromAccount.withdrawMoney(amount);
        toAccount.depositMoney(amount);
    }
    public void withdrawMoney(AccountConstantsNew accountType,Money amount){
        Account acc = findAccount(accountType);
        acc.withdrawMoney(amount);
    }
    public void depositMoney(AccountConstantsNew accountType,Money amount){
        Account acc = findAccount(accountType);
        acc.depositMoney(amount);
    }
    public void gainInterest(Interest interest, AccountConstantsNew accountType){
        Account account = findAccount(accountType);
        if(!(account instanceof NullAccount)) {
            account.depositInterest(interest);
        }
    }
    public boolean withdrawnInPeriod(Days withdrawalPeriod, AccountConstantsNew accountType){
        Account acc = findAccount(accountType);
        boolean withdrawal=false;
        if(!(acc instanceof NullAccount)) {
            withdrawal=acc.withdrawalInPeriod(withdrawalPeriod);
        }
        return withdrawal;
    }
    private boolean isAccount(Account account,AccountConstantsNew accountType){
        return account.accountType()==accountType;
    }
    private Account correctAccount(boolean isAccount,Account acc){
        if(isAccount){
            return acc;
        }
        return NullAccount.getInstance();
    }
}