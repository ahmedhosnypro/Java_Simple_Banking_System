package banking;

import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        BankDataManager.setPathToDatabase(args[1]);
        BankDataManager.setupData();
        CLI.printMainMenu();
    }
}