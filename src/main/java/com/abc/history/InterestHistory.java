package com.abc.history;

import com.abc.rates.Interest;
import com.abc.rates.InterestPayment;
import com.abc.transactions.AbstractTransaction;
import com.abc.utilities.DateProvider;
import com.abc.utilities.Money;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vahizan on 18/08/2017.
 */
public class InterestHistory implements History{
    private ArrayList<InterestPayment> interestsPaid;

    public InterestHistory(){
        interestsPaid = new ArrayList<InterestPayment>();
    }
    public void addInterest(InterestPayment interest){
        interestsPaid.add(interest);
    }
    public String fullStatement() {
        return totalPayment(totalInterest());
    }
    public String periodStatement(Date dateAfter, Date dateBefore) {
        return totalPaymentInPeriod(totalInterestInPeriod(dateAfter, dateBefore));
    }
    public float totalInterestInPeriod(Date dateAfter, Date dateBefore){
        float totalPayment=0;
        for(InterestPayment payment: interestsPaid){
            addInformationInPeriod(totalPayment,payment,dateAfter,dateBefore);
        }
        return totalPayment;
    }
    public float totalInterest() {
        float totalPayment=0;
        for(InterestPayment payment: interestsPaid){
            Money interestPaid = payment.value();
            totalPayment+=interestPaid.amount();
        }
        return totalPayment;
    }
    private String totalPayment(float totalInterest){
        return String.valueOf(totalInterest);
    }
    private String totalPaymentInPeriod(float totalInterest){
        return String.valueOf(totalInterest);
    }
    private void addInformationInPeriod(float payment,InterestPayment interest,Date dateAfter,Date dateBefore){
        boolean correctPeriod=correctPeriod(interest,dateAfter,dateBefore);
        if(correctPeriod) {
            aggregateInterest(interest.value(),payment);
        }
    }
    private boolean correctPeriod(InterestPayment payment,Date dateAfter,Date dateBefore){
        String stringDate = payment.date();
        Date paymentDate = DateProvider.getInstance().stringToDate(stringDate);
        return (paymentDate.after(dateAfter) && paymentDate.before(dateBefore));
    }
    private void aggregateInterest(Money money,float payment){
         payment+=money.amount();
    }
}
