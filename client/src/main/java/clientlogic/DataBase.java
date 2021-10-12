package clientlogic;

import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    @Language("SQL")
    String s =
    public static void main(String[] args) throws Exception {
        init();
    }

    private static void init() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:~/test");
    }

    public static void statements (Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("");
        }
    }

}
