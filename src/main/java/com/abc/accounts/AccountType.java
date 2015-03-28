package com.abc.accounts;

 /**
 * Represents an account type. Different accounts have interest calculated in different ways.
 * <code>CHECKING</code> accounts have a flat rate of 0.1%.
 * <code>SAVINGS</code> accounts have a rate of 0.1% for the first $1,000 then 0.2%.
 * <code>MAXISAVINGS</code> have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%.
 */

public enum AccountType {

	CHECKING {
		@Override
		public String toString() {
			return "Checking Account";
		}
	},
	SAVINGS {
		@Override
		public String toString() {
			return "Savings Account";
		}
	},
	MAXISAVINGS {
		@Override
		public String toString() {
			return "Maxi Savings Account";
		}
	};
	
	
}
