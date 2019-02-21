Changes Explained
====

General
----
* **Account refactored to Abstract Class** -- subclasses extending Account superclass were introduced
and some of the Account superclass functionality was overridden by each subclass.
* **String Building** -- although nothing was changed in the original implementation I believe that
neither _Customer_ nor _Bank_ should be responsible to provide this functionality. It could be better to shift this
responsibility to a _Utility Class_ for bigger projects. Also a _StringBuilder_ should have been used instead of string
concatenation for larger projects to minimize runtime complexity.
* **Removed unreachable and redundant code** -- _checkIfTransactionsExist_ was "merged" with _sumTransactions_. Also
get _getFirstCustomer_ was removed since there was no use for it.

Business Logic
----
* **UUID** -- Introduced a UUID field in the Account Class in order to identify uniquely each of the accounts created
and thus allow the creation of multiple accounts of each type. This change will allow easier look up of accounts through
a dictionary (HashMap) instead of storing the accounts in an ArrayList.
* **Negative Balance** -- It seems that the business logic was designed to allow withdrawals even if there were
insufficient funds in the Account. This functionality was removed from the base Class, and left as a responsibility
to each of the subclasses to provide their own overdraft logic.
* **Timeless Interest Rates** -- It seems that the Account's method _interestEarned_ does not take into account
any sort of time measure. It just calculates an interest rate the Customer will receive based on the total amount
in his account at the moment of calling the method. I would assume the real time management will have to be 
performed by a "Controller" class of some sort and it's beyond the scope of this exercise.

Additional Feature Implementation
----
* **Transfer Between Own Accounts** -- Since the addition of the UUID constant in the Account fields, it made more sense
to move the transfer responsibility to the Customer and not to the account. In this way the customer can 
now specify a from and to account using the UUID to id each account.
* **Maxi-Savings Account interest rate change** -- Although I regret the name, the private _isWithdrawal_ method checks
to see whether an account has had any withdrawals the last N number of days and returns a boolean. If there is
a withdrawal then the _interestEarned_ method of _MaxiSavingsAccount_ responds accordingly.
* **Daily Interest Rates** -- In response to the "Timeless Interest Rates" section above, the _interestEarned_
(and related methods) were refactored to take a "flag" parameter which specifies whether the caller wants to receive
the interest rate annually or daily. It will be the responsibility of a "Controller" class to call each method at the
appropriate real time and use the deposit() method to accumulate the interest for each account.

Unit Testing
----
* **Refactored** -- Tests were updated accordingly to accommodate the refactoring of various methods and the addition of
new features.
* **Additional Tests** -- A test class AccountsTest was added to test the implementation of both
the abstract superclass Account and the 3 subclasses that extend its functionality.
Nested classes were used to group together related tests as well as separate the test of each of the
subclasses.
* **JUnit5** -- please note that some of the test classes use JUnit 5.
* **Testing Dates** -- To ideally test dates the "public Date now() {return Calendar.getInstance().getTime();}" should be
substituted in some way to allow to set a fixed date during testing. Using a mock object of the 
DateProvider.getInstance will not solve the issue completely and so it was not used.