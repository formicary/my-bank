package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public abstract class Account {

	private final String accountType;
	private final int accountID;
	private BigDecimal balance = new BigDecimal(0);
	private ArrayList<Deposit> depositList;
	private ArrayList<Withdrawal> withdrawalList;

	public Account(String accountType, int accountID) {
		this.accountType = accountType;
		this.accountID = accountID;
		this.depositList = new ArrayList<Deposit>();
		this.withdrawalList = new ArrayList<Withdrawal>();
	}

	public String deposit(BigDecimal amount) {
		if (amount.signum() == Constants.ZERO_INT || amount.signum() == Constants.NEGATIVE_ONE) {
			throw new IllegalArgumentException(Constants.ACCOUNT_DEPOSIT_ILLEGAL_ARGUMENT_EXCEPTION);
		} else {
			BigDecimal updatedBalance = getBalance().add(amount);
			setBalance(updatedBalance.setScale(2, RoundingMode.HALF_UP));
			recordDeposit(amount);
			return Constants.SUCCESSFUL_DEPOSIT;
		}
	}

	public String withdraw(BigDecimal amount) {
		if (amount.signum() == Constants.ZERO_INT || amount.signum() == Constants.NEGATIVE_ONE) {
			throw new IllegalArgumentException(Constants.ACCOUNT_WITHDRAW_ILLEGAL_ARGUMENT_EXCEPTION);
		} else {
			BigDecimal potentialBalance = getBalance().subtract(amount);
			if (potentialBalance.signum() == Constants.NEGATIVE_ONE) {
				return Constants.INSUFFICIENT_FUNDS;
			}

			else {
				BigDecimal updatedBalance = getBalance().subtract(amount);
				setBalance(updatedBalance.setScale(2, RoundingMode.HALF_UP));
				recordWithdrawal(amount);
				return Constants.SUCCESSFUL_WITHDRAWAL;
			}
		}
	}

	public void recordDeposit(BigDecimal amount) {
		getDepositList().add(new Deposit(amount));
	}

	public void recordWithdrawal(BigDecimal amount) {
		getWithdrawalList().add(new Withdrawal(amount));
	}

	public BigDecimal interestEarned() {
		return Constants.INTEREST_EARNED;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public ArrayList<Deposit> getDepositList() {
		return depositList;
	}

	public void setDepositList(ArrayList<Deposit> deposits) {
		this.depositList = deposits;
	}

	public ArrayList<Withdrawal> getWithdrawalList() {
		return withdrawalList;
	}

	public void setWithdrawalList(ArrayList<Withdrawal> withdrawals) {
		this.withdrawalList = withdrawals;
	}

	public String getAccountType() {
		return accountType;
	}

	public int getAccountID() {
		return accountID;
	}
}
