Programming Test
========

This is a dummy application to be used as part of a software development interview.

instructions
--------

* Treat this code as if you owned this application, do whatever you feel is necessary to make this your own.
* There are several deliberate design, code quality and test issues that should be identified and resolved.
* Below is a list of the current features supported by the application; as well as some additional features that have been requested by the business owner.
* In order to work on this take a fork into your own GitHub area; make whatever changes you feel are necessary and when you are satisfied submit back via a pull request. See details on GitHub's [Fork & Pull](https://help.github.com/articles/using-pull-requests) model
* The project uses maven to resolve dependencies however if you want to avoid maven configuration the only external JAR that's required is junit-4.11.
* Refactor and add features (from the below list) as you see fit; there is no need to add all the features in order to "complete" the exercise. Keep in mind that code quality is the critical measure and there should be an obvious focus on testing.
* You'll notice there is no database or UI; these are not needed - the exercise deliberately avoids these requirements.
* REMEMBER: this is YOUR code, make any changes you feel are necessary.
* You're welcome to spend as much time as you like; however it's anticipated that this should take about 2 hours.
* The code will be a representation of your work, so it's important that all the code--new and pre-existing--is how you want your work to be seen.  Please make sure that you are happy with ALL the code.

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
  * **Maxi-Savings accounts** have a rate of 2% for the first $1,000 then 5% for the next $1,000 then 10%
* A bank manager can get a report showing the list of customers and how many accounts they have
* A bank manager can get a report showing the total interest paid by the bank on all accounts

### Additional Features

* A customer can transfer between their accounts
* Change **Maxi-Savings accounts** to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
* Interest rates should accrue and compound daily (incl. weekends), rates above are per-annum


### SOLUTION

Project Structure:

Design:
-Define Banking Service Interface project definitions.  Delineate Admin/Operation APIs.  
-Organize code into packages to better organize and encapsulate features and limit Scope of APIs.
-Add Guava  lib
-UnCouple Pojo's from features implementations improving ability to isolate functionality, write unit tests, serialize data and promote code reuse. 
-Redefine Bank API to increase chances of maintaining a stable API/Reduce the need to edit/touch existing code as new features are requested. BankService Class could be extracted from BANK to maintain clearer BANK API.   
-Builder/Factory Classes added to support extensibility of future Account/Transaction Types.   Utilization of an Iterator/Visitor pattern additional Account/Customer reports while allowing adherence to DRY.

Bank:
-Flatten Account Storage into Table<Customer,Long,Account> a.k.a (Map<Customer,Map<Long,Account>>) to align with anticipated usage pattern (i.e. provide a mechanism of iterating over accounts without iterating over Customers).    Additionally removing Account from the Customer object reduces the chance that any serialization of Customer Objects inadvertently serialize their Account Information.
-TODO: Make Account Access Thread Safe.
-Iterator Pattern/Visitor Interface Could be Improved to Handle Exceptions when iterating.
-BankService Class could be extracted from BANK to maintain clearer BANK API.

Transaction/Customer/Account:  Add properties that Improve Class Utility(eg. capability to delineate Customers with the Same Name). 

Account:
Rather than subclass account (a pojo),Subclassing is done in the Externalized InterstRate Calculators 
ReportGenerator:
	Could Take in a "Formatter" Type object so that Addition Export Formats could be supported (XML/JSON,Binary,etc.).

Interest Rate Calculation 
-Orig. InterestEarned Calculation Calculated Interest on Final Balance... this is inconsistent with how Interest Rates are calculated.  Not clear if this was a simplification for the exercise or a deliberately introduced issue.  TODO:  Uncouple Interest Rate Calculator from Transaction... instead define a more generic ICashFlow Interface.
-Current Interface could be Improved so that it returns Interest Calculated during a time Range instead of assuming today.  


Implementation Optimizations:
-Added code to normalize Date...  Rather than return Date... better to wrap with Custom object(like BankDate)... and then change the DaysDiff API to take BANKDates.



Other areas of interest:
-Although the problem states Rates accrue daily... it does not specify when it is paid... current code could specify BankOp for adding Interest to Accounts
-Adding State to Objects (i.e. Account Balance)could  improve  Batch process  efficiency at the cost of having to add that manages potential object state synchronization issues.
-Definition of  Transaction Date needs to be addressed..current solution normalizes all Transaction Dates.
 -Compounding Formula assumes Rates are perannum.  Adding normalization method for Monthly/Semi-Annual and Continuous Rates improve the functionality.
-Bank Manager and Customer object extend from a User principally as a method of gating certain APIs. Better implementation would be for the methods to authenticate users without the subclassing of User.
-Explicit Account Read and Account Write Interfaces could be defined to protect against unintended object mutations.


Testing:

By Better Encapsulating features the ability improves the chances of being able to write cleaner unit tests. Supplementing the project
with a JMockit (or similar jar) provides increased ability for testing.

Although I have re-implemented  some of the original tests. Tests for the various interestCalculators and all of the BankOps are still required. Similarly tests could be put in place to ensure proper error handling when unexpected data is passed throught the IBankService Interface.  




