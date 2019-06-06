import java.util.UUID;

public class Checking_Account extends  Account {
    private double interestRate;
    private String accountType;

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Checking_Account{" +
                "interestRate=" + interestRate +
                ", accountType='" + accountType + '\'' +
                '}';
    }

    public Checking_Account(Customer customer, double amount, UUID accountId) {
        super(customer, amount, accountId);
        this.interestRate = interestRate;
        this.accountType = "Checking Account";
    }
}
