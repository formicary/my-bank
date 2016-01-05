package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BankUtilTest {

  @Test
  public void formatWithOne() {
    assertEquals(BankUtil.format(1,"Test"),"1 Test");
  }

  @Test
  public void formatWithZero() {
    assertEquals(BankUtil.format(0,"Test"),"0 Tests");
  }
  
  @Test
  public void formatWithMany() {
    assertEquals(BankUtil.format(6,"Test"),"6 Tests");
    assertEquals(BankUtil.format(2,"Test"),"2 Tests");
  }
  
  //ensure -ea flag is set
  @Test(expected = AssertionError.class)
  public void formatWithNegative() {
    BankUtil.format(-6,"Test");
  }

  @Test
  public void toDollarsWithPositive() {
    assertEquals(BankUtil.toDollars(50),"$50.00");
    assertEquals(BankUtil.toDollars(50.056),"$50.06");
    assertEquals(BankUtil.toDollars(0),"$0.00");
    assertEquals(BankUtil.toDollars(0.0000000005),"$0.00");
  }
  
  @Test
  public void toDollarsWithNegative() {
    assertEquals(BankUtil.toDollars(-50),"$50.00");
    assertEquals(BankUtil.toDollars(-50.056),"$50.06");
    assertEquals(BankUtil.toDollars(-0.5),"$0.50");
    assertEquals(BankUtil.toDollars(-0.0000000005),"$0.00");
  }


  
}
