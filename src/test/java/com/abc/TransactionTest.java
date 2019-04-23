package com.abc;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionTest {

	Transaction t;
	
	@BeforeEach
	void setUp() throws Exception {
		t = new Transaction(5);
	}

	/**
	 * 
	 */
	@Test
    public void transaction() {
        assertTrue(t instanceof Transaction);
    }

}
