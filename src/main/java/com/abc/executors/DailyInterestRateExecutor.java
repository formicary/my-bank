package com.abc.executors;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a simple executor that is run with <code>Bank</code> to recompute
 * earned interest for all accounts in the bank. Initially, it is scheduled to run every day 
 * including weekends at 00:00:01. However, to ensure that this service is run,
 * the <code>Bank</code> application needs keep application thread(s) alive.  
 */

final public class DailyInterestRateExecutor implements InterestRateExecutor  {
    
	/**
	 * Executor service.
	 */
	final private ScheduledExecutorService executorService;
    /**
     * Task run with the executor service.
     */
	final private AbstractInterestCalculator task;
    /**
     * 
     */

    public DailyInterestRateExecutor(final AbstractInterestCalculator task) {
        this.task = task;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Enable executor executions. It should be enabled only once. 
     */
    @Override
    public void start () {
    	startExecutionAt(0, 0, 1);
    }
    
    /**
     * It is possible to provide the exact time when executor should be run.
     * @param hour.
     * @param min.
     * @param sec.
     */
    public void startExecutionAt(final int hour, final int min, final int sec) {
    	startExecutionAt(hour, min, sec, false);
    }
      
    private void startExecutionAt(final int hour, final int min, final int sec, final boolean executed) {
        Runnable taskWrapper = new Runnable(){

            @Override
            public void run() 
            {
                task.execute();
                startExecutionAt(hour, min, sec, true);
            }

        };
        long next = computeNext(hour, min, sec, executed);
      
        executorService.schedule(taskWrapper, next, TimeUnit.SECONDS);
    }

    private long computeNext (final int hour, final int min, final int sec, final boolean executed) {
        LocalDateTime local = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(local, zone);
        ZonedDateTime zonedNext = zonedNow.withHour(hour)
        								  .withMinute(min)
        								  .withSecond(sec);
        
        if(executed || zonedNow.compareTo(zonedNext) > 0) {
            zonedNext = zonedNext.plusDays(1);
        }

        Duration duration = Duration.between(zonedNow, zonedNext);
        return duration.getSeconds();
    }


    /**
     * Method to shut down the executor.
     */
    @Override
	public void shutdown() {
		executorService.shutdownNow();
		
	}
}
