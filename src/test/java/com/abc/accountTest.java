package mybank;
import java.util.Date;
import org.junit.Ignore;


public class accountTest {



public void DepositTest(){
	Account test = new Account(AccountType.CHECKING);
	test.deposit(300);
	double currentBalance = test.sumTransactions();
	assertEquals(300, currentBalance+300);
	
	}
public void WithdrawTest(){
	Account testWithdraw = new Account(AccountType.CHECKING);

	testWithdraw.deposit(400);
	testWithdraw. withdraw(300);
	double currentBalance2 = testWithdraw.sumTransactions();
	assertEquals(100, currentBalance2);
	}

private void sumOfTransactionsTest(){
	
	Account account = new Account(AccountType.SAVINGS);
	Account account2 = new Account(AccountType.CHECKING);
	
	account.deposit(300);
	account.deposit(700);
	account.withdraw(200);
	
	account2.deposit(200);
	account2.withdraw(199);
	
	assertEquals(800, account.sumTransactions());
	assertEquals(1, account2.sumTransactions());
	}


public void interestPaidToCheckingAccountTest(){
	 Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
	 DateProvider dateProvider = new DateProvider();
	 
	 
	 checkingAccount.deposit(500);
	 checkingAccount.deposit(1500);
	 int daysBetween =  Days.daysBetween(checkingAccount.transactions.get(0).transactionDate(), checkingAccount.transactions.get(1).transactionDate()).getDays();
	 Date currentDate = dateProvider.now();
	 int betweenCurrentToLast = Days.daysBetween(checkingAccount.transactions.get(1).transactionDate(), currentDate).getDays(); 
	 
	 double expectInterest = 500*(daysBetween/365)*0.001+1500*(betweenCurrentToLast/365)*0.001;
	 double checkingAccountInterest = checkingAccount.getcheckingInterest();
	 assertEquals(expectInterest, checkingAccountInterest);
	}


}





