package migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseMigration {

    public static void applyMigration(Connection connection) throws SQLException {
        try {
            boolean isLoginApplied=login(connection);
            System.out.println(isLoginApplied);
//            boolean isUserDetailsApplied=userDetailsTable(connection);
//            System.out.println(isUserDetailsApplied);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean login(Connection connection) throws SQLException {

        final String query = "CREATE TABLE userLogin (user_id VARCHAR(36) NOT NULL PRIMARY KEY, email VARCHAR(50) NOT NULL UNIQUE,password varchar(50))";

        try {
            Statement statement = connection.createStatement();
            return statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean userDetailsTable(Connection connection) throws SQLException {
        final String query = "CREATE TABLE usersDetails( user_id VARCHAR(36) NOT NULL PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, email VARCHAR(100) UNIQUE, phone VARCHAR(20),    is_active BOOLEAN NOT NULL DEFAULT true,    password VARCHAR(255) NOT NULL, address TEXT)";
        try {
            Statement statement = connection.createStatement();
            return statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
