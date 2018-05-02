For testing purposes, account was opened a year ago and first deposit was just after that. Any other deposit or withdrawl happened a year later.

Account.java

1. Changed class Account.java to an abstract class
2. Changed variable transactions to private
3. Added a private balance variable
4. Added a protected account_holder variable
5. Added an protected account_opendate variable 
6. Added an latest_interestdate variable
7. Added an earned_interest variable
8. Changed the deposit method 
8.1Required update of the balance first - it will affect how the system calculate it's interest
9. Changed the withdraw method 
9.1 Required update of the balance first
9.2 Check whether there is enough balance in the account - no overdraft is implemented
10. Changed interestEarned method to abstract method
11. Created an updateBalance method
12. Created a getAccountBalance method
13. Created a getAccountHolder method
14. Created a getTransactions method
15. Created a getinterest method
16. Created a checkwithdrawlinpasttendays method

Bank.java

1. Added a checkifCustomerExist method
2. Modified getFirstCustomer method

Customer.java

1. Modified openAccount method
2. Modified totalInterestEarned method
3. Modified getStatement method
4. Modified statementForAccount method
5. Added an account_exist method
6. Added a transfer method

DateProvider.java

1. Added a past method

Transaction.java

1. Changed variable amount to private
2. Added a getamount method


BankTest.java

1. added a getfirstcustomer test
2. added a deposit/withdrawl test

