package com.abc;

public class MaxiSavingsAccount extends Account {
    public static double normalIR = 0.001;
    public static double maxiSavingsIR = 0.05;
    public static int daysThreshold = 10;
    public static long MILLISECONDS_PER_DAY = 86400000;

    public MaxiSavingsAccount() {
        super(Accounts.MAXI_SAVINGS);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        Transaction lastT =lastWithdrawal();
        if (amount <= 0) {
            return 0;
        } else if (lastT != null) {
            long diff = DateProvider.getInstance().now().getTime() - lastT.getTransactionDate().getTime();
            double milliToTenDayRatio = (double) diff / (double) (MILLISECONDS_PER_DAY * daysThreshold);
            if (milliToTenDayRatio < 1.0) {
                return amount * normalIR;
            }
        }
        return amount * maxiSavingsIR;
    }
}
