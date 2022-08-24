Completed programming test by Dainius Cerniauskas
========
This is a university 3rd year project

Discussion
--------

* Currently the program doesn't allow for interest withdrawal, that can be easily implemented by calling interestEarned() at the end of sumTransactions(), however in my opinion it's not necessary for this dummy application.
* While the tests don't provide 100% coverage, some of the methods in my opinion don't need testing due to their simplicity and others get tested indirectly.

abc-bank
--------

A dummy application for a bank; should provide various functions of a retail bank.

### Current Features

* A customer can open an account
* A customer can deposit / withdraw funds from an account
* A customer can request a statement that shows transactions and totals for each of their accounts
* Different accounts have interest calculated in different ways
  * **Checking accounts** have a flat rate of 0.1%
  * **Savings accounts** have a rate of 0.1% for the first $1,000 then 0.2%
  * **Maxi-Savings accounts** have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
* A customer can transfer between their accounts
* Interest rates accrue and compound daily (incl. weekends), rates above are per-annum
* A bank manager can get a report showing the list of customers and how many accounts they have
* A bank manager can get a report showing the total interest paid by the bank on all accounts

