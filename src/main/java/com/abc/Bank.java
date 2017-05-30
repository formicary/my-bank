package com.abc;


import com.google.common.collect.Maps;

import java.util.Map;

public class Bank {
    private SystemManagement systemManagement;

    public Bank(String name) {
        this.systemManagement = new StandardSystemManagement();
    }

    public Bank(String name, SystemManagement systemManagement) {
        this.systemManagement = systemManagement;
    }

    public SystemManagement getSystemManagement() {
        return systemManagement;
    }

}
