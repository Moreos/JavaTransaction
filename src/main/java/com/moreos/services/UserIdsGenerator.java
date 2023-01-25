package com.moreos.services;

public class UserIdsGenerator {
    private int instance;

    public UserIdsGenerator() {
        instance = 0;
    }

    public int getInstance() {
        return instance;
    }

    public int generateId() {
        return ++instance;
    }
}
