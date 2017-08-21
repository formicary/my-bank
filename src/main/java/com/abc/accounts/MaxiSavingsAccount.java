package com.abc.accounts;

import com.abc.constants.AccountConstantsNew;

/**
 * Created by vahizan on 07/08/2017.
 */
public class MaxiSavingsAccount extends Account {
    @Override
    public AccountConstantsNew accountType() {
        return AccountConstantsNew.MAXI_SAVINGS;
    }

}
