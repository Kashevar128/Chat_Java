package clientlogic;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;


public class App {

    private static Connection connection;

    private Connection getConnection() throws SQLException {
        try {
            System.out.println(Class.forName("org.h2.Driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:h2:~/test");
    }

    @Before
    public void init() throws SQLException {
        connection = getConnection();
    }
    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void shouldGetJDBCConnection() throws SQLException {
        try (Connection connection = getConnection()) {
            assertTrue(connection.isValid(1));
            assertTrue(connection.isClosed());
        }
    }
}


