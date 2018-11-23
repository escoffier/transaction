package com.example.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Autowired
    PlatformTransactionManager platformTransactionManager = null;

    @PostConstruct
    public void viewTransactionManager(){
        System.out.println("--------- " + platformTransactionManager.getClass().getName());
    }
}
