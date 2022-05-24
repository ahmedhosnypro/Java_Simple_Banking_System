package banking;

import java.util.Random;

import static banking.LuhnAlgorithm.createCardCheckSum;

public class AccountBuilder {
    private AccountBuilder() {
    }

    private static final int PIN_SIZE = 4;
    private static final int RANDOM_BOUND = 9;
    private static final int BIN = 400000;
    private static final int ACCOUNT_ID_LENGTH = 9;
    static Random random = new Random();

    public static BankAccount createNewAccount() {
        return new BankAccount(createNewCardNumber(), createRandomPin());
    }

    private static String createNewCardNumber() {
        StringBuilder cardNumBuilder = new StringBuilder(String.valueOf(BIN));

        for (int i = 0; i < ACCOUNT_ID_LENGTH; i++) {
            cardNumBuilder.append(random.nextInt(RANDOM_BOUND));
        }

        cardNumBuilder.append(createCardCheckSum(cardNumBuilder.toString()));

        if (BankDataManager.isCardExists(cardNumBuilder.toString())) {
            return createNewCardNumber();
        }

        return cardNumBuilder.toString();
    }

    private static String createRandomPin() {
        StringBuilder accountBinBuilder = new StringBuilder();

        int i = 0;
        while (i < PIN_SIZE) {
            int randomNUm = random.nextInt(RANDOM_BOUND);
            if (!(i == 0 && randomNUm == 0)) {
                accountBinBuilder.append(randomNUm);
                i++;
            }
        }

        return accountBinBuilder.toString();
    }
}
