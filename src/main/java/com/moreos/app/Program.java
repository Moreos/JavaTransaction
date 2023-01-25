package com.moreos.app;

import com.moreos.model.Menu;

public class Program {
    public static void main(String[] args) {
        Menu menu;
        if (args.length == 1 && args[0].equals("--profile=dev")) {
            menu = new Menu(true);
        } else {
            menu = new Menu(false);

        }
        menu.start();
    }
}