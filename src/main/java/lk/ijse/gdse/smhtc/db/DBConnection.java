package lk.ijse.gdse.smhtc.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dBConnection;

    private final Connection connection;

    private DBConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/Supermarketfx?createDatabaseIfNotExist=true";
        String USER = "root";
        String PASSWORD = "@317Kns20020317";
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        return dBConnection == null ? dBConnection = new DBConnection() : dBConnection;
    }

    //kamathinam lombok tiyena nisa @Getter danna puluwan
    public Connection getConnection(){
        return connection;
    }
}
