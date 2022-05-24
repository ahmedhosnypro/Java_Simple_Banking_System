package banking;

public class BankAccount {
    private final String cardNumber;
    private final String pin;
    private final int balance;

    public BankAccount(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        balance = 0;
    }

    public String getCardNumber() {
        return cardNumber;
    }


    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }
}