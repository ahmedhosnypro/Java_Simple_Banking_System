package banking;

public class Session {
    private final BankAccount bankAccount;

    public Session(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
