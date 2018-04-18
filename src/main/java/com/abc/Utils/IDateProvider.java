package com.abc.Utils;

import java.util.Date;

/**
 * Represents an interface for a date provider.
 */
public interface IDateProvider {
    /**
     * Returns the present date.
     *
     * @return The present date.
     */
    Date now();
}
