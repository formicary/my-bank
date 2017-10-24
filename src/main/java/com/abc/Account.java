package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.math.BigDecimal;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;
    private Calendar nextinterest;

    private Calendar now; // Used for testing to customise transaction dates.

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.now = Calendar.getInstance();
//        now.setTimeInMillis(System.currentTimeMillis());
        Calendar nextinterest = (Calendar) now.clone();
        // Set nextinterest to last midnight.
        nextinterest.add(Calendar.MILLISECOND,- nextinterest.get(Calendar.MILLISECOND));
        nextinterest.add(Calendar.SECOND,- nextinterest.get(Calendar.SECOND));
        nextinterest.add(Calendar.MINUTE,- nextinterest.get(Calendar.MINUTE));
        nextinterest.add(Calendar.HOUR,- nextinterest.get(Calendar.HOUR));
        nextinterest.add(Calendar.DAY_OF_MONTH,1);
        this.nextinterest = nextinterest;
    }

	public Account(int accountType, Calendar date) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.now = date;
//		Calendar nextinterest = (Calendar) date.clone();
		// Set nextinterest to next midnight.
		nextinterest.add(Calendar.MILLISECOND,- nextinterest.get(Calendar.MILLISECOND));
		nextinterest.add(Calendar.SECOND,- nextinterest.get(Calendar.SECOND));
		nextinterest.add(Calendar.MINUTE,- nextinterest.get(Calendar.MINUTE));
		nextinterest.add(Calendar.HOUR,- nextinterest.get(Calendar.HOUR));
		nextinterest.add(Calendar.DAY_OF_MONTH,1);
		this.nextinterest = nextinterest;
	}

	// Interest is accrued onto the account on each interaction.
	// Set realtime to false to allow custom control of date.
    public void deposit(BigDecimal amount, boolean realtime) {
        if (amount.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	if (realtime) this.now.setTimeInMillis(System.currentTimeMillis());
            this.accrueInterest(now);
            transactions.add(new Transaction(amount, false,now));
        }
    }

	public void withdraw(BigDecimal amount, boolean realtime) {
		if (amount.compareTo(BigDecimal.ZERO) == -1) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			if (realtime) this.now.setTimeInMillis(System.currentTimeMillis());
			this.accrueInterest(now);
			transactions.add(new Transaction(amount.negate(), false,now));
		}
    }

	public List<Transaction> getTransactions(boolean realtime){
		if (realtime) now.setTimeInMillis(System.currentTimeMillis());
		this.accrueInterest(now);
		return this.transactions;
	}

	public BigDecimal interestEarned(boolean realtime) {
        if (realtime) now.setTimeInMillis(System.currentTimeMillis());
        this.accrueInterest(now);
		BigDecimal interest = BigDecimal.ZERO;
		for (Transaction t: transactions){
			if (t.interest)
				interest = interest.add(t.amount);
		}
		return interest;
	}

	// Use to customise date/time in testing.
	public void setDate(Calendar date){
		this.now = date;
	}

	private void accrueInterest(Calendar transactiondate){
		// Adds interest for the days since last check.
		BigDecimal amount;

		BigDecimal pointonepercent = new BigDecimal(0.00000273836082624434948482434714767443784569828915819);    // = 1.001^(1/365)
        BigDecimal pointtwopercent = new BigDecimal(0.00000547399487998969795913748156017534854741054057364);    // = 1.002^(1/365)
        BigDecimal fivepercent = new BigDecimal(0.0001336806171134403505084797728061304509749343850785);   // = 1.05^(1/365)

		int lastwithdrawl = this.lastWithdrawl();
		Calendar fullinterest;
		if (lastwithdrawl != -1) {
			fullinterest =(Calendar) transactions.get(lastwithdrawl).getDate().clone();
			fullinterest.add(Calendar.DAY_OF_MONTH, 10);
		} else {
			fullinterest = Calendar.getInstance();
			fullinterest.set(1900,0,0);
		}
		BigDecimal interest;

		while (nextinterest.before(transactiondate)){
			amount = this.sumTransactions();
			switch(accountType){
                case CHECKING:
                    interest = amount.multiply(pointonepercent);
                    break;
				case SAVINGS:
					if (amount.compareTo(new BigDecimal(1000)) != 1){
						interest = amount.multiply(pointonepercent);
					} else{
					    BigDecimal overshoot = amount.subtract(new BigDecimal(1000));
					    overshoot = overshoot.multiply(pointtwopercent);
						interest = overshoot.add(new BigDecimal(0.00000273836082624434948482434714767443784569828915819*1000));
					}
					break;
				case MAXI_SAVINGS:
					if (lastwithdrawl == -1 || nextinterest.after(fullinterest)){
						interest = amount.multiply(fivepercent);
					} else {
						interest = amount.multiply(pointonepercent);
					}
					break;
				default:
					interest = BigDecimal.ZERO;
					break;
			}
            transactions.add(new Transaction(interest,true,nextinterest));
			nextinterest.add(Calendar.DAY_OF_MONTH,1);
		}
	}

    public BigDecimal sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private BigDecimal checkIfTransactionsExist(boolean checkAll) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Transaction t: transactions)
            amount = amount.add(t.amount);
        return amount;
    }

	private int lastWithdrawl(){
		int lastwithdrawl = this.transactions.size() - 1;
		if (lastwithdrawl >= 0) {
			while (lastwithdrawl >= 0 && this.transactions.get(lastwithdrawl).amount.compareTo(BigDecimal.ZERO) == 1) {
				lastwithdrawl -= 1;
			}
		}
		return lastwithdrawl;
	}

    public int getAccountType() {
        return accountType;
    }
}
