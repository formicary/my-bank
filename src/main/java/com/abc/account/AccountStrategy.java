package com.abc.account;

import java.util.Date;

public interface AccountStrategy {

    double interestEarned(double amount, Date dateOfLastWithdrawal);
}
