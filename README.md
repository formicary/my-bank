### Additional Features

* A customer can transfer between their accounts

To transfer between accounts I have created a "transfer" function that takes an account to transfer to and an amount as a parameter
The function will then withdraw money from the current account and deposit it into the account passed within the function

* Change **Maxi-Savings accounts** to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%

To do this I created a function "checkdayspassed" which checks how many days have passed between the current date and the last date in which a withdrawal was made. If the value returned is more than 10 the program sets the interest rate to 5% and starts calculations. If not the program assumes the default 0.01%

* Interest rates should accrue and compound daily (incl. weekends), rates above are per-annum

To do this I created a boolean named last deposit. If the last action from the user was a withdrawal then to calculate the daily interest, the number of days from the current day to the last day of withdrawal are calculated and that value is used to calculate the daily compound accrute interest. Else it is assumed the last action from the user is a deposit and so the number of days from the current day to the last deposit are calculated and each interest calculation makes use of that value when calculating the daily compound accrute interest. All original interest rates have also been divided by 365 to calculate the daily interest rate.
