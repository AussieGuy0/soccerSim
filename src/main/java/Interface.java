package main.java;

import java.util.Scanner;

/**
 * Created by anthony on 11/07/16.
 */
public class Interface {

    private final Scanner in = new Scanner(System.in);

    public void displayMainMenu() {
        System.out.println("1. Create Friendly Game");
        System.out.println("2. Create League");
        System.out.println("3. Load league");
        System.out.println("4. Quit");
        int input;
        do {
            input = in.nextInt();
        } while (input < 1 && input > 5);

        switch (input) {
            case 1:
                displayFriendlyGameMenu();
                break;
            case 2:
                displayLeagueMenu();
                break;
            case 3:
                displayLoadMenu();
                break;
            case 4:
                System.exit(0);
        }
    }

    private void displayLeagueMenu() {
    }


    private void displayLoadMenu() {
    }

    private void displayFriendlyGameMenu() {

    }


}
