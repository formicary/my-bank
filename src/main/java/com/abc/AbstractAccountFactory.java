package com.abc;


public interface AbstractAccountFactory {
	
	Account createCheckingAccount();
    Account createSavingsAccount();
    Account createMaxiSavingAccount();

}
