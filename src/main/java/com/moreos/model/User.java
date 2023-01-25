package com.moreos.model;

import com.moreos.services.UserIdsGenerator;
import com.moreos.arrays.TransactionsLinkedList;
import com.moreos.arrays.TransactionsList;

public class User {
    private final int id;
    private final String name;
    private int balance = 0;

    TransactionsList transactionsList;

    public User(UserIdsGenerator idsGenerator, String name, int balance) {
        if (balance < 0) throw new RuntimeException("balance must be positive");
        this.id = idsGenerator.generateId();
        this.name = name;
        this.balance = balance;
        transactionsList = new TransactionsLinkedList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }
}
