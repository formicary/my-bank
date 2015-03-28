package com.abc;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.abc.accounts.AccountType;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.MaxiSavingsAccount;
import com.abc.accounts.SavingsAccount;
import com.abc.exceptions.ExceededFundsException;
import com.abc.exceptions.InvalidAccountTypeException;
import com.abc.exceptions.InvalidCustomerAccount;
import com.abc.exceptions.InvalidCustomerName;
import com.abc.interests.CheckingStrategy;
import com.abc.interests.SavingsStrategy;
import com.abc.interests.WithdrawalBasedMaxiStrategy;

/**
 * This class represents a customer of the bank. Customer can open an account, 
 * maintain several accounts, transfer between them, get statement and total interest earned. 
 */
final public class Customer {
    
	/**
	 * Unique name of the customer.
	 */
	final private String name;
	/**
	 * All accounts belonging to the customer. 
	 */
    final private List<Account> accounts;
    /**
     * Factory class responsible for opening an account.
     */
    final private AbstractAccountFactory accountFactory;
    
    /**
     * Constructor of the customer.
     * Normally, it should not be directly used. Bank instance can create customers properly.
     * @param name unique name of the customer.
     * @throws InvalidCustomerName if name was not validated, only letters are allowed.
     */
    protected Customer(final String name) throws InvalidCustomerName {
    	if (!validateName(name)) throw new InvalidCustomerName("Name did not pass validation " + name);
    	this.name = name;
    	this.accountFactory = new AccountFactory();
        this.accounts = new ArrayList<Account>();
    }
    
    private class AccountFactory implements AbstractAccountFactory {

    	@Override
    	public Account createCheckingAccount() {
    		Account account = new CheckingAccount();
    		account.setInterestRateStrategy(new CheckingStrategy());
    		return account;
    	}

    	@Override
    	public Account createSavingsAccount() {
    		Account account =  new SavingsAccount();
    		account.setInterestRateStrategy(new SavingsStrategy());
    		return account;
    	}

    	@Override
    	public Account createMaxiSavingAccount() {
    		Account account = new MaxiSavingsAccount();
    		account.setInterestRateStrategy(new WithdrawalBasedMaxiStrategy(account));
    		return account;
    	}

    }
    
    /**
	 * @param account.
	 * @return true if this account belong to this customer.
	 */
	public boolean contains (final Account account) {
		return accounts.contains(account);
	}

    /** 
     * @return name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * @param accountType type of the account.
     * @return new account of the corresponding type.
     * @throws InvalidAccountTypeException if method does not know how to open an account of this type.
     */
    public Account openAccount(final AccountType accountType) throws InvalidAccountTypeException {
        Account account = null;
    	switch (accountType) {
		case CHECKING:
			account = accountFactory.createCheckingAccount();
			break;
		case SAVINGS:
			account = accountFactory.createSavingsAccount();
			break;
		case MAXISAVINGS:
			account = accountFactory.createMaxiSavingAccount();
			break;
		default:
			throw new InvalidAccountTypeException("This AccountType is not handled in this method.");
			
		}
    	
    	accounts.add(account);
        return account;
    }

    /**
     * @return number of the accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * @return total interest earned for this customer
     */
    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts) {
            total = total.add(a.interestEarned().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return total;
    }

    /**
     * @return statement of all accounts for this customer.
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for ").append(name).append(System.lineSeparator());
        BigDecimal total = BigDecimal.ZERO;
        if (accounts.isEmpty()) {
        	statement.append("There are no accounts associated with ").append(name);
        	return statement.toString();
        }
        for (Account a : accounts) {
            statement.append(System.lineSeparator())
            .append(statementForAccount(a))
            .append(System.lineSeparator());
            total = total.add(a.getBalance());
        }
        statement.append(System.lineSeparator())
        .append("Total In All Accounts ")
        .append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(final Account account) {
       StringBuilder sb = new StringBuilder();
       sb.append(account.getAccountType()).append(System.lineSeparator());

        //Now total up all the transactions
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : account.getTransactions()) {
            sb.append("  ")
            .append(t.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit")
            .append(" ").append(toDollars(t.getAmount()))
            .append(System.lineSeparator());
            total = total.add(t.getAmount());
        }
        sb.append("Total ").append(toDollars(total));
        return sb.toString();
    }

    private String toDollars (final BigDecimal total) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(total.abs());
    }

 	protected void updateInterestEarned() {
		for (Account account: accounts) {
			account.updateInterestEarned();
		}	
	}
	
 	/**
 	 * 
 	 * @param from account to be debited.
 	 * @param to account to be credited.
 	 * @param amount overall sum in this transfer.
 	 * @return true if transfer succeeded, false otherwise.
 	 * @throws InvalidCustomerAccount if one of the accounts does not belong to this customer.
 	 * @throws ExceededFundsException is amount is higher than the balance of the debited account.
 	 */
	public boolean transferBetweenAccounts (final Account from, final Account to, final BigDecimal amount) throws InvalidCustomerAccount, ExceededFundsException {
		if (contains(from) && contains(to)) {
			if (!from.equals(to)) {
				return from.transferTo(to, this, amount);
			} else {
				//alert somehow, e.g. log
				return false;
			}
		} else 
			throw new InvalidCustomerAccount("One of the Account instances does not belong to this Customer");
	}
	
	//methods that checks that passed name eligible
    private boolean validateName(final String name) {
    	if (name.matches("^[A-Z][a-z]+(( [A-Z][a-z]+)+)?$")) return true;
    	return false;
    }
    
}
