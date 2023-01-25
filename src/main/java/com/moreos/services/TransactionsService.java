package com.moreos.services;

import com.moreos.arrays.TransactionsLinkedList;
import com.moreos.arrays.TransactionsList;
import com.moreos.arrays.UsersArrayList;
import com.moreos.arrays.UsersList;
import com.moreos.exceptions.IllegalTransactionException;
import com.moreos.model.Transaction;
import com.moreos.model.User;

import java.util.UUID;

public class TransactionsService {
    UsersList usersList = new UsersArrayList();

    public UsersList getUsersList() {
        return usersList;
    }

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getUserBalance(int userId) {
        return usersList.getUserByID(userId).getBalance();
    }

    public void makeTransaction(int idUserFrom, int idUserTo, int amount) {
        if (usersList.getUserByID(idUserFrom).getBalance() < amount)
            throw new IllegalTransactionException("User with id: " + idUserFrom + " has not enough money\n \tHave: " + usersList.getUserByID(idUserFrom).getBalance() + " need: " + amount);
        if (amount < 0)
            throw new IllegalTransactionException("Amount must be positive");


        UUID transactionId = UUID.randomUUID();
        User userOut = usersList.getUserByID(idUserFrom);
        User userIn = usersList.getUserByID(idUserTo);
        Transaction tOut = new Transaction(transactionId, userIn, userOut, "OUTCOME", amount * -1);
        Transaction tIn = new Transaction(transactionId, userIn, userOut, "INCOME", amount);
        userOut.setBalance(userOut.getBalance() - amount);
        userIn.setBalance(userIn.getBalance() + amount);
        userOut.getTransactionsList().addTransaction(tOut);
        userIn.getTransactionsList().addTransaction(tIn);
    }

    public Transaction[] takeTransactionHistory(int userId) {
        return usersList.getUserByID(userId).getTransactionsList().listToArray();
    }

    public Transaction deleteUserTransaction(UUID transactionId, int userId) {
        return usersList.getUserByID(userId).getTransactionsList().deleteTransaction(transactionId);
    }

    public Transaction[] takeNotValidateTransaction() {
        int userCount = usersList.getCountUsers();
        Transaction[][] transactionsForValidate = new Transaction[userCount][];
        TransactionsList transactionsList = new TransactionsLinkedList();
        for (int i = 0; i < userCount; i++) {
            transactionsForValidate[i] = usersList.getUserByIndex(i).getTransactionsList().listToArray();
        }
        for (int i = 0; i < userCount; i++) {
            for (int j = 0; j < transactionsForValidate[i].length; j++) {
                UUID currentTransactionId = transactionsForValidate[i][j].getId();
                boolean noMatches = true;
                int counterMatches = 0;
                for (int k = 0; k < userCount; k++) {
                    if (k == i) continue;
                    for (int z = 0; z < transactionsForValidate[k].length; z++) {
                        if (currentTransactionId.compareTo(transactionsForValidate[k][z].getId()) == 0) {
                            noMatches = false;
                            break;
                        }
                    }
                }
                if (noMatches) {
                    transactionsList.addTransaction(transactionsForValidate[i][j]);
                }
            }
        }
        return transactionsList.listToArray();
    }
}
