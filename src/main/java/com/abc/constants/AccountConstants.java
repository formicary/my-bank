package com.abc.constants;

/**
 * Created by vahizan on 07/08/2017.
 */
public final class AccountConstants {
    public static final String CHECKING="checking";
    public static final String MAXI_SAVINGS="maxi_savings";
    public static final String SAVINGS="savings";


    /*interest rate rules for maxi savings account*/
    public static final float MAXI_SAVINGS_INTEREST_BAND_ONE=2;
    public static final float MAXI_SAVINGS_INTEREST_BAND_TWO=5;
    public static final float MAXI_SAVINGS_INTEREST_BAND_THREE=10;

    /*interest rate rules for savings account*/
    public static final float SAVINGS_INTEREST_BAND_ONE=0.1f;
    public static final float SAVINGS_INTEREST_BAND_TWO=0.2f;

    /*interest rate rules for checkings account*/
    public static final float CHECKINGS_INTEREST_BAND_ONE=0.1f;

    /*Number of days in a year*/
    public static final int DAYS_IN_A_YEAR=365;

    /*WITHDRAW PERIOD BEFORE INTEREST RATE REDUCES*/
    public static final int WITHDRAW_PERIOD=10;

}
