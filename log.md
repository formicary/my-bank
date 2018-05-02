For testing purposes, account was opened a year ago and first deposit was just after that. Any other deposit or withdrawl happened a year later.

Account.java

1. Changed class Account.java to an abstract class
2. Changed variable transactions to private
3. Added a private balance variable
4. Added a protected account_holder variable
5. Added a private final account_opendate variable 
6. Added a protected latest_interestdate variable
7. Added a private earned_interest variable
8. Removed its int(CHECKING, SAVINGS, MAXI_SAVINGS)
9. Removed constructor Account
10. Changed the deposit method 
10.1 Required update of the balance first - it will affect how the system calculate it's interest
11. Changed the withdraw method 
11.1 Required update of the balance first
11.2 Check whether there is enough balance in the account - no overdraft is implemented
12. Changed interestEarned method to abstract method
13. Created an updateBalance method
14. Created a getAccountBalance method
15. Created a getAccountHolder method
16. Created a getTransactions method
17. Created a getinterest method
18. Created a checkwithdrawlinpasttendays method

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

