package banking;

public class Session {
    private Session() {
    }

    private static String userCardNum;
    private static Boolean isLoggedIn;

    static boolean login() {
        System.out.println("Enter your card number:");
        String cardNum = Main.scanner.nextLine();

        System.out.println("Enter your PIN:");
        String pin = Main.scanner.nextLine();
        System.out.println();

        if (BankSystem.isValidCardNumber(cardNum) && !BankSystem.isNotValidPin(pin) && BankDataManager.isCardExists(cardNum) && BankDataManager.isCorrectPin(cardNum, pin)) {
            userCardNum = cardNum;
            isLoggedIn = true;
            System.out.println("You have successfully logged in!");
            return true;
        } else {
            System.out.println("Wrong card number or PIN!");
            return false;
        }
    }

    static void logout() {
        userCardNum = null;
        isLoggedIn = null;
        System.out.println("You have successfully logged out!");
    }
    public static String getUserCardNum() {
        return userCardNum;
    }

    public static Boolean isIsLoggedIn() {
        return isLoggedIn;
    }
}
