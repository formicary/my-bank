package com.abc;

import com.abc.accenture.financial.items.Bank;
import com.abc.accenture.financial.services.BankService;
import com.abc.accenture.financial.services.BankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BankDemoApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(BankDemoApplication.class);
    }

    @Override
    public void run(String... args) {

        BankService bankService = applicationContext.getBean("bankService", BankServiceImpl.class);
        Bank bank = new Bank();
        bankService.openAccount(bank, "1", "Jill");

        System.out.println(bankService.customerSummary(bank));
    }
}
