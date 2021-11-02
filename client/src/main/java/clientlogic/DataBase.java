package clientlogic;

import org.intellij.lang.annotations.Language;

import java.sql.*;

public class DataBase implements AuthService {

    private static DataBase instance;

    public DataBase() throws Exception {
        createTable();
        instance = this;
    }

    public static DataBase getInstance() throws Exception {
        if(instance == null) instance = new DataBase();
        return instance;
    }

    private static void init() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }


    public static Connection getConnection() throws SQLException {
        @Language("SQL")
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String pass = "root";
        return DriverManager.getConnection(url, user, pass);
    }

    private static void createTable() throws Exception {
        init();
        try(Connection connection = getConnection()) {
            @Language("SQL")
            String query_01 = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100), password VARCHAR(100));";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query_01);
            }
        }
    }

    public static void resultSet() throws Exception {
        init();
        try(Connection connection = getConnection()) {
            @Language("SQL")
            String query_02 = "SELECT * FROM users";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query_02);
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " : " + rs.getString("name") + " ; " + rs.getString("password"));
                }
            }
        }
    }

    public static void delete(int ID) throws Exception {
        init();
        try(Connection connection = getConnection()) {
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
        try(Connection connection = getConnection()) {
            @Language("SQL")
            String query_01 = "INSERT INTO users (name, password) VALUES (?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query_01)) {
                statement.setString(1, name);
                statement.setString(2, pass);
                statement.executeUpdate();
                return true;
            }catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean auth(String name, String pass) throws Exception {
        init();
        try(Connection connection = getConnection()) {
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
}
