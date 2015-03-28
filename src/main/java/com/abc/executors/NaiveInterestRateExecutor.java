package com.abc.executors;


final public class NaiveInterestRateExecutor implements InterestRateExecutor {

	final private AbstractInterestCalculator task;
	
	public NaiveInterestRateExecutor(final AbstractInterestCalculator task) {
	        this.task = task;
	}
	
	@Override
	public void start() {		
		task.execute();
	}

	@Override
	public void shutdown() {}

}
