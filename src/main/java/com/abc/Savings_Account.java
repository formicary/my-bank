import java.util.UUID;

public class Savings_Account extends Account {
    private double interestRate;
    private String accountType;

    @Override
    public String toString() {
        return "Savings_Account{" +
                "interestRate=" + interestRate +
                ", accountType='" + accountType + '\'' +
                '}';
    }

    public Savings_Account(Customer customer, double amount, UUID accountId) {
        super(customer, amount, accountId);
        this.interestRate = 0;
        this.accountType = "Savings_Account";
    }

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
}
