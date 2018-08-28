My-Bank
========

A mock banking application which supports common operations undertaken by a retail bank - 
i.e. the creation of customers, and three types of accounts (belonging to customers):  
    * **Checking accounts** have a flat rate of 0.1%  
    * **Savings accounts** have a rate of 0.1% for the first $1,000 then 0.2%  
    * **Maxi-Savings accounts** to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%  

Customers can also transfer funds between them. Funds are assumed to be in GBP but currency is not enforced in this system.
Reports can be generated in the console which shows:  
    * List of customers and how many accounts they have  
    * Total interest paid by the bank on all accounts

Note: This application does not have a user interface. Persistence is also not yet supported.

Author: Sameen Islam