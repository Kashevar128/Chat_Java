package clientlogic;

import java.sql.SQLException;

public interface AuthService {

    public void addUser(String name, String pass) throws SQLException;

    boolean auth(String name, String pass);
}
