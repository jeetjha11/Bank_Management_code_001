package App;

import connectionConfig.EstablishConnection;
import migration.DataBaseMigration;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

public class BankApp {


    public static void main(String[] args) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Welcome>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("\n \n \n");

        // making Database Connection
        Connection connection;
        try {
            connection = EstablishConnection.makeConnection();
            System.out.println("DB CONNECTED");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // apply migration for first time

        try {
            DataBaseMigration.applyMigration(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Authenticating User;


    }
}
