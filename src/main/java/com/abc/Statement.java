// This class is create to have a better control over Statment over each account, for isntace, 
// Dailystatment, Monthly statment , etc ...
private class Statement {
    
    private List<Transaction> transactions;
    private int accountType; 

    public Statement(int accountType){
        this.transactions = new ArrayList<Transaction>();
        this.accountType = accountType;
    }


    private String statementForAccount(Account a) {

    }
}