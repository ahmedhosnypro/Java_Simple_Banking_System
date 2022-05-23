package banking;

import java.util.*;

public class AccountsDataSet {
    private final HashSet<BankAccount> accounts = new HashSet<>(); //card number, account

    public Set<BankAccount> getAccountsList() {
        return accounts;
    }

    public void addNewAccount(BankAccount newBankAccount) {
        accounts.add(newBankAccount);
    }
}