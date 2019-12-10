package com.abc.shared;

public class Constants {
    public static String GreaterThanZeroErrorMessage = "Amount must be greater than zero";
    public static String UnexpectedAccountTypeErrorMessage = "Unexpected account type!";

    public enum AccountTypes {
        CheckingAccount,
        SavingsAccount,
        MaxiSavingsAccount,
    }
}
