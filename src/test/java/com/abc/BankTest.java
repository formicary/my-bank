package com.abc;

import com.abc.account.CheckingAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankTest {

  @Mock
  private Customer customerOne = new Customer("CustomerOne");
  @Mock
  private Customer customerTwo = new Customer("CustomerTwo");
  @Mock
  private Customer customerThree = new Customer("CustomerThree");

  @Test
  public void customerSummaryWithOneCustomer() {
    final Bank bank = new Bank();
    customerOne = new Customer("CustomerOne");
    customerOne.openAccount("id", new CheckingAccount());
    bank.addCustomer(customerOne);
    assertEquals("Customer Summary\n - CustomerOne (1 account)", bank.getCustomersSummary());
  }

  @Test
  public void customerSummaryWithMultipleCustomers() {
    final Bank bank = new Bank();
    customerOne = new Customer("CustomerOne");
    customerTwo = new Customer("CustomerTwo");
    customerThree = new Customer("CustomerThree");
    bank.addCustomer(customerOne);
    bank.addCustomer(customerTwo);
    bank.addCustomer(customerThree);
    assertEquals(
        "Customer Summary\n - CustomerOne (0 accounts)"
            + "\n - CustomerTwo (0 accounts)"
            + "\n - CustomerThree (0 accounts)",
        bank.getCustomersSummary());
  }

  @Test
  public void getFirstCustomerName() {
    final Bank bank = new Bank();
    customerOne = new Customer("CustomerOne");
    customerTwo = new Customer("CustomerTwo");
    customerThree = new Customer("CustomerThree");
    bank.addCustomer(customerOne);
    bank.addCustomer(customerTwo);
    bank.addCustomer(customerThree);
    assertEquals(customerOne.getName(), bank.getFirstCustomerName());
  }

  @Test
  public void getTotalInterestPaid() {
    final Bank bank = new Bank();
    bank.addCustomer(customerOne);
    bank.addCustomer(customerTwo);
    bank.addCustomer(customerThree);
    when(customerOne.getTotalInterestEarned()).thenReturn(BigDecimal.ZERO);
    when(customerTwo.getTotalInterestEarned()).thenReturn(BigDecimal.ONE);
    when(customerThree.getTotalInterestEarned()).thenReturn(BigDecimal.TEN);
    assertEquals(BigDecimal.valueOf(11), bank.getTotalInterestPaid());
  }
}
