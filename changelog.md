## Jay Tserkezie Changelog

Account Changes:

- Changed transactions to private.

- Added getTransactions method.

- Changed the deposit method to return true if sucessful.

- Changed the withdraw method to return true if sucessful. Also added a check to make sure the account has enough money before withdrawing.

- Updated interestEarned method to include a case for CHECKING account in the switch statement, removed the unused case, and changed the default to throw an exception if the account type cannot be determined.

- Updated InterestEarned to compute the interest for the new, updated MAXI_SAVINGS account.

- Reworked the sumTransactions and checkIfTransactionsExist methods to more appropriately perform their requred functions.

- Added a method to give the date ten days ago.

- Added a method to find the most recent withdrawal.

- Added a method to deposit the daily interest.

- Added documentation.




Bank Changes:

- Updated getFirstCustomer method.

- Added documentation.




Customer Changes:

- Added immutabibity to the customer name in the constructor and updated the getName method to be more defensive.

- Updated statementForAccount method to use getTransactions method rather than directly accessing the previously public field.

- Changed the getStatement method to initialise an empty string rather than a null.

- Added a method to allow a customer to transfer money between accounts.

- Added documentation.




Transaction Changes:

- Changed amount to private and added getter method.

- Added getter method for transaction date which returns a clone of the object.

- Added documentation.




DateProvider Changes:

- Added documentation.




BankTest Changes:

- Added tests for the daily interest method.

- Added test for the get first customer method.




CustomerTest Changes:

- Updated the test three accounts method.

- Added a test for transferring money between accounts.


