package banking;

public class Main {
    public static void main(String[] args) {
        BankDataManager.setPathToDatabase(args[1]);
        BankDataManager.setUpData();
        CLI.printMainMenu();
    }
}