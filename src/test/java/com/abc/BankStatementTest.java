package com.abc;

import com.abc.entity.Bank;
import com.abc.entity.impl.BankImpl;
import com.abc.exception.InvalidBankException;
import com.abc.service.BankStatementService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankStatementTest {

    private static Bank bank;

    @Before
    public void setup(){
        bank = new BankImpl();
    }

    @Test(expected = InvalidBankException.class)
    public void nullBankThrowsException(){
        BankStatementService.bankCustomerSummary(null);
    }

    @Test
    public void zeroCustomersShowEmptyReport(){
        assertEquals("Bank statement with zero customers is not printing as expected", "Customer Summary", BankStatementService.bankCustomerSummary(bank));
    }

}
