package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.abc.Bank;
import com.abc.exceptions.ExceededFundsException;
import com.abc.exceptions.InvalidAccountTypeException;
import com.abc.exceptions.InvalidCustomerName;
import com.abc.executors.AbstractInterestCalculator;
import com.abc.executors.InterestRateExecutor;
import com.abc.executors.NaiveInterestRateExecutor;
import com.abc.interests.DefaultMaxiSavingsStrategy;
import com.abc.interests.WithdrawalBasedMaxiStrategy;

import static com.abc.accounts.AccountType.*;
import static org.junit.Assert.*;

public class BankTest {
    
    private Bank bank;
    @Rule
	public ExpectedException exception;
    
    @Before
    public void setUp () {
    	bank = new Bank();
    	exception = ExpectedException.none();
    }

    @Test
    public void customerSummary() {
        try {
        	Customer john = bank.createCustomer("John");
			john.openAccount(CHECKING);
		} catch (InvalidAccountTypeException | InvalidCustomerName e) {
			e.printStackTrace();
		}
        
        assertEquals("Customer Summary\n - John (1 account)", bank.customersSummary());
    }
    
    @Test
    public void fewCustomerSummaries() {
    	try {
    		Customer john = bank.createCustomer("John");
    		Customer bill = bank.createCustomer("Bill");
    		john.openAccount(SAVINGS);
    		john.openAccount(CHECKING);
    		bill.openAccount(MAXISAVINGS);
    	} catch (InvalidAccountTypeException | InvalidCustomerName e) {
    		e.printStackTrace();
    	}
    	
    	 assertEquals("Customer Summary\n - John (2 accounts)\n - Bill (1 account)", bank.customersSummary());
    }
    

    @Test
    public void checkingAccount() {
		try {
			Customer bill = bank.createCustomer("Bill");
			Account checkingAccount;
			checkingAccount = bill.openAccount(CHECKING);
			checkingAccount.deposit(new BigDecimal("100.00"));
			for (int i=0; i<365; i++)
				checkingAccount.updateInterestEarned();
		} catch (InvalidAccountTypeException | InvalidCustomerName e) {
			e.printStackTrace();
		}

        assertEquals(new BigDecimal("0.10"), bank.totalInterestPaid());
        
    }

    @Test
    public void savingsAccount() {
		try {
			Customer bill = bank.createCustomer("Bill");
			Account savingsAccount;
			savingsAccount = bill.openAccount(SAVINGS);
			savingsAccount.deposit(new BigDecimal("1500.00"));
			for (int i=0; i<365; i++)
				savingsAccount.updateInterestEarned();
		} catch (InvalidAccountTypeException | InvalidCustomerName e) {
			e.printStackTrace();
		}

        assertEquals(new BigDecimal("2.00"), bank.totalInterestPaid());
    }

    @Test
    public void maxisavingsAccount() {
		try {
			Customer bill = bank.createCustomer("Bill");
			Account maxisavingsAccount;
			maxisavingsAccount = bill.openAccount(MAXISAVINGS);
			maxisavingsAccount.deposit(new BigDecimal("3000.00"));
			maxisavingsAccount.setInterestRateStrategy(new DefaultMaxiSavingsStrategy());
			for (int i=0; i<365; i++)
				maxisavingsAccount.updateInterestEarned();
		} catch (InvalidAccountTypeException | InvalidCustomerName e) {
			e.printStackTrace();
		}

        assertEquals(new BigDecimal("170.00"), bank.totalInterestPaid());
    }
    
    @Test
    public void maxisavingsAccountWithdrawalStrategy() {
		try {
			Customer bill = bank.createCustomer("Bill");
			Account maxisavingsAccount;
			maxisavingsAccount = bill.openAccount(MAXISAVINGS);
			maxisavingsAccount.deposit(new BigDecimal("3000.00"));
			
			for (int i=0; i<365; i++)
				maxisavingsAccount.updateInterestEarned();

			assertEquals(new BigDecimal("150.00"), bank.totalInterestPaid());
			
			maxisavingsAccount.withdraw(new BigDecimal("1000.00"));
			maxisavingsAccount.setInterestRateStrategy(new WithdrawalBasedMaxiStrategy(maxisavingsAccount) {
				@Override
				public boolean isLastWithdrawalExpired() {
					return false;
				}
			});
			for (int i=0; i<10; i++)
				maxisavingsAccount.updateInterestEarned();
			
			assertEquals(new BigDecimal("150.05"), bank.totalInterestPaid());
			
			maxisavingsAccount.setInterestRateStrategy(new WithdrawalBasedMaxiStrategy(maxisavingsAccount) {
				
				@Override
				public boolean isLastWithdrawalExpired() {
					return true;
				}
			});
			
			for (int i=0; i<365; i++)
				maxisavingsAccount.updateInterestEarned();
			
			assertEquals(new BigDecimal("250.05"), bank.totalInterestPaid());
			
		} catch (InvalidAccountTypeException | ExceededFundsException | InvalidCustomerName e) {
			e.printStackTrace();
		}     
    }
 
    @Test
    public void complexTotalInterestPaid () {
    	try {
    		Customer john = bank.createCustomer("John");
    		Customer bill = bank.createCustomer("Bill");
    		List<Customer> list = new ArrayList<>();
    		list.add(john);
    		list.add(bill);
    		Account checkingAccount, savingsAccount;
			checkingAccount = bill.openAccount(CHECKING);
			checkingAccount.deposit(new BigDecimal("100.00"));
			savingsAccount = john.openAccount(SAVINGS);
			savingsAccount.deposit(new BigDecimal("1500.00"));
			InterestRateExecutor executor = new NaiveInterestRateExecutor(new AbstractInterestCalculator() {
				@Override
				public void execute() {
					for (Customer c: list) {
						c.updateInterestEarned();
					}
				}
			});
			bank.setInterestRateExecutor(executor);
			//364 and not 365 as NaiveInterestRate executor executed the task once in setInterestReateExecutor method
			for (int i=0; i<364; i++)
				executor.start();
		} catch (InvalidAccountTypeException | InvalidCustomerName e) {
			e.printStackTrace();
		}

    	assertEquals(new BigDecimal("2.10"), bank.totalInterestPaid());
    	
    }
    
    @Test
    public void customersWithSameName() {
    	Customer john;
		try {
			john = bank.createCustomer("John");
			Customer john2 = bank.createCustomer("John");
			assertFalse(john.equals(john2));
		} catch (InvalidCustomerName e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void customerNames() {
		try {
			bank.createCustomer("John");
			bank.createCustomer("John Smith");
			exception.expect(InvalidCustomerName.class);
			bank.createCustomer("");

		} catch (InvalidCustomerName e) {}
    }

}
