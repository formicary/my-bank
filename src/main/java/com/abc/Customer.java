import javax.print.DocFlavor;
import java.util.*;

import static java.lang.Math.abs;

/*This class implements a customer of the bank.
Certain assumptions have been made about the attributes that each customer should have.
The choice is subjective and entirely depends on each bank but common standards have been taken
into account.
A linkedHashSet have been used instead of an ArrayList because the Set does not accept duplicates
meaning that eliminates the possibility for a customer to have identical accounts without the need of any additional checking.

 */

public class Customer implements  Comparable <Customer>{
    private String firstname;
    private String surname;
    private LinkedHashSet<Account> accounts;
    private String address;
    private String phoneNumber;
    private int age;
    private String nationalIdNumber;

    public Customer(String firstname, String surname, String address, String phoneNumber, int age, String nationalIdNumber) {
        checkCustomerAddress(address);
        checkCustomeFirstname(firstname);
        checkCustomerIdNumber(nationalIdNumber);
        checkCustomerSurname(surname);
        chekcCustomerAge(age);
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.nationalIdNumber = nationalIdNumber;
        this.accounts =  new LinkedHashSet<>();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(LinkedHashSet<Account> accounts) {
        this.accounts = accounts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }
//        this.accounts = new ArrayList<Account>();
//    }

    public String getName() {
        return firstname + " " + surname;
    }

    public void openAccount(Account account) {
        if(accounts.contains(account)){
            throw new IllegalArgumentException("account already exists");
        }
        accounts.add(account);
        System.out.println("Account has been added to the Customer's account set successfully");
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
// calculates the daily interest earned for a customer
    public double totalInterestEarnedDaily() {
        return accounts.stream().mapToDouble(Account::interestCalculationDaily).sum();
    }
    public double totalInterestEarnedYearly(){
        return accounts.stream().mapToDouble(Account::interestCalculationYearly).sum();
    }
//A separate method to transfer the interest to a customer's account
    public void transferInterestEarnedOfCustomer(){
        accounts.stream().forEach(Account::transferInterestEarnedDaily);
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + getFirstname() + " " + getSurname() + "\n";
        double total = 0.0;
        for(Account ac : accounts) {
            statement += "\n" + statementForAccount(ac) + "\n";
            total += ac.getAmount();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
                if(a instanceof  Checking_Account) {
                    s += "Checking Account\n";
                }else if(a instanceof  Savings_Account) {
                    s += "Savings Account\n";
                }else {
                    s += "Maxi Savings Account\n";
                }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            if(t.getTransactionType().equals(Transaction.TranType.WITHDRAW)) {
                s += "  " + "withdrawal" + " " + toDollars(t.amount) + "\n";
            }else if(t.getTransactionType().equals(Transaction.TranType.DEPOSIT)){
                s += "  " + "deposit" + " " + toDollars(t.amount) + "\n";
            }else {
                s += "  " + "interest" + " " + toDollars(t.amount) + "\n";
            }
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

// a method which transfer an amount from one account to another.
    //Both accounts should belong to the same customer
    //The method can easily be changed to perform a transfer from accounts which do not
    //belong to the same customer but this was a constrain of the additional features.
    public synchronized void transfer(Account account1, Account account2, Double amount){
        if(accounts.contains(account1)&&accounts.contains(account2)){
            if(amount > 0 && amount <= account1.getAmount()) {
                account1.withdraw(amount);
                account2.deposit(amount);
                System.out.println("Transfer was successful, amount has been transferred");
            }else{
                throw new IllegalArgumentException("Insufficient funds,transfer failed, try again");
            }

        }else{
            throw new IllegalArgumentException("One or more accounts do not belong to this customer" + "\n" + "Transfer failed, try again");
        }
    }

//An assumption has been made that each customer should have a unique national id number
    //The national id number is used as a unique key to identify a customer so
    //the compareTo method is overridden to accommodate this change.
    @Override
    public int compareTo(Customer o) {
        if(this.getNationalIdNumber().compareTo(o.getNationalIdNumber())==0) {
            return 0;
        }
        return  1;
    }
    private void chekcCustomerAge(int age) {
        if (age < 18 || age > 130 ) {
            throw new IllegalArgumentException("Customer must be over 18 and less than 130 years old");
        }
    }

    private void checkCustomerSurname(String surname) {
        if (surname == null || surname.length() <= 1 || surname.length() > 40)
            throw new IllegalArgumentException("Surname must be greater than one and less than 40");
    }

    private void checkCustomerIdNumber(String nationalIdNumber) {
        if (nationalIdNumber == null || nationalIdNumber.length()<=1)
            throw new IllegalArgumentException("National id number should be longer than one character and not null");
    }
    private void checkCustomeFirstname(String firstname) {
        if (firstname == null || firstname.length()<=1)
            throw new IllegalArgumentException("Name should be longer than one character and not null");
    }

    private void checkCustomerAddress(String address) {
        if (address == null || address.length()<=1)
            throw new IllegalArgumentException("Address should be longer than one character and not null");
    }

}
