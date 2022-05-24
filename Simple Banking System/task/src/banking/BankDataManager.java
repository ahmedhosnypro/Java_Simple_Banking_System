package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class BankDataManager {
    private BankDataManager() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource dataSource = new SQLiteDataSource();

    static void setUpData() {
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
                    "                    VALUES (%d, %s, %d)", account.getCardNumber(), account.getPin(), account.getBalance()));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
