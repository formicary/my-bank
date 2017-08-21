package com.abc.person;

/**
 * Created by vahizan on 19/08/2017.
 */
public class Name {
    private Surname surname;
    private GivenNames names;
    public Name(GivenNames names,Surname surname){
        this.names=names;
        this.surname=surname;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(names.toString())
                .append(" ")
                .append(surname.toString());
        return sb.toString();
    }
}
