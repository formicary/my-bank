package com.abc.banking;

import org.junit.Assert;
import org.junit.Test;

import com.abc.banking.UniqueIdGenerator;

public class UniqueIdGeneratorTest {
	@Test
	public void uniqueIdGeneratorShallOnSubsequentCallsReturnDifferentValues() {
		Assert.assertNotEquals(UniqueIdGenerator.getNext(), UniqueIdGenerator.getNext());
	}
}
