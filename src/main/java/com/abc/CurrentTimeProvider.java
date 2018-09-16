package com.abc;

import java.time.LocalDate;

/**
 * Gets the current date.
 */
public class CurrentTimeProvider implements ICurrentTimeProvider {
	public LocalDate now() {
		return LocalDate.now();
	}
}
