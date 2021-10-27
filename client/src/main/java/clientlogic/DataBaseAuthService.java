package clientlogic;

import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DataBaseAuthService implements AuthService {
    private static  DataBaseAuthService instance;
    private DataBase db;

    private DataBaseAuthService()  {
        db = new DataBase();
    }

    public static void main(String[] args) throws SQLException {
//        getInstance().addUser("Слава", "pass");
//        getInstance().addUser("Серега", "pass01");
        getInstance();
        DataBase.resultSet();
    }

    public static  DataBaseAuthService getInstance() {
        if (instance == null) {
            instance = new DataBaseAuthService();
        }
        return instance;
    }

    @Override
    public void addUser(String name, String pass) throws SQLException {
        @Language("SQL")
                String query_01 = "INSERT INTO users (name, password) VALUES (?,?)";
        try (PreparedStatement statement = db.getConnection().prepareStatement(query_01)) {
            statement.setString(1, name);
            statement.setString(2, pass);
            statement.executeUpdate();
        }
    }

    @Override
    public boolean auth(String name, String pass) throws SQLException {
        @Language("SQL")
                String query_02 = "SELECT * FROM users";
        try (Statement statement = db.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery(query_02);
            while (rs.next()) {
                if (rs.getString("name").equals(name) && rs.getString("password").equals(pass)) return true;
            }
            return false;
        }
    }

}
