package com.moreos.arrays;

import com.moreos.model.Transaction;
import com.moreos.exceptions.TransactionNotFoundException;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    int size = 0;
    Transaction head = null; // Начало
    Transaction tail = null; // Конец

    public void addTransaction(Transaction transaction) {
        if (head == null) {
            tail = head = transaction;
        } else {
            tail.setNext(transaction);
            transaction.setPrev(tail);
            tail = transaction;
        }
        size++;
    }

    public Transaction deleteTransaction(UUID id) {
        boolean wasDeleted = false;
        Transaction t = head;
        for (; t != null; t = t.getNext()) {
            if (t.getId().compareTo(id) == 0) {
                t.getPrev().setNext(t.getNext());
                wasDeleted = true;
                size--;
                break;
            }
        }

        if (!wasDeleted) throw new TransactionNotFoundException("This Transaction with id: " + id + " doesn't exist");

        return t;
    }

    public Transaction[] listToArray() {
        Transaction[] array = new Transaction[size];
        int i = 0;
        for (Transaction t = head; t != null && i < size; t = t.getNext(), i++) {
            array[i] = t;
        }

        return array;
    }
}
