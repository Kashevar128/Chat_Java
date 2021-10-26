package clientlogic;

import org.intellij.lang.annotations.Language;

import java.sql.*;

public class DataBase {

    private static Connection connection;

    public DataBase() {
        try {
            connection = getConnection();
            createTable();
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        @Language("SQL")
        String query_00 = "jdbc:h2:./test";
        return DriverManager.getConnection(query_00);
    }

    private static void createTable() throws SQLException {
        @Language("SQL")
        String query_01 = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100), password VARCHAR(100));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query_01);
        }
    }

    public static void resultSet() throws SQLException {
        @Language("SQL")
        String query_02 = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query_02);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " : " + rs.getString("name") + " ; " + rs.getString("password"));
            }
        }
    }

    public static void delete(int ID) throws SQLException {
        @Language("SQL")
        String query_00 = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query_00)) {
            statement.setInt(1, ID);
            statement.executeUpdate();
        }
    }

}
