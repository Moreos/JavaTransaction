package com.moreos.model;

import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final User recipient;
    private final User sender;
    private final String transferType;
    private final int transferAmount;

    private Transaction next = null;
    private Transaction prev = null;

    public Transaction(UUID id, User recipient, User sender, String transferType, int transferAmount) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        if (transferType.equals("OUTCOME") || transferType.equals("INCOME")) {
            this.transferType = transferType;
        } else {
            this.transferType = ("OUTCOME");
            System.out.println("Incorrect type, set default OUTCOME");
        }
        if (this.transferType.equals("OUTCOME") && transferAmount < 0) {
            this.transferAmount = transferAmount;
        } else if (this.transferType.equals("INCOME") && transferAmount > 0) {
            this.transferAmount = transferAmount;
        } else {
            this.transferAmount = 0;
            System.out.println("Incorrect amount, nothing changed");
        }
    }

    public void setNext(Transaction next) {
        this.next = next;
    }

    public void setPrev(Transaction prev) {
        this.prev = prev;
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public String getTransferType() {
        return transferType;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public Transaction getNext() {
        return next;
    }

    public Transaction getPrev() {
        return prev;
    }
}
