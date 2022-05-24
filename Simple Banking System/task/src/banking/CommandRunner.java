package banking;

import java.util.Objects;
import java.util.Scanner;

import static banking.LuhnAlgorithm.checksum;

public class CommandRunner {
    private CommandRunner() {
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int PIN_SIZE = 4;
    private static final String ERROR_MESSAGE = "\nWrong card number or PIN!\n";

    static void createAccount() {
        BankAccount newBankAccount = AccountBuilder.createNewAccount();
        System.out.println("\nYour card has been created\n"
                + "Your card number:\n"
                + newBankAccount.getCardNumber() + "\n"
                + "Your card PIN:\n" + newBankAccount.getPin()
                + "\n");
        CLI.printMainMenu();
    }

    static void logIn() {
        System.out.println("\nEnter your card number:");
        String cardNum = scanner.nextLine();

        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();

        if (!isValidCardNumber(cardNum) || !isValidPin(pin)) {
            System.out.println(ERROR_MESSAGE);
            CLI.printMainMenu();
            return;
        }

        var optBankAccount = BankSystem.getAccountsDataSet().getAccountsList().stream()
                .filter(b -> b.getCardNumber() == Long.parseLong(cardNum)).findFirst();

        if (optBankAccount.isPresent()) {
            if (optBankAccount.get().getPin().equals(pin)) {
                CLI.setSession(new Session(optBankAccount.get()));
                System.out.println("\nYou have successfully logged in!\n");
                CLI.printLoggedAccountInfoMenu();
            } else {
                System.out.println(ERROR_MESSAGE);
                CLI.printMainMenu();
            }
        } else {
            System.out.println(ERROR_MESSAGE);
            CLI.printMainMenu();
        }
    }

    static void printLoggedAccountInfo() {
        if (Objects.nonNull(CLI.getSession())) {
            System.out.println("\nBalance: " + CLI.getSession().getBankAccount().getBalance() + "\n");
        }
    }

    private static boolean isValidCardNumber(String inputCardNum) {
        if (inputCardNum.length() == CARD_NUMBER_LENGTH) {
            try {
                Long.parseLong(inputCardNum);
                return checksum(inputCardNum);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private static boolean isValidPin(String inputPin) {
        if (inputPin.length() == PIN_SIZE) {
            try {
                Integer.parseInt(inputPin);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    static void logOut() {
        CLI.setSession(null);
        System.out.println("\nYou have successfully logged out!\n");
        CLI.printMainMenu();
    }
}
