Danling Wu's Programming Test
========

This is a dummy application for part of a software development interview.

Design Issues
--------
* Used inheritance with Abstract class for Account, there are some general functions for all accounts, but different account types have different interest rates.  This means we don't need switch case, and easy add new account types.  If there are more differences in the account, then can start using interfaces, especially multiple inheritance situation.
* No date for transations, so added data parameter in constructor.
* Customer and Account need unique IDs, as there's no database, just used static variables to keep track an give a unique number.
* Added function to get transactions withing a date range.

Code Quality
--------
* Consistent indentation, variable names (camel case).
* Deleted unused functions.
* Deleted some not relevant comments.
* Use more descriptive function names.

Test Issues
--------
* Added test coverage for the Accounts.
* Added more tests for the other classes and functions.

Features
--------
* Added transfer between accounts.
* Added function to get transactions between a date range (inclusive).

Improvements
--------
* Can split Customer into Business and Personal (make Customer into interface/abstract), they might have different charges and services. 