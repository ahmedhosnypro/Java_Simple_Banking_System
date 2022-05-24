package banking;

public class BankAccount {
    private final long cardNumber;
    private final String pin;
    private final long balance;

    public BankAccount(long cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        balance = 0;
    }

    public long getCardNumber() {
        return cardNumber;
    }


    public String getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }
}