package configSource;

public class ConfigSource {

    final String DB_MANAGER_URL = "com.mysql.cj.jdbc.Driver";
    final String DB_NAME = "bank_db";
    final String DB_PASSWORD = "root";
    final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    final String USER = "root";

    public ConfigSource() {

    }

    public String getDB_MANAGER_URL() {
        return DB_MANAGER_URL;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public String getDB_PASSWORD() {
        return DB_PASSWORD;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getUSER() {
        return USER;
    }
}
