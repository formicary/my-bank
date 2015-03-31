#Solution to Formicary Programming Test
In the current design, five main issues are identified:

1.	Methods for interest calculation are encapsulated in the Account object. If interest rates are changed or the bank would like to change the rule of interest calculation, Account object will be affected;

2.	Printing methods are embedded as methods in objects of Transaction, Customer and Bank. This may cause problem if the current application need to communicate with outside printing objects;

3.	The accessibility of several functions and attributes, List<Transaction> of Account for example, should be modified;

4.	Exception handlers and rules for empty List<> should be added to several methods, for example, how to handle overdraft when withdraw money and how to print empty transaction / account lists;

5.	In the test file, not all occurrences are tested, for example, the interest calculation of savings and maxi savings account with other balances are not tested.

To solve the above listed problems:

1.	In my design, types of accounts are encapsulated in AccountsEnum providing type information for relevant class. Objects InterestCalculator and InterestRateProvider are introduced for calculate the interest of different types of accounts, where InterestRateProvider provide interest rates required by InterestCalculator:

    a)	Considering InterestCalculator is designed only for calculating accumulated interest with stepped interest rates,            this object is encapsulated inside the object of Checking Account (AccountChecking) which extends the base vanilla           account.
    
    b)	According to current rules of interest calculation, the format of interest information for Savings and Maxi Savings          account is the same as it is of Checking account, in my design, Savings (AccountSavings) and Maxi Savings                    (AccountMaxiSavings) accounts are designed as the extension of Checking Account. InterestCalculator provides relevant         functions for the interest calculation of different types of accounts.
    
    c)	Furthermore, in my design, general forms of functions are provided for calculating interest with stepped interest            rates (steps can be larger than 1 and 2).

2.	Objects related printing information is encapsulated as nested objects within relevant objects. For Account, this is PrintTransactions; for Customer, it is PrintStatements, for Bank, it is is PrintCustomerSummary. An interface of printer (Printer) is provided to communicate printing information between objects and the outside world. Shared formatting methods (toDollars and format) are included in a public object Formatter.

3.	In my design, the accessibilities of methods affecting inner information stored in types of Transaction, Account, Customer and Bank are set as package private or private.

4.	Necessary examinations for dealing null pointer, empty List<> and overdraft are added;

5.	More tests are generated considering more cases including exceptions.

6.	To test the correctness of the daily accumulated interest calculation, testAccount is extended from Account providing methods to add Transactions with manually selected Date. Checking Account is then designed as an extension of testAccount.
