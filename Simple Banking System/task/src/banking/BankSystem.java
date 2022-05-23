package banking;

public class BankSystem {
    private BankSystem() {
    }

    private static final AccountsDataSet accountsDataSet = new AccountsDataSet();

    public static AccountsDataSet getAccountsDataSet() {
        return accountsDataSet;
    }

    public static void addNewAccount(BankAccount newBankAccount) {
        accountsDataSet.addNewAccount(newBankAccount);
    }
}
