package com.abc.history;

import java.util.Date;

/**
 * Created by vahizan on 18/08/2017.
 */
public interface History {
    String fullStatement();
    String periodStatement(Date dateAfter,Date dateBefore);
}
