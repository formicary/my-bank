#Hans-Wai Chan changes

## Account.java changes

- Class into an abstract class for different types of Accounts to be extended.
- Changed withdraw method to include a check to make sure the account have enough money (sum of transactions > withdrawal amount)
- Changed interestEarned to be an abstract method.
- Added getTransaction and changed transactions to be private.
- Edited sumTransactions and checkIfTransactionsExist method to perform the correct task given their names.
- Added HasWithdrawnPast10days method to check if a withdrawal has taken place within the past 10 days.
- Added interestEarnedDaily to calculate the daily interest rate
- Added comments and documentation.

### CheckingAccount.java, SavingsAccount.java and MaxiSavingsAccount.java
 - Restructured how accounts are made. 
 - Added CheckingAccount.java, SavingsAccount.java and MaxiSavingsAccount.java which contains subclasses extends to Account.
 - Added unique interestEarned to each of the Accounts to their corresponding interest rate. 

## Bank.java changes

- Edited getFirstCustomer method to work as intended.
- Added comments and documentation.

## Customer.java changes

- Changed statementForAccount method to use getTransaction instead of accessing the public field directly.
- Added transferMoney method to transfer money between accounts. 
- Changed getStatement method so the returned string initialises  with empty string and not NULL.
- Added comments and documentation.
 
## DateProvider.java changes

- Added datePast method to get the date x number of days before now.
- Added comments and documentation. 

## Transaction.java changes

- Changed amount to be private and added getAmount method.
- Added getTransaction method which returns a clone of transactionDate.
- Added comments and documentation.

## BankTest.java changes

- Updated all tests to encompass the new structure with CheckingAccount, SavingsAccount and MaxiSavingsAccount.
- Added test for getFirstCustomer method.

## AccountTest.java changes

- Added tests to calculate interests for Checking, Savings and MaxiSavings accounts
- Added tests to calculate interests after two days for checking account.

## CustomerTest.java changes

- Added test for transferring money from one account to another.



