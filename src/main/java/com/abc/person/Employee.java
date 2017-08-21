package com.abc.person;

/**
 * Created by vahizan on 19/08/2017.
 */
public class Employee implements Person {
    private Name name;
    public Employee(Name name){
        this.name=name;
    }
    public Name name() {
        return name;
    }
}
