package handler;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends Config {
    private Connection dbConnection;

    public Connection getDBConnection() throws ClassNotFoundException, SQLException {
        if (dbConnection == null) {
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        }
        return dbConnection;
    }
}
