package com.moreos.arrays;

import com.moreos.model.Transaction;

import java.util.UUID;

public interface TransactionsList {
    public void addTransaction(Transaction transaction);
    public Transaction deleteTransaction(UUID id);
    public Transaction[] listToArray();
}
