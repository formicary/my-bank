package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private Date TD;  
    private final Account targetACC;
    public final double AMT;
    public static final int DEP = 0;
    public static final int WD = 1;
    public static final int TF = 2;
    private final int TFType;
    
    public Transaction(double AMT, int TFType) { //used to determine the type of transaction used 
      this.AMT = AMT;
      this.TD = DateProvider.getInstance().now();
      this.TFType = TFType;
      this.targetACC = null;  
    }
    
    public Transaction(double AMT, Account ACC){ //used to transfer funds from one account to the next 
      this.AMT = AMT;
      this.TFType = TF;
      this.TD = DateProvider.getInstance().now();
      this.targetACC = ACC;
      
    }
    
    public Date getTD () {
        return TD;
    } 
    
    public double getAM(){
        return this.AMT;
    }
    
    public int getTATtype(){
        return TFType;
    }
    
    public Account getTargetACC(){
    return targetACC;
} 

    

}
