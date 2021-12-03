package clientlogic;

import gui.Avatar;
import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

public class DataBase implements AuthService {

    private static DataBase instance;

    public DataBase() throws Exception {
        createTable();
    }

    public static void main(String[] args) throws Exception {
        // addField();
        resultSet();
    }

    public static synchronized DataBase getInstance() throws Exception {
        if (instance == null) instance = new DataBase();
        return instance;
    }

    private static void init() throws ClassNotFoundException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("org.h2.Driver");
    }


    public static Connection getConnection() throws SQLException {
        @Language("SQL")
        String urlMySql = "jdbc:mysql://localhost:3306/test";
        String urlH2 = "jdbc:h2:./client/src/main/resources/db/demodb";
        String user = "root";
        String pass = "root";
        //return DriverManager.getConnection(urlMySql, user, pass);
        return DriverManager.getConnection(urlH2, "", "");
    }

    public static void addField() throws Exception {
        init();
        try (Connection connection = getConnection()) {
            @Language("SQL")
            String query = "ALTER TABLE users ADD avatar TEXT NULL";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query);
            }
        }
    }

    private static void createTable() throws Exception {
        init();
        try (Connection connection = getConnection()) {
            @Language("SQL")
            String query_01 = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100), password VARCHAR(100), avatar BLOB)";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query_01);
            }
        }
    }

    public static void resultSet() throws Exception {
        init();
        try (Connection connection = getConnection()) {
            @Language("SQL")
            String query_02 = "SELECT * FROM users";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query_02);
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " : " + rs.getString("name") + " ; " + rs.getString("password") +
                            " ; " + Arrays.toString(rs.getBytes("avatar")));
                }
            }
        }
    }

    public static void delete(int ID) throws Exception {
        init();
        try (Connection connection = getConnection()) {
            @Language("SQL")
            String query_00 = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query_00)) {
                statement.setInt(1, ID);
                statement.executeUpdate();
            }
        }
    }


    @Override
    public boolean addUser(String name, String pass) throws Exception {
        init();
        try (Connection connection = getConnection()) {
            @Language("SQL")
            String query_01 = "INSERT INTO users (name, password, avatar) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query_01)) {
                statement.setString(1, name);
                statement.setString(2, pass);
                statement.setBytes(3,Avatar.createAvatar(name));
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean auth(String name, String pass) throws Exception {
        init();
        try (Connection connection = getConnection()) {
            @Language("SQL")
            String query_02 = "SELECT * FROM users";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query_02);
                while (rs.next()) {
                    if (rs.getString("name").equals(name) && rs.getString("password").equals(pass)) return true;
                }
                return false;
            }
        }
    }

    public static byte[] getAvatar(String name) throws Exception {
        init();
        try (Connection connection = getConnection()) {
            @Language("SQL")
            String query = "SELECT * FROM users";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    if (rs.getString("name").equals(name)) return rs.getBytes("avatar");
                }
            }
        }
        return null;
    }


}
