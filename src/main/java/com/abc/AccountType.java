
package com.abc;

// File added so account types can be custom and scalable.
// Abstract method takes in days since withdrawal incase other types of accounts
// want to take advantage of that feature, or the day changes from just 10.

public enum AccountType {
    CHECKING {
        @Override
        public double InterestEarned(double amount ,int daysSinceWithdrawal) {
          return amount * 0.001;
        }

    },
    SAVINGS {
        @Override
        public double InterestEarned(double amount ,int daysSinceWithdrawal) {
            if (amount <= 1000) {
                return amount * 0.001;
            } else {
                return 1 + (amount - 1000) * 0.002;
            }
        }
    },
    MAXI_SAVINGS {
        
        private static final int DAYS_UNTIL_WITHDRAWAL_INCREASE = 10;
        @Override
        public double InterestEarned(double amount ,int daysSinceWithdrawal) {
      
            if(daysSinceWithdrawal >= DAYS_UNTIL_WITHDRAWAL_INCREASE)
                return amount * 0.05;
            else
                return amount * 0.001;
                
        }
        
        

    };

    public abstract double InterestEarned(double amount, int daysSinceWithdrawal);
}
