package com.abc.executors;

/**
 * To create own executors for interest rate update, one needs to implement this interface.
 */
public interface InterestRateExecutor {
	
	void start();
	void shutdown();

}
