package com.abc;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledTask {
	    private final ScheduledExecutorService scheduler = Executors
	        .newScheduledThreadPool(1);

	    public void startScheduleTask(List<Customer> customers) {
	    /**
	    * Scheduled TASK: interestEarned() run daily.
	    */
	    final ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(
	        new Runnable() {
	            public void run() {
	                try {
	                	if (customers.size()>0)
							for (Customer customer : customers)
								if (customer.getNumberOfAccounts()>0)
									for (Account account: customer.getAccounts())
										account.deposit(account.interestEarned());
						
	                }catch(Exception ex) {
	                    ex.toString();
	                }
	            }
	        }, 0, 1, TimeUnit.DAYS);
	    }

}
