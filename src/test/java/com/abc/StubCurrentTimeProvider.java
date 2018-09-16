package com.abc;

import java.time.LocalDate;

public class StubCurrentTimeProvider implements ICurrentTimeProvider {

	private final LocalDate time;
	
	public StubCurrentTimeProvider(LocalDate time) {
		this.time = time;
	}

	public LocalDate now() {
		return time;
	}
}
