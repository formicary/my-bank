package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Interest {

    private Date transactionDate;

    public Interest() {
        this.transactionDate = DateProvider.now();
    }

	public Data getDate(){
		return transactionDate;
	}

}
