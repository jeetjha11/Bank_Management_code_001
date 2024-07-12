package connectionConfig;

import configSource.ConfigSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EstablishConnection {


    public static Connection makeConnection() throws ClassNotFoundException, SQLException {
        ConfigSource configSource = new ConfigSource();
        Class.forName(configSource.getDB_MANAGER_URL());
        return DriverManager.getConnection
                (configSource.getDB_URL(), configSource.getUSER(), configSource.getDB_PASSWORD());

    }


}
