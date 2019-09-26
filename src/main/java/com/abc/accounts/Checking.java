package com.abc.accounts;

import java.time.LocalDateTime;

/**
 * @project MyBank
 */
public class Checking extends Account {

    public Checking(){
        super();
        init();
    }
    public Checking(LocalDateTime date){
        super(date);
        init();
    }

    private void init() {
        intRate = 0.001;
        accrueRate = intRate /365;
    }

    @Override
    protected void compoundInterest() {
        balance += balance * intRate;
    }

    @Override
    protected void accrueInterest() {
        intRate += accrueRate;
    }

    @Override
    public String toString() {
        return "Checking Account";
    }
}
