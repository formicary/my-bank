package com.abc;

import java.time.LocalDate;

public class CurrentTimeProvider implements ICurrentTimeProvider {
	public LocalDate now() {
		return LocalDate.now();
	}
}
