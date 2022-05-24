package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankDataManager {
    private BankDataManager() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final String CHECK_PIN_QUERY = """
            SELECT pin
            FROM card
            WHERE number = ?
            ;
            """;
    private static final String GET_BALANCE_QUERY = """
            SELECT balance
            FROM card
            WHERE number = ?
            ;
            """;

    private static final String DEPOSIT_QUERY = """
            UPDATE card
            SET
            balance = balance + ?
            WHERE number = ?
            ;
            """;

    private static final String WITHDRAW_QUERY = """
            UPDATE card
            SET
            balance = balance - ?
            WHERE number = ?
            ;
            """;

    private static final String DELETE_QUERY = """
            DELETE FROM card
            WHERE number = ?
            ;
            """;
    private static final SQLiteDataSource dataSource = new SQLiteDataSource();

    static void setupData() {
        try (Connection con = dataSource.getConnection(); Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';");
            if (!resultSet.next() || !resultSet.getString("name").equals("card")) {
                statement.execute("""
                        CREATE TABLE card
                        (
                            id      INTEGER PRIMARY KEY AUTOINCREMENT,
                            number  TEXT,
                            pin     TEXT,
                            balance INTEGER DEFAULT 0
                        );
                        """);

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void setPathToDatabase(String pathToDatabase) {
        String url = JDBC_URL_PREFIX + pathToDatabase;
        dataSource.setUrl(url);
    }

    static void addNewAccount(BankAccount account) {
        try (Connection con = dataSource.getConnection(); Statement statement = con.createStatement()) {
            statement.execute(String.format("INSERT INTO card (number, pin, balance)\n" +
                    "                    VALUES (%s, %s, %d)", account.getCardNumber(), account.getPin(), account.getBalance()));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static List<String> getCardNumList() {
        try (Connection con = dataSource.getConnection(); Statement statement = con.createStatement()) {
            ArrayList<String> cardNumList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT number FROM card;");
            while (resultSet.next()) {
                cardNumList.add(resultSet.getString(1));
            }
            return cardNumList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Collections.emptyList();
    }

    static boolean isCardExists(String cardNum) {
        return getCardNumList().contains(cardNum);
    }

    static boolean isCorrectPin(String cardNum, String pin) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement selectPin = con.prepareStatement(CHECK_PIN_QUERY)) {
            selectPin.setString(1, cardNum);
            ResultSet resultSet = selectPin.executeQuery();
            return (resultSet.next() && resultSet.getString("pin").equals(pin));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    static int getBalance(String cardNum) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement selectPin = con.prepareStatement(GET_BALANCE_QUERY)) {
            selectPin.setString(1, cardNum);
            ResultSet resultSet = selectPin.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("balance");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    static boolean transferMoney(String currentLoggedUserCardNumber, String toUserCardNum, int transferAmount) {
        if (withdraw(currentLoggedUserCardNumber, transferAmount)) {
            return deposit(toUserCardNum, transferAmount);
        }
        return false;
    }

    private static boolean withdraw(String cardNum, int withdraw) {
        return setBalance(cardNum, withdraw, WITHDRAW_QUERY);
    }

    static boolean deposit(String cardNum, int money) {
        return setBalance(cardNum, money, DEPOSIT_QUERY);
    }

    private static boolean setBalance(String cardNum, int money, String query) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement withdraw = con.prepareStatement(query)) {
            withdraw.setInt(1, money);
            withdraw.setString(2, cardNum);
            return !withdraw.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    static boolean closeAccount(String cardNum) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement withdrawById = con.prepareStatement(DELETE_QUERY)) {
            withdrawById.setString(1, cardNum);
            return !withdrawById.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
