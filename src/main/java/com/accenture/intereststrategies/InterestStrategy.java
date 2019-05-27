package com.accenture.intereststrategies;

import com.accenture.Transaction;
import com.accenture.DollarAmount;

import java.util.List;

public interface InterestStrategy {

    DollarAmount getInterest(List<Transaction> transactions);

}
