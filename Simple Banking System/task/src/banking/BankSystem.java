package banking;

import java.util.Scanner;

import static banking.LuhnAlgorithm.checksum;

public class BankSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int PIN_SIZE = 4;

    private BankSystem() {
    }

    public static void createNewAccount() {
        BankAccount newBankAccount = AccountBuilder.createNewAccount();
        System.out.println("Your card has been created\n"
                + "Your card number:\n"
                + newBankAccount.getCardNumber() + "\n"
                + "Your card PIN:\n" + newBankAccount.getPin() + "\n");
        BankDataManager.addNewAccount(newBankAccount);
    }

    static void showCurrentUserBalance() {
        if (Boolean.TRUE.equals(Session.isIsLoggedIn())) {
            int balance = BankDataManager.getBalance(Session.getUserCardNum());
            if (balance != -1) {
                System.out.println("Balance: " + balance + "");
            } else {
                System.out.println("error");
            }
        }
    }

    static void addIncome() {
        System.out.println("Enter income:");

        String incomeInput = scanner.nextLine();

        if (isInValidMoneyInput(incomeInput)) {
            System.out.println("Invalid input");
            return;
        }

        if (BankDataManager.deposit(Session.getUserCardNum(), Integer.parseInt(incomeInput))) {
            System.out.println("Income was added!");
        } else {
            System.out.println("error! try again");
        }
    }

    static void doTransfer() {
        System.out.println("""
                Transfer
                Enter card number:""");

        String cardNum = scanner.nextLine();

        if (isNotValidNonExistCard(cardNum)) {
            return;
        }

        if (Session.getUserCardNum().equals(cardNum)) {
            System.out.println("You can't transfer money to yourself");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        String transferAmount = scanner.nextLine();

        if (isInValidMoneyInput(transferAmount)) {
            System.out.println("Invalid input");
            return;
        }

        if (Boolean.TRUE.equals(Session.isIsLoggedIn())) {
            int transferMoney = Integer.parseInt(transferAmount);
            if (transferMoney > BankDataManager.getBalance(Session.getUserCardNum())) {
                System.out.println("Not enough money!");
                return;
            }
            if (BankDataManager.transferMoney(Session.getUserCardNum(), cardNum, transferMoney)) {
                System.out.println("Success!");
            } else {
                System.out.println("error occurred during transfer");
            }
        }

    }

    static void closeAccount() {
        if (BankDataManager.closeAccount(Session.getUserCardNum())) {
            System.out.println("The account has been closed!");
        } else {
            System.out.println("error, try again!");
        }
    }

    static boolean isNotValidPin(String inputPin) {
        if (inputPin.length() == PIN_SIZE) {
            try {
                Integer.parseInt(inputPin);
                return false;
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return true;
    }

    static boolean isNotValidNonExistCard(String cardNum) {
        if (!isValidCardNumber(cardNum)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!\n");
            return true;
        }
        if (!BankDataManager.isCardExists(cardNum)) {
            System.out.println("Such a card does not exist.");
            return true;
        }
        return false;
    }

    static boolean isValidCardNumber(String inputCardNum) {
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

    private static boolean isInValidMoneyInput(String inputMoney) {
        try {
            int money = Integer.parseInt(inputMoney);
            return money < 0;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}