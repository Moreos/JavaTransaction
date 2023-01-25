package com.moreos.arrays;

import com.moreos.model.User;
import com.moreos.exceptions.UserNotFoundException;

public interface UsersList {
    public void addUser(User user);
    public User getUserByID(int id) throws UserNotFoundException;
    public User getUserByIndex(int index) throws UserNotFoundException;
    public int getCountUsers();
}
