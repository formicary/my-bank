package com.abc.users;

/**
 * @project MyBank
 */
public abstract class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
