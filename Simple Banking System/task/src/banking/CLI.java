package banking;

import java.util.Scanner;

public class CLI {
    private CLI() {
    }

    private static final String MAIN_MENU = """
            1. Create an account
            2. Log into account
            0. Exit""";

    private static final String ACCOUNT_INFO_MENU = """
            1. Balance
            2. Add income
            3. Do transfer
            4. Close account
            5. Log out
            0. Exit""";
    private static final String INPUT_ERROR_MSG = "Invalid choice";
    private static final Scanner scanner = new Scanner(System.in);

    static void printMainMenu() {
        System.out.println(MAIN_MENU);

        String inputChoice = scanner.nextLine();
        System.out.println();

        if (isNotValidChoice(inputChoice)) {
            System.out.println(INPUT_ERROR_MSG);
            printMainMenu();
            return;
        }

        switch (Integer.parseInt(inputChoice)) {
            case 1 -> CommandRunner.createAccount();
            case 2 -> CommandRunner.login();
            case 0 -> exit();
            default -> {
                System.out.println("Invalid choice, try again!\n");
                printMainMenu();
            }
        }
    }

    static void printLoggedInMenu() {
        System.out.println(ACCOUNT_INFO_MENU);

        String inputChoice = scanner.nextLine();
        System.out.println();

        if (isNotValidChoice(inputChoice)) {
            System.out.println(INPUT_ERROR_MSG);
            printLoggedInMenu();
            return;
        }

        switch (Integer.parseInt(inputChoice)) {
            case 1 -> CommandRunner.showBalance();
            case 2 -> CommandRunner.addIncome();
            case 3 -> CommandRunner.doTransfer();
            case 4 -> CommandRunner.closeAccount();
            case 5 -> CommandRunner.logOut();
            case 0 -> exit();
            default -> {
                System.out.println("Invalid choice, try again!\n");
                printLoggedInMenu();
            }
        }
    }

    private static boolean isNotValidChoice(String inputChoice) {
        try {
            Integer.parseInt(inputChoice);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static void exit() {
        System.out.println("\nBye!");
        System.exit(0);
    }
}