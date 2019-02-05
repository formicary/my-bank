
package com.abc;


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
        @Override
        public double InterestEarned(double amount ,int daysSinceWithdrawal) {
      
            if(daysSinceWithdrawal >= 10)
                return amount * 0.05;
            else
                return amount * 0.001;
                
        }
        
        

    };

    public abstract double InterestEarned(double amount, int daysSinceWithdrawal);
}
