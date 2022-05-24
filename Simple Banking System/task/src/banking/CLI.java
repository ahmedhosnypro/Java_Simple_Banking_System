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
            2. Log out
            0. Exit""";
    private static final String INPUT_ERROR_MSG = "Invalid choice";
    private static final Scanner scanner = new Scanner(System.in);

    private static Session session = null;

    static void printMainMenu() {
        System.out.println(MAIN_MENU);

        String inputChoice = scanner.nextLine();

        if (isNotValidChoice(inputChoice)) {
            System.out.println(INPUT_ERROR_MSG);
            printMainMenu();
            return;
        }

        switch (Integer.parseInt(inputChoice)) {
            case 1 -> CommandRunner.createAccount();
            case 2 -> CommandRunner.logIn();
            case 0 -> exit();
            default -> throw new IllegalArgumentException();
        }
    }

    static void printLoggedAccountInfoMenu() {
        System.out.println(ACCOUNT_INFO_MENU);

        String inputChoice = scanner.nextLine();

        if (isNotValidChoice(inputChoice)) {
            System.out.println(INPUT_ERROR_MSG);
            printLoggedAccountInfoMenu();
            return;
        }

        switch (Integer.parseInt(inputChoice)) {
            case 1 -> {
                CommandRunner.printLoggedAccountInfo();
                printLoggedAccountInfoMenu();
            }
            case 2 -> CommandRunner.logOut();
            case 0 -> exit();
            default -> throw new IllegalArgumentException();
        }
    }

    public static void setSession(Session session) {
        CLI.session = session;
    }

    public static Session getSession() {
        return session;
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