package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {
 private static final double DOUBLE_DELTA = 1e-15;

 @Test
 public void testAccountCreationSuccess() {
  Account checkingAccount = new Account(0);
  assertTrue(checkingAccount instanceof Account);
 }

 @Test
 public void testAccountCreationFail() {
  try {
   Account checkingAccount = new Account(-1);
   assert(false);
  } catch (IllegalArgumentException e) {
   assert(true);
  }
 }

 @Test
 public void testDepositSucccess() {
  Account checkingAccount = new Account(0);
  try {
   checkingAccount.deposit(250);
   assert(true);
  } catch (IllegalArgumentException e) {
   assert(false);
  }
 }

 @Test
 public void testDepositFail() {
  Account checkingAccount = new Account(0);
  try {
   checkingAccount.deposit(-250);
   assert(false);
  } catch (IllegalArgumentException e) {
   assert(true);
  }
 }

 @Test
 public void testDepositAndSumSuccess() {
  Account checkingAccount = new Account(0);
  try {
   int amount1 = 250;
   int amount2 = 390;
   checkingAccount.deposit(amount1);
   checkingAccount.deposit(amount2);
   assertEquals(amount1 + amount2, checkingAccount.sumTransactions(), DOUBLE_DELTA);
  } catch (IllegalArgumentException e) {
   assert(false);
  }
 }

 @Test
 public void testWithdrawSuccess() {
  Account checkingAccount = new Account(0);
  try {
   checkingAccount.deposit(250);
   checkingAccount.withdraw(250);
   assert(true);
  } catch (IllegalArgumentException e) {
   assert(false);
  }
 }

 @Test
 public void testWithdrawFail() {
  Account checkingAccount = new Account(0);
  try {
   checkingAccount.withdraw(-250);
   assert(false);
  } catch (IllegalArgumentException e) {
   assert(true);
  }
 }

 @Test
 public void testWithdrawAndSumSuccess() {
  Account checkingAccount = new Account(0);
  try {
   int amount1 = 250;
   int amount2 = 390;
   checkingAccount.withdraw(amount1);
   checkingAccount.withdraw(amount2);
   assertEquals(-amount1 - amount2, checkingAccount.sumTransactions(), DOUBLE_DELTA);
  } catch (IllegalArgumentException e) {
   assert(false);
  }
 }

 @Test
 public void testInterestEarnedForCheckingAcc() {
  Account checkingAccount = new Account(0);
  try {
   checkingAccount.deposit(1000);
   assertEquals(1, checkingAccount.interestEarned(), DOUBLE_DELTA);
  } catch (IllegalArgumentException e) {
   assert(false);
  }
 }

 @Test
 public void testInterestEarnedForSavingsAcc() {
  Account savingsAccount = new Account(1);
  try {
   savingsAccount.deposit(2000);
   assertEquals(3, savingsAccount.interestEarned(), DOUBLE_DELTA);
  } catch (IllegalArgumentException e) {
   assert(false);
  }
 }

 @Test
 public void testInterestEarnedForMaxiSavingsAcc() {
  Account maxiAccount = new Account(2);
  try {
   maxiAccount.deposit(1000);
   assertEquals(50, maxiAccount.interestEarned(), DOUBLE_DELTA);
  } catch (IllegalArgumentException e) {
   assert(false);
  }
 }

}
