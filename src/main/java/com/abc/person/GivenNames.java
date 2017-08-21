package com.abc.person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahizan on 19/08/2017.
 */
public class GivenNames {
    private List<String> givenNames;
    public GivenNames(){
        givenNames=new ArrayList<String>();
    }
    public void addName(String name){
        givenNames.add(name);
    }
    public int size(){return givenNames.size();}
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(String name:givenNames){
            sb.append(name)
                    .append(" ");
        }
        String names=sb.toString();
        return names.trim();
    }
}
