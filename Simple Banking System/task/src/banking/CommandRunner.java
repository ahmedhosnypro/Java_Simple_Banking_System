package banking;

public class CommandRunner {
    private CommandRunner() {
    }

    static void createAccount() {
        BankSystem.createNewAccount();
        CLI.printMainMenu();
    }

    static void login() {
        if (Session.login()) {
            System.out.println();
            CLI.printLoggedInMenu();
        } else {
            System.out.println();
            CLI.printMainMenu();
        }
    }

    static void showBalance() {
        BankSystem.showCurrentUserBalance();
        System.out.println();
        CLI.printLoggedInMenu();
    }

    static void addIncome() {
        BankSystem.addIncome();
        System.out.println();
        CLI.printLoggedInMenu();
    }

    static void doTransfer() {
        BankSystem.doTransfer();
        System.out.println();
        CLI.printLoggedInMenu();
    }

    static void closeAccount() {
        BankSystem.closeAccount();
        System.out.println();
        CLI.printMainMenu();
    }

    static void logOut() {
        Session.logout();
        System.out.println();
        CLI.printMainMenu();
    }
}
