package pl.ur.travel.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if(conn == null) {
            conn = DriverManager.getConnection("jdbc:sqlite:travel.db");
        }

        return conn;
    }
}
