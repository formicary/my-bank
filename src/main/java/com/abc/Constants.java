package com.abc;

import com.abc.account_types.CheckingAccount;
import com.abc.account_types.SavingsAccount;

public class Constants {
    public static String GreaterThanZeroErrorMessage = "Amount must be greater than zero";

    public enum AccountTypes {
        CheckingAccount,
        SavingsAccount,
        MaxiSavingsAccount,
    }
}
