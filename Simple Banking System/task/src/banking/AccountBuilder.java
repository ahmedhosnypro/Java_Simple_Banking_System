package banking;

import java.util.Random;

public class AccountBuilder {
    private AccountBuilder() {
    }

    private static final int PIN_SIZE = 4;
    private static final int RANDOM_BOUND = 9;
    private static final int BIN = 400000;
    private static final int ACCOUNT_ID_LENGTH = 9; // for now, it contains checksum
    static Random random = new Random();

    public static BankAccount createNewAccount() {
        BankAccount newAccount = new BankAccount(createNewCardNumber(), createRandomPin());
        BankSystem.addNewAccount(newAccount);
        return newAccount;
    }

    private static long createNewCardNumber() {
        StringBuilder cardNumBuilder = new StringBuilder(String.valueOf(BIN));

        for (int i = 0; i <= ACCOUNT_ID_LENGTH; i++) {
            cardNumBuilder.append(random.nextInt(RANDOM_BOUND));
        }

        if (BankSystem.getAccountsDataSet().getAccountsList().stream()
                .anyMatch(b -> b.getCardNumber() == Long.parseLong(cardNumBuilder.toString()))) {
            return createNewCardNumber();
        }

        return Long.parseLong(cardNumBuilder.toString());
    }

    private static int createRandomPin() {
        StringBuilder accountBinBuilder = new StringBuilder();

        for (int i = 0; i < PIN_SIZE; i++) {
            accountBinBuilder.append(random.nextInt(RANDOM_BOUND));
        }

        return Integer.parseInt(accountBinBuilder.toString());
    }
}
