package com.abc;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author R. Fei
 */
class AccountChecking extends testAccount{
    
    private InterestCalculator calcInterest;
    
    AccountChecking(){
        this.accountType = AccountsEnum.CHECKING;    
    }
    
    @Override
    double interestEarned(){
        this.calcInterest = 
               new InterestCalculator(this.accountType, this.getBalancesList());
        if ( null != calcInterest)
            return calcInterest.getInterest();
        else
            return 0.0;
    };
/*******************************************************************************
* Nested class for interestEarned() method.
*******************************************************************************/    
private class InterestCalculator {
    
    private final InterestRateProvider InterestRate;
    private final List<List<Double>> InterestInfo;
        
    private final Date Current = new Date();
    private final int numOfDays = DaysOfYear();
    private final AccountsEnum accountType;
    private final List<Transaction> BalanceList;
    
    private InterestCalculator(AccountsEnum accountType, List<Transaction> Balances){
        this.accountType = accountType;
        InterestRate = new InterestRateProvider(accountType);
        this.InterestInfo = InterestRate.getInterestInfo();
        this.BalanceList = Balances;     
    }
    
    double getInterest(){
        switch (accountType){
            case MAXI_SAVINGS:
                return this.MaxiSavings(BalanceList, Current);
            default:
                return this.CheckingSavings(BalanceList, Current);
        }
    }    
    private boolean validInterestInfo(List<List<Double>> InterestInfo){
        boolean valid = false;
        if(null != InterestInfo){
            if(InterestInfo.size() == 2){
                if(null != InterestInfo.get(0) && null != InterestInfo.get(1)){
                    if (0 != InterestInfo.get(0).size() &&
                        0 != InterestInfo.get(1).size() &&
                        InterestInfo.get(0).size()==InterestInfo.get(1).size())
                        valid = true;
                }
            }
        }
        return valid;
    }
    /***************************************************************************
     * Rules of Calculating the interests of Checking/Savings Account.
     * @param Balances (Lists, updated when new transaction happen)
     * @param Current  (The date of calculation)
     * @return Total Interest Earned from start date of Balances to Current
     * Calculate accurred interest according to the stepped interest rates:
     * (Annual Interest Rate = r)
     * Dates     Balances  -->       Accured Interest (I)
     *   D1        B1      -->       I1 = B1 * (D2-D1) * r
     *   D2        B2      -->       I2 = B2 * (D3-D2) * r + I1
     *   D3        B3      -->       I  = B3 * (D4-D3) * r + I2 (end of list)
     *   D4(Now)                     (return I / 365) 
     **************************************************************************/
    private double CheckingSavings(List<Transaction> Balances, Date Current){
        if( !validInterestInfo(this.InterestInfo) ){
            throw new IllegalArgumentException
                ("Invalid Interest Information:\n"+
                 "InterestInfo should contain 2 Lists with equal length\n"+
                 "The first list contains the threshold (balances) affecting interest rate\n"+
                 "The second list contains relevant interest rates with regard to the threshold");
        }
        else{
            //Calculate interest when InterestInfo Lists are correctly provided
            if (null != Balances && !Balances.isEmpty()){
                double accureInterest = 0.0;
                int i = 0;
                //In above example, following loop calculates I1, I2 and I
                while(i < Balances.size()){
                    Date dStart = Balances.get(i).transactionDate;
                    Date dEnd = i < Balances.size()-1 ? 
                                    Balances.get(i+1).transactionDate : Current;
                    int diffDays = DatesBetween(dStart, dEnd);
                    accureInterest += diffDays *
                                   annualCheckingSaving(Balances.get(i).amount);
                    i++;
                }
                return accureInterest / numOfDays;
            }
            else{
                return 0.0;
            }
        }
    }
        /***********************************************************************
         * Calculate interest based on stepped Interest Rate.
         * (Balance = B, Annual Stepped Interest Rate: r1, r2, r3)
         * Money   Interest    Balance       -->  Action                    Line   
         *    0      r1                                                           
         * 1000      r2     B in [0, 1000]   -->  return B * r1               |0
         *                  B > 1000         -->  I1 = 1000 * r1              |1
         * 2000      r3     B in [1000,2000] -->  return (B-1000) * r2 + I1   |2
         *                  B > 2000         -->  I2 = (2000-1000) * r2 + I1  |3
         * (End of List)                     -->  return (B-2000) * r3 + I2   |4
         **********************************************************************/
        private double annualCheckingSaving(double Balance){
            if( validInterestInfo(this.InterestInfo) ){
                
                List<Double> BalanceInfo = InterestInfo.get(0);
                List<Double> InterestList = InterestInfo.get(1);

                double result=0.0;
                int i=1;
                // In above comments: Do Line 1 or Line 3
                while (i < InterestList.size()){
                    if(Balance > BalanceInfo.get(i)){
                    //According to above comments: Do Line 1 or Line 3, ...  
                        result += (BalanceInfo.get(i)-BalanceInfo.get(i-1))*
                                                          InterestList.get(i-1);
                        i++;
                    }
                    else 
                    //End the loop and do Line 0, 2 or 4 based on the value of i
                        break;
                }
                //Do Line 0(i=1), Line 2(i=2) or Line 4(i=3), ...
                result += (Balance-BalanceInfo.get(i-1))*InterestList.get(i-1);
                return result;
            }
            else
                return 0.0;
        }
    /***************************************************************************
     * Rules of Calculating the interests of Maxi Saving Account.
     * @param Balances (Lists, updated when new transaction happen)
     * @param Current  (The date of calculation)
     * @return Total Interest Earned from start date of Balances to Current
     
     * Dw = Date of last withdraw, Df = periods of calculation
     * Equivalent Interest Rate: r(Dw, Df)
     * See: equivalentMaxiRate(int diffActiveWithdraw, int diffActiveCurrent)
     * Dates     Balances  -->     Accured Interest (I)
     *   D1        B1      -->       I1 = B1 * r(Dw, D2-D1)                      
     *   D2        B2      -->       I2 = B2 * r(Dw, D3-D2) + I1    (update Dw)
     *   D3        B3      -->       I  = B3 * r(Dw, D4-D3) + I2    (update Dw)
     *   D4(Now)                     (return I / 365)
    **************************************************************************/
    private double MaxiSavings(List<Transaction> Balances, Date Current){
        if( !validInterestInfo(this.InterestInfo) ){
            throw new IllegalArgumentException
               ("Invalid Interest Information:\n"+
                "InterestInfo should contain 2 Lists with equal length.\n"+
                "1st list contains the threshold (days) affecting interest rate;\n"+
                "2nd list contains relevant interest rates with regard to the threshold.\n");
        }else{   
        //Calculate interest when InterestInfo Lists are correctly provided
        if ( null != Balances && !Balances.isEmpty()){
            double accureInterest = 0.0;
            Date lastWithdraw = Balances.get(0).transactionDate;
            int i = 0;
            //According to above comments: calculate I1, I2, I
            while (i < Balances.size()){
                double amount = Balances.get(i).amount;
                
                Date lastActive = Balances.get(i).transactionDate;
                //Update the date of last withdraw: Dw
                if(i > 0){
                    if(Balances.get(i).amount<Balances.get(i-1).amount){
                        lastWithdraw = Balances.get(i).transactionDate;
                    }
                }               
                Date tCurrent = i < Balances.size()-1 ? 
                                    Balances.get(i+1).transactionDate : Current;
                
                int diffActiveWithdraw = DatesBetween(lastWithdraw, lastActive);
                int diffActiveCurrent = DatesBetween(lastActive, tCurrent);
                accureInterest += amount * equivalentMaxiRate(
                                         diffActiveWithdraw, diffActiveCurrent);
                i++;
            }
            return accureInterest / numOfDays; // I / 365(366)
        }
        else
            return 0.0;
        
        }
    }
        /* ********************************************************************* 
         * Calculate the equivalent interest of the periods of calculation
         * (Annual Interest Rate: r1, r2, r3)
         * Periods of calculation: D = Dn(current date) - Da(date of balance)
         * No.days from Da to Dw (date of last withdraw (Dw < Da) ) D' = Da - Dw
         * Days   Interest    D'        D        -->  Actions               Line   
         *    0      r1                                                           
         *   10      r2     D'<=10    D < 10-D'  -->  return r1 * D          | 0
         *                            D >=10-D'  -->  R1 = r1 * (10-D'),     | 1
         *                                            D1 = D - (10-D')       | 2
         *   20      r3     D'<=10    D1< 20-10  -->  return r2 * D1 + R1    | 3         
         *                            D1>=20-10  -->  R2 = r2 * (20-10)      | 4
         *                                            D2 = D1 - (20-10)      | 5
         *               10<D'<=20    D < 20-D'  -->  return r2 * D          | 6
         *                            D >=20-D'  -->  R1'= r2 * (20-D'),     | 7
         *                                            D1'= D - (20-D')       | 8
         *(End of List)     D'<=10    D2>0       -->  return r3 * D2 + R2    | 9
         *               10<D'<=20    D1>0       -->  return r3 * D1'+ R1'   |10 
         *                  D'>20     D >0       -->  return r3 * D          |11
         **********************************************************************/
        private double equivalentMaxiRate
                                (int diffActiveWithdraw, int diffActiveCurrent){
            if( validInterestInfo(this.InterestInfo) ){
                
                double result = 0.0;
                if(diffActiveCurrent>0){

                    List<Double> DatesList = InterestInfo.get(0);
                    List<Double> InterestList = InterestInfo.get(1);
                    int i=1;
                    double daysRemain = diffActiveCurrent;
                    while (i < InterestList.size()){
                        if(daysRemain > 0){
                 //According to comments: Do Line 0, 1 or Line 3, 4 or Line 6, 7
                            if(diffActiveWithdraw <= DatesList.get(i)){
                                double ndays = DatesList.get(i) - 
                                    max(diffActiveWithdraw, DatesList.get(i-1));
                                double diffdays = min(daysRemain, ndays);
                                result += diffdays * InterestList.get(i-1);
                                //According to comments: Do Line 2, 5 or 8
                                daysRemain -= diffdays;
                            }
                            i++;
                        }
                        else
                            break;
                    }
                    // In above comments: Do Line 9, 10 or 11
                    if(daysRemain > 0){
                        result += daysRemain * InterestList.get(i-1);
                    }   
                }
                return result;
            }
            else
                return 0.0;
        }                               
    /* *************************************************************************
     * Calculate dates between two dates: 
     * 2015-Feb-01 - 2015-Jan-01 = 31 days */
    private int DatesBetween(Date dFrom, Date dTo){
        long vFrom = dFrom.getTime();
        long vTo = dTo.getTime();
        long diff = vTo - vFrom;
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        return diffDays;
    }
    
    private int DaysOfYear(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(Current);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }    
            
}//end of nested class InterestCalculator
    
}//end of class AccountChecking
