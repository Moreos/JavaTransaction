package com.moreos.arrays;

import com.moreos.model.User;
import com.moreos.exceptions.UserNotFoundException;

public class UsersArrayList implements UsersList {
    private int sizeArrayList = 10;
    User[] usersArrayList = new User[sizeArrayList];
    private int countUsers = 0;

    public void addUser(User user) {
        for (int i = 0; i < sizeArrayList; i++) {
            if (usersArrayList[i] == null) {
                usersArrayList[i] = user;
                break;
            }
        }
        countUsers++;
        if (countUsers == sizeArrayList) {
            sizeArrayList *= 2;
            User[] newUsersArrayList = new User[sizeArrayList];
            System.arraycopy(usersArrayList, 0, newUsersArrayList, 0, usersArrayList.length);
            usersArrayList = newUsersArrayList;
        }
    }

    public User getUserByID(int id) throws UserNotFoundException {
        User resultUser = null;
        for (int i = 0; i < sizeArrayList; i++) {
                if (usersArrayList[i] != null && usersArrayList[i].getId() == id) {
                    resultUser = usersArrayList[i];
                    break;
                }
        }

        if (resultUser == null) throw new UserNotFoundException("This User Id doesn't exist");

        return resultUser;
    }

    public User getUserByIndex(int index) throws UserNotFoundException {
        return usersArrayList[index];
    }

    public int getCountUsers() {
        return countUsers;
    }
}
