package com.abc.bank.admin;

public interface Visitor<T> {

	boolean visit(T t);
}
