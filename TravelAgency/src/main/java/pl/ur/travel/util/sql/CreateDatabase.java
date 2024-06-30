package pl.ur.travel.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDatabase {

    // creates database
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:travel.db");

        connection.prepareStatement(SQLs.CREATE_TABLE_COSTS).execute();
        connection.prepareStatement(SQLs.CREATE_TABLE_OFFER_COSTS).execute();
        connection.prepareStatement(SQLs.CREATE_TABLE_OFFER).execute();

    }
}
