abc-bank
========

A dummy application for a bank. Provides various functions of a retail bank.

Features
--------

* A customer can open an account
* A customer can deposit / withdraw funds from an account
* A customer can transfer between their accounts
* A customer can request a statement that shows transactions and totals for each of their accounts
* Different accounts have interest calculated in different ways
  * **Checking accounts** have a flat rate of 0.1%
  * **Savings accounts** have a rate of 0.1% for the first $1,000 then 0.2%
  * **Super-Savings accounts** have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
  * **Maxi-Savings accounts** have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
* A bank manager can get a report showing the list of customers and how many accounts they have
* A bank manager can get a report showing the total interest paid by the bank on all accounts

### Additional Features

* Interest rates should accrue and compound daily (incl. weekends), rates above are per-annum
