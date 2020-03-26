/**
 * 
 */
package com.accenture.mybank.utils;

/**
 * @author anusha.a.avuthu
 *
 */
public class CommonUtil {
	public static int noOfDays=365;
	public static String maxiSavingsAccountType="Maxi Savings Account";
	public static String checkingAccountType="Checking Account";
	public static String savingsAccountType="Savings Account";
	public static double savingsAccountFirstThousandInterestRate=0.001;
	public static double savingsAccountNextThousandInterestRate=0.002;
	public static double maxiSavingsAccountNoWithdrawalsInterestRate=0.05;
	public static double maxiSavingsAccountInterestRate=0.001;
	public static double checkingAccountInterestRate=0.001;
	
	public static boolean isGreaterThanThousand(double number){
        return number > 1000;
    }
}
