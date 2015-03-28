package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abc.accounts.AccountType;
import com.abc.exceptions.ExceededFundsException;
import com.abc.exceptions.InvalidCustomerAccount;
import com.abc.interests.InterestRateStrategy;

/**
 * <code>Account</code> class is a naive implementation aimed to replicate 
 * limited functionality of the actual bank account. It misses essential features like account number.
 * The owner of this class can deposit, withdraw, and transfer money between own accounts.
 * The class is thought for single-threaded use, it is not thread safe.
 * This class is abstract.
 * 
 */
public abstract class Account {

	/**
	 * List of all transactions made with this account.
	 */
	final private List<Transaction> transactions;
	/**
	 * The current balance.
	 */
	private BigDecimal balance; 
	/**
	 * The interest earned on current date.
	 */
	private BigDecimal interestEarned;
	/**
	 * The strategy used for interest rate computation.
	 */
	private InterestRateStrategy strategy;

	/**
	 * Constructor of the account. It is advised not to use it directly or from its subclasses.
	 * Customer class is responsible for account creation.
	 */
	protected Account() {
		this.balance = BigDecimal.ZERO;
		this.interestEarned = BigDecimal.ZERO;
		this.transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * @param strategy specific strategy for interest rate computation.
	 */
	public void setInterestRateStrategy(final InterestRateStrategy strategy) {
		if (isApplicable(strategy)) {
			this.strategy = strategy;
		} else {
			throw new IllegalArgumentException("This strategy is not applicable for " + getAccountType());
		}
	}

	/**
	 * This method should test is the passed strategy is applicable for this account type.
	 * @param strategy specific strategy for interest rate computation.
	 * @return true if applicable, otherwise false.
	 */
	public abstract boolean isApplicable(final InterestRateStrategy strategy);


	/**
	 * Method to deposit money.
	 * @param amount amount to deposit.
	 * @throws IllegalArgumentException if requested deposit is less than or equal to zero.
	 */
	public void deposit(final BigDecimal amount) {

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		BigDecimal famount = format(amount);
		BigDecimal tempBalance = balance.add(famount);

		// given that BigDecimal is arbitrary precision this condition should never return true
		// it is left here just for the consistency
		// if BigDecimal type will be replaced with primitive type
		if (tempBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new ArithmeticException();
		}

		balance = tempBalance;
		transactions.add(new Transaction(famount));
	}


	/**
	 * Gets the current balance of this <code>Account</code>.
	 * @return the current balance.
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Interest earned at the moment.
	 * @return amount of interest earned.
	 */
	public BigDecimal interestEarned() {
		return interestEarned;
	}
	
	/**
	 * Recompute interest. Should not be directly used.
	 */
	protected void updateInterestEarned () {
		interestEarned = interestEarned.add(strategy.computeInterestEarned(balance));
	}
	
	/**
	 * Method to withdraw money.
	 * @param amount amount to withdraw.
	 * @return amount withdrawn from the <code>Account</code>.
	 * @throws ExceededFundsException if the account contains insufficient funds for the requested withdrawal.
	 * @throws IllegalArgumentException if requested withdrawal is less than or equal to zero.
	 */
	public BigDecimal withdraw(final BigDecimal amount) throws ExceededFundsException {

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}

		if (amount.compareTo(balance) > 0) {
			throw new ExceededFundsException("Insufficient funds on the account");
		}
		BigDecimal famount = format(amount);
		balance = balance.subtract(famount);
		transactions.add(new Transaction(famount.negate()));
		return famount;
	}


	/**
	 * @return type of the account <code>AccountType</code>. 
	 */
	public abstract AccountType getAccountType();

	/**
	 * @return list of the transactions.
	 */
	public List<Transaction> getTransactions() {
		List<Transaction> clone = new ArrayList<>();
		for (Transaction t: transactions) {
			try {
				clone.add((Transaction)t.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				//TODO consider re-throwing to handle it elsewhere 
			}	
		}
		return clone;
	}
	/**
	 * Transfer money from this account to specified account.
	 * @param to account to be credited.
	 * @param customer must be the same <code>Customer</code> for both accounts.
	 * @return true if succeeded, otherwise false.
	 * @throws InvalidCustomerAccount if <code>Customer</code> is not the owner of both accounts.
	 * @throws ExceededFundsException  if debited account balance is insufficient for passed amount.
	 */
	public boolean transferTo (final Account to, final Customer customer, final BigDecimal amount) throws InvalidCustomerAccount, ExceededFundsException {
		if (customer.contains(to) && customer.contains(this)) {
			if (!this.equals(to)) {
				withdraw(amount);
				to.deposit(amount);
				return true;
			} else {
				return false;
			}
		} else
			throw new InvalidCustomerAccount("One of the Account instances does not belong to this Customer");
		
	}
	
	private BigDecimal format (final BigDecimal amount) {
		return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	

}
