package com.moreos.model;

import com.moreos.exceptions.UserNotFoundException;
import com.moreos.model.Transaction;
import com.moreos.model.User;
import com.moreos.services.TransactionsService;
import com.moreos.services.UserIdsGenerator;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    boolean dev;
    boolean exit = false;
    char maxMenu = '5';
    UserIdsGenerator idsGenerator = new UserIdsGenerator();
    Scanner scanner = new Scanner(System.in);
    TransactionsService transactionsService = new TransactionsService();


    public Menu(boolean type) {
        dev = type;
        if (type) maxMenu = '7';
    }
    public void start() {
        while(!exit) {
            outConsoleMenu();
        }
    }

    public void outConsoleMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (dev) {
            System.out.println("5. DEV – remove a transfer by ID");
            System.out.println("6. DEV – check transfer validity");
            System.out.println("7. Finish execution");
        } else {
            System.out.println("5. Finish execution");
        }
        userIn();
        System.out.println("============================================================================================================");
    }

    public void userIn() {
        String userInput = scanner.nextLine();
        if (userInput.length() == 1 && userInput.charAt(0) > '0' && userInput.charAt(0) <= maxMenu) {
            runSomeFunctions(userInput.charAt(0) - '0');
        } else {
            System.out.println("Incorrect menu select, try again");
            userIn();
        }
    }

    public void runSomeFunctions(int select) {
        switch (select) {
            case 1:
                addUser();
                break;
            case 2:
                viewUserBalance();
                break;
            case 3:
                performTransfer();
                break;
            case 4:
                viewAllUserTransaction();
                break;
            case 5:
                if (dev) {
                    removeTransferById();
                } else {
                    exit = true;
                }
                break;
            case 6:
                checkTransferById();
                break;
            case 7:
                exit = true;
                break;
        }
    }

    public void addUser() {
        System.out.println("Enter a user name and a balance");
        String userInput = scanner.nextLine();
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 2) {
            try {
                User user = new User(idsGenerator, splitInput[0], Integer.parseInt(splitInput[1]));
                transactionsService.addUser(user);
                System.out.println("User " + user.getName() + " with id = " + user.getId() + " added");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                addUser();
            }
        } else {
            System.out.println("Not enough items, try again");
            addUser();
        }
    }
    public void viewUserBalance() {
        System.out.println("Enter a user ID");
        String userInput = scanner.nextLine();
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 1) {
            try {
                System.out.println("User " + transactionsService.getUsersList().getUserByID(Integer.parseInt(splitInput[0])).getName() + " have - " + transactionsService.getUserBalance(Integer.parseInt(splitInput[0])));
            } catch (UserNotFoundException u) {
                System.out.println(u.getMessage());
                viewUserBalance();
            }
        } else {
            System.out.println("Not enough items, try again");
            viewUserBalance();
        }
    }
    public void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String userInput = scanner.nextLine();
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 3) {
            try {
                transactionsService.makeTransaction(Integer.parseInt(splitInput[0]), Integer.parseInt(splitInput[1]), Integer.parseInt(splitInput[2]));
                System.out.println("The transfer is completed");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                performTransfer();
            }
        } else {
            System.out.println("Not enough items, try again");
            performTransfer();
        }
    }
    public void viewAllUserTransaction() {
        System.out.println("Enter a user ID");
        String userInput = scanner.nextLine();
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 1) {
            try {
                Transaction[] transactions = transactionsService.takeTransactionHistory(Integer.parseInt(splitInput[0]));

                for (Transaction t: transactions) {
                    System.out.println("To " + t.getRecipient().getName() + " (id = " + t.getRecipient().getId() + ") " + t.getTransferAmount() + " From " + t.getSender().getName() + " (id = " + t.getSender().getId() + ") with id = " + t.getId());
                }
                System.out.println("User " + transactionsService.getUsersList().getUserByID(Integer.parseInt(splitInput[0])).getName() + " have - " + transactionsService.getUserBalance(Integer.parseInt(splitInput[0])));
            } catch (UserNotFoundException u) {
                System.out.println(u.getMessage());
                viewAllUserTransaction();
            }
        } else {
            System.out.println("Not enough items, try again");
            viewAllUserTransaction();
        }
    }
    public void removeTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        String userInput = scanner.nextLine();
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 2) {
            try {
                Transaction t = transactionsService.deleteUserTransaction(UUID.fromString(splitInput[1]), Integer.parseInt(splitInput[0]));
                System.out.println("To " + t.getRecipient().getName() + " (id = " + t.getRecipient().getId() + ") " + t.getTransferAmount() + " From " + t.getSender().getName() + " (id = " + t.getSender().getId() + ") was removed");
            } catch (UserNotFoundException | IllegalArgumentException u) {
                System.out.println(u.getMessage());
                removeTransferById();
            }
        } else {
            System.out.println("Not enough items, try again");
            removeTransferById();
        }
    }
    public void checkTransferById() {
        Transaction[] transactions = transactionsService.takeNotValidateTransaction();
        System.out.println("Check result:");
        if (transactions.length == 0) {
            System.out.println("All right");
        } else {
            for (Transaction t : transactions) {
                System.out.println("ID: " + t.getId() + " Recipient: " + t.getRecipient().getName() + " (id = " + t.getRecipient().getId() + ") Sender: " + t.getSender().getName() + " (id = " + t.getRecipient().getId() + ") has an unacknowledged transfer");
            }
        }
    }
}
