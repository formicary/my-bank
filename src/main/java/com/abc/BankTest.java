package com.abc;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankTest<assertEquals> {
    private static final double DOUBLE_DELTA = 0.001;
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", "James", "52 Abbey road", "07656261464", 30, "ASFA686ADG65");
        Account checkingAccount = new Checking_Account(john, 0, UUID.randomUUID());
        bank.addCustomer(john);

        assertEquals("com.abc.Customer Summary\n - John James (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", "James", "52 Abbey road", "07656261464", 30, "ASFA6ADG65");
        Account checkingAccount = new Checking_Account(bill, 0, UUID.randomUUID());
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestToBePaid(Account.InteType.YEARLY), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", "James", "52 Abbey road", "07656261464", 30, "ASFA6ADG65");
        Account savingsAccount = new Savings_Account(bill, 0, UUID.randomUUID());
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestToBePaid(Account.InteType.YEARLY), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Customer jack = new Customer("Jack", "James", "52 Abbey road", "07656261464", 30, "ASFA686AD");
        Account maxiSavingsAcoount = new Maxi_Savings_Account(jack, 0, UUID.randomUUID());
        bank.addCustomer(jack);

        maxiSavingsAcoount.deposit(3000.0);

        assertEquals(30.0, bank.totalInterestToBePaid(Account.InteType.YEARLY), DOUBLE_DELTA);
    }

    @Test
    public void Zero_Accounts_in_the_bank() {
    Bank bank = new Bank();
    assertEquals(0,bank.totalInterestToBePaid(Account.InteType.DAILY),DOUBLE_DELTA);
}


    @Test
    public void checkingAccountDaily() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", "James", "52 Abbey road", "07656261464", 30, "ASFA6ADG65");
        Account checkingAccount = new Checking_Account(bill, 0, UUID.randomUUID());
        //bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(Double.MIN_VALUE, bank.totalInterestToBePaid(Account.InteType.DAILY), DOUBLE_DELTA);
    }

    @Test
    public void savings_accountDaily() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", "James", "52 Abbey road", "07656261464", 30, "ASFA6ADG65");
        Account savingsAccount = new Savings_Account(bill, 0, UUID.randomUUID());
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);

        assertEquals(1.002, bank.totalInterestToBePaid(Account.InteType.DAILY), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_accountDaily() {
        Bank bank = new Bank();
        Customer jack = new Customer("Jack", "James", "52 Abbey road", "07656261464", 30, "ASFA686AD");
        Account maxiSavingsAcoount = new Maxi_Savings_Account(jack, 0, UUID.randomUUID());
        bank.addCustomer(jack);

        maxiSavingsAcoount.deposit(3000.0);

        assertEquals(0.082, bank.totalInterestToBePaid(Account.InteType.DAILY), DOUBLE_DELTA);
    }
    //com.abc.Customer does not exist on the list
    @Test
    public void CheckAddCustomer(){
            Bank bank = new Bank();
            Customer jack = new Customer("Jack", "James", "52 Abbey road", "07656261464", 30, "ASFA686AD");
            bank.addCustomer(jack);
            bank.getCustomers().equals(jack);
    }
    //com.abc.Customer already exists in the list
  @Test
    public  void AddCustomerWhoAlreadyExists(){

        Bank bank = new Bank();
        Customer jack = new Customer("Jack", "James", "52 Abbey road", "07656261464", 30, "ASFA686AD");
        bank.addCustomer(jack);
      Throwable exception = assertThrows(IllegalArgumentException.class, () -> bank.addCustomer(jack));
      assertEquals("com.abc.Customer already exists", exception.getMessage());

    }
    @Test
    public void deleteCustomerWhoDoesNotExist(){
        Bank bank = new Bank();
        Customer jack = new Customer("Jack", "James", "52 Abbey road", "07656261464", 30, "ASFA686AD");
        //bank.deleteCustomer(jack);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> bank.deleteCustomer(jack));
        assertEquals("The specified customer does not exist", exception.getMessage());
    }
    @Test
    public  void deleteCustomer(){
        Bank bank = new Bank();
        Customer jack = new Customer("Jack", "James", "52 Abbey road", "07656261464", 30, "ASFA686AD");
        bank.addCustomer(jack);
        bank.deleteCustomer(jack);
        assertEquals(new LinkedHashMap<>(),bank.getCustomers());
    }


    @Test
    public void CheckTransferOfInterest(){
        Bank bank = new Bank();
        Customer jack = new Customer("Jack", "James", "52 Abbey road", "07656261464", 30, "ASFA686AD");
        bank.addCustomer(jack);
        Account checkingAccount = new Checking_Account(jack, 1000, UUID.randomUUID());
        bank.transferInterestEarnedToEachCustomer();
          Double amount= jack.getAccounts().iterator().next().getAmount();
        assertEquals(1000.002,amount,DOUBLE_DELTA);

    }


}
