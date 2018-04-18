package com.abc.Accounts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for the AccountIdManager class.
 */
public class AccountIdManagerTests {
    /**
     * The account ID manager.
     */
    private IAccountIdManager accountIdManager;

    @Before
    public void setUp() {
        this.accountIdManager = AccountIdManager.getInstance();
    }

    /**
     * Tests that a account ID is generated correctly.
     */
    @Test
    public void generatesAccountIdCorrectly() {
        int accountId = this.accountIdManager.generateAccountId();

        Assert.assertNotEquals(accountId, 0);
    }

    /**
     * Tests that different account IDs are generated each time.
     */
    @Test
    public void generatesUniqueAccountIds() {
        int firstAccountId = this.accountIdManager.generateAccountId();
        int secondAccountId = this.accountIdManager.generateAccountId();

        Assert.assertNotEquals(firstAccountId, secondAccountId);
    }
}
