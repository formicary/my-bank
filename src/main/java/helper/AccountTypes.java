package helper;

// An useful interface class that allows the below constant values to be accessed in other classes.
// Saves code space by not forcing the Account class to define them, and can be used in Customer class.
public interface AccountTypes {

	int CHECKING = 0;
	int SAVINGS = 1;
	int MAXI_SAVINGS = 2;

}
