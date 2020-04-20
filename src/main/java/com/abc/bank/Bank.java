package com.abc.bank;

import com.abc.customer.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Bank {
    private List<Customer> customers = new ArrayList<>();
}
