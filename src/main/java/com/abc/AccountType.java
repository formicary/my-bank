package com.abc;

public enum AccountType {
    CHECKING("Checking") {
        @Override
        public double calculateInterestEarned(double balance) {
            return balance * 0.001;
        }
    }, SAVINGS("Savings") {
        @Override
        public double calculateInterestEarned(double balance) {
            if (balance <= 1000) {
                return balance * 0.001;
            }
            return 1 + (balance - 1000) * 0.002;
        }
    }, MAXI_SAVINGS("Maxi Savings") {
        @Override
        public double calculateInterestEarned(double balance) {
            if (balance <= 1000) {
                return balance * 0.02;
            }
            if (balance <= 2000) {
                return 20 + (balance - 1000) * 0.05;
            }
            return 70 + (balance - 2000) * 0.1;
        }
    };

    public final String readableName;

    AccountType(final String readableName) {
        this.readableName = readableName;
    }

    public abstract double calculateInterestEarned(double balance);
}
