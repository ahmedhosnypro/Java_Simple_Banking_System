package banking;

import java.util.Objects;
import java.util.Scanner;

public class CommandRunner {
    private CommandRunner() {
    }

    private static final Scanner scanner = new Scanner(System.in);

    static void createAccount() {
        BankAccount newBankAccount = AccountBuilder.createNewAccount();
        System.out.println("\nYour card has been created\n"
                + "Your card number:\n"
                + newBankAccount.getCardNumber() + "\n"
                + "Your card PIN:\n" + newBankAccount.getPin()
                + "\n");
        UI.showMainMenu();
    }

    static void logIn() {
        System.out.println("\nEnter your card number:");
        String cardNum = scanner.nextLine();
        if (!isValidCardNumber(cardNum)) {
            return;
        }
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();
        if (!isValidPin(pin)) {
            return;
        }

        var optBankAccount = BankSystem.getAccountsDataSet().getAccountsList().stream()
                .filter(b -> b.getCardNumber() == Long.parseLong(cardNum)).findFirst();

        if (optBankAccount.isPresent()) {
            if (optBankAccount.get().getPin() == Integer.parseInt(pin)) {
                UI.setSession(new Session(optBankAccount.get()));
                System.out.println("\nYou have successfully logged in!\n");
                UI.showLoggedAccountInfoMenu();
            } else {
                System.out.println("\nWrong card number or PIN!\n");
                UI.showMainMenu();
            }
        } else {
            System.out.println("\nWrong card number or PIN!\n");
            UI.showMainMenu();
        }
    }

    static void showLoggedAccountInfo() {
        if (Objects.nonNull(UI.getSession())) {
            System.out.println("\nBalance: " + UI.getSession().getBankAccount().getBalance() + "\n");
        }
    }

    private static boolean isValidCardNumber(String inputCardNum) {
        if (inputCardNum.length() == 16) {
            try {
                Long.parseLong(inputCardNum);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private static boolean isValidPin(String inputPin) {
        if (inputPin.length() == 4) {
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
        UI.setSession(null);
        System.out.println("\nYou have successfully logged out!\n");
        UI.showMainMenu();
    }
}
