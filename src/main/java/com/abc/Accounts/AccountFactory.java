package main.java.com.abc.Accounts;

/*
 * Now you don't need to know the EXACTLY subclass to create an Account
 * For encap and decoupling mostly
 */
public class AccountFactory {
	public static AccountBase CreateAccount(AccountType type){
		switch(type){
			case Checking:
				return new CheckingAccount();
			case MaxiSavings:
				return new MaxiSavingsAccount();
			case Savings:
				return new SavingsAccount();
			default:
				return null;
		}
	}
}
