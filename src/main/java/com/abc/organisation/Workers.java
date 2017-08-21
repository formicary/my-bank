package com.abc.organisation;

import com.abc.person.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahizan on 19/08/2017.
 */
public class Workers {
    private List<Employee> workers;
    public Workers(){
        workers = new ArrayList<Employee>();
    }

    public void add(Employee employee){ workers.add(employee); }
    public int size(){ return workers.size(); }
}
