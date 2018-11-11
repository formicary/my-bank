package src.main.java.com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * Write a description of class test here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class logicTesting
{
    public void dateTest(){
        Date test = new Date(0);
        System.out.println(test);
        Date test2 = DateProvider.getInstance().now();
        System.out.println(test2);
        long difference = test2.getTime()-test.getTime();
        int days = (int) (difference / (1000*60*60*24));
        System.out.println(difference); System.out.println(days);
    }
    public void interestTest(){
        int days = 730;
        int years = (int) days/365;
        
        double start = 100;
        double flat = 0.365; //i.e. flat rate
        double flatInterest = start + (start * flat * years);
        System.out.println("Amount after a year = "+ flatInterest);
        System.out.println("Total profit = "+(start * flat * years));
        
        double dailyInterest = flat/365;
        double compoundInterest = start;
        double temp = 0.0;
        for (int i = 0; i<days; i++){
            temp += compoundInterest*dailyInterest;
            compoundInterest += (compoundInterest*dailyInterest);
        }
        System.out.println("Daily compound interest = "+compoundInterest);
        System.out.println("Total profit = "+temp);
    }
    public void checking(){
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        for (int i = 0; i<11; i++){
            System.out.println(bank.totalInterestPaidTestVer(i));
        }
    }
}
