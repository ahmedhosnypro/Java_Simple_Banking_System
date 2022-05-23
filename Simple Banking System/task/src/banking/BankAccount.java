package banking;

public class BankAccount {
    private final long cardNumber;
    private final int pin;
    private final long balance;

    public BankAccount(long cardNumber, int pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        balance = 0;
    }

    public long getCardNumber() {
        return cardNumber;
    }


    public int getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }
}