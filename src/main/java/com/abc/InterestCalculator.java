package com.abc;

import java.util.Calendar;
import java.util.TimerTask;

public class InterestCalculator extends TimerTask {
	private Bank b;
	private Calendar c;

	/**
	 * 
	 * @param b
	 *            a bank object, which the task can then prompt at the correct
	 *            time to calculate the compound daily interest.
	 * @param c
	 *            a Calendar object storing the execution time of the task. The
	 *            task is scheduled on creation (in Bank) to execute every day
	 *            at the same time.
	 */
	public InterestCalculator(Bank b, Calendar c) {
		this.b = b;
		this.c = c;
	}

	/**
	 * implements the run method for the TimerTask parent class. Prints a
	 * notification that daily interests are being calculated, and executes the
	 * bank object's accrueDailyInterest() method.
	 */
	@Override
	public void run() {
		System.out.println("Accruing daily interest on " + c.getTime());
		b.accrueDailyInterest();
	}
}
