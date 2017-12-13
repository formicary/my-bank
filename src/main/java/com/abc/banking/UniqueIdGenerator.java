package com.abc.banking;

public final class UniqueIdGenerator {
	private static long lastId = 0;
	
	private UniqueIdGenerator() { }
	
	protected static long getNext() {
		return ++lastId;
	}
}
