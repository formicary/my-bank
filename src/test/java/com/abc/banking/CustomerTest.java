package com.abc.banking;

import java.math.BigDecimal;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.abc.banking.AbstractAccount;
import com.abc.banking.Account;
import com.abc.banking.Bank;
import com.abc.banking.CheckingAccount;
import com.abc.banking.Customer;
import com.abc.banking.SavingsAccount;
import com.abc.banking.Transaction;
import com.abc.banking.exceptions.AccountOpeningException;
import com.abc.banking.exceptions.TransactionException;
import com.abc.utils.TestingUtils;

public class CustomerTest {

    @Test
    public void getNumberOfAccountsShallReturnCorrectValueForOneAccount() throws AccountOpeningException {
        Bank bank = new Bank();
        Customer oscar = bank.newCustomer("Oscar");
        oscar.openAccount(SavingsAccount.class);
        Assert.assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void getNumberOfAccountsShallReturnCorrectValueForTwoAccounts() throws AccountOpeningException {
        Bank bank = new Bank();
        Customer oscar = bank.newCustomer("Oscar");
        oscar.openAccount(SavingsAccount.class);
        oscar.openAccount(CheckingAccount.class);

        Assert.assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void customerShallBeEqualOnlyIfTheSameInstance() throws IllegalAccessException, InstantiationException  {
    	Customer patrik = new Customer("Patrik");
    	Customer patrik2 = new Customer("Patrik");
    	
    	Assert.assertNotEquals(patrik, patrik2);
    	Assert.assertEquals(patrik, patrik);
    }
    
    @Test
    public void sameInstanceShallHaveSameHashCode() throws IllegalAccessException, InstantiationException  {
    	Customer johny5 = new Customer("Johny 5");
    	
    	Assert.assertEquals(johny5.hashCode(), johny5.hashCode());
    }
    
    @Test
    public void transferBetweenAccountsShallIncreaseTransferorAccount() throws Exception  {
    	Customer johny5 = new Customer("Johny 5");
    	
    	Account fromAccount = johny5.openAccount(SavingsAccount.class);
    	Account toAccount = johny5.openAccount(CheckingAccount.class);
    	
    	fromAccount.deposit(new BigDecimal("555.00"));
    	
    	BigDecimal formerBalance = toAccount.getBalance();
    	
    	johny5.transferBetweenAccounts(fromAccount, toAccount, new BigDecimal("130.00"));
    	
    	BigDecimal increase = toAccount.getBalance().subtract(formerBalance);
    	
    	TestingUtils.assertEqualsBigDecimal(new BigDecimal("130.00"), increase);
    }
    
    @Test
    public void transferBetweenAccountsShallDecreaseTransfereeAccount() throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	
    	Account fromAccount = johny5.openAccount(SavingsAccount.class);
    	Account toAccount = johny5.openAccount(CheckingAccount.class);
    	
    	fromAccount.deposit(new BigDecimal("555.00"));
    	
    	BigDecimal formerBalance = fromAccount.getBalance();
    	
    	johny5.transferBetweenAccounts(fromAccount, toAccount, new BigDecimal("130.00"));
    	
    	BigDecimal decrease = formerBalance.subtract(fromAccount.getBalance());
    	
    	TestingUtils.assertEqualsBigDecimal(new BigDecimal("130.00"), decrease);
    }

    @Test
    public void transferBetweenAccountsShallReturnSameButOppositeAmountTransactions()  throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	
    	Account fromAccount = johny5.openAccount(SavingsAccount.class);
    	Account toAccount = johny5.openAccount(CheckingAccount.class);
    	
    	fromAccount.deposit(new BigDecimal("555.00"));
    	
    	Pair<Transaction, Transaction> result =
    			johny5.transferBetweenAccounts(fromAccount, toAccount, new BigDecimal("130.00"));
    	
    	Transaction fromTransaction = result.getLeft();
    	Transaction toTransaction = result.getRight();
    	
    	TestingUtils.assertEqualsBigDecimal(fromTransaction.getAmount(), toTransaction.getAmount().negate());
    	Assert.assertEquals(fromTransaction.getTransactionDate(), toTransaction.getTransactionDate());
    	Assert.assertEquals(fromTransaction.isAccruedInterest(), toTransaction.isAccruedInterest());
    	
    }
    
    @Test(expected = TransactionException.class)
    public void transferBetweenAccountsShallFailIfNotSufficientFunds()  throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	
    	Account fromAccount = johny5.openAccount(SavingsAccount.class);
    	Account toAccount = johny5.openAccount(CheckingAccount.class);
    	
    	fromAccount.deposit(new BigDecimal("555.00"));
    	
    	johny5.transferBetweenAccounts(fromAccount, toAccount, new BigDecimal("900.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferBetweenAccountsShallFailOnNullArgumentsSource()  throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	Account toAccount = johny5.openAccount(CheckingAccount.class);
    	
    	johny5.transferBetweenAccounts(null, toAccount, new BigDecimal("130.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferBetweenAccountsShallFailOnNullArgumentDestination()  throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	Account fromAccount = johny5.openAccount(SavingsAccount.class);
    	fromAccount.deposit(new BigDecimal("555.00"));
    	
    	johny5.transferBetweenAccounts(fromAccount, null, new BigDecimal("130.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferBetweenAccountsShallFailOnNullArgumentAmount()  throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	Account fromAccount = johny5.openAccount(SavingsAccount.class);
    	Account toAccount = johny5.openAccount(CheckingAccount.class);
    	fromAccount.deposit(new BigDecimal("555.00"));
    	
    	johny5.transferBetweenAccounts(fromAccount, toAccount, null);
    }

    @Test(expected = AccountOpeningException.class)
    public void openingAnAbstractAccountShallFail() throws Exception {
    	new Customer("Failo").openAccount(AbstractAccount.class);
    }

    @Test(expected = AccountOpeningException.class)
    public void openingAnInterfaceAccountShallFail() throws Exception {
    	new Customer("Failo").openAccount(Account.class);
    }

    @Test(expected = TransactionException.class)
    public void transferFromAccountOfAnotherClientShallFail()  throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	Account johnysAccount = johny5.openAccount(SavingsAccount.class);

    	Customer andrew = new Customer("Andrew");
    	Account andrewsAccount = andrew.openAccount(CheckingAccount.class);
    	
    	johny5.transferBetweenAccounts(andrewsAccount, johnysAccount, new BigDecimal("130.00"));
    }

    @Test(expected = TransactionException.class)
    public void transferToAccountOfAnotherClientShallFail()  throws Exception {
    	Customer johny5 = new Customer("Johny 5");
    	Account johnysAccount = johny5.openAccount(SavingsAccount.class);

    	Customer andrew = new Customer("Andrew");
    	Account andrewsAccount = andrew.openAccount(CheckingAccount.class);
    	
    	johny5.transferBetweenAccounts(johnysAccount, andrewsAccount, new BigDecimal("130.00"));
    }
    
    @Test
    public void constructorShallAssignName() {
    	Customer rudolph = new Customer("Rudolph");
    	Assert.assertEquals("Rudolph", rudolph.getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void constructorShallThrowExceptionOnNullArgument() {
    	new Customer(null);
    }
    
    @Test
    public void setNameShallChangeName() {
    	Customer rudolph = new Customer("Rudolph");
    	rudolph.setName("Rodolfo");
    	Assert.assertEquals("Rodolfo", rudolph.getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void setNameShallThrowExceptionOnNullArgument() {
    	Customer customer = new Customer("Bill");
    	customer.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void openAccountShallThrowExceptionOnNullArgument() throws Exception {
    	new Customer("John").openAccount(null);
    }

}
