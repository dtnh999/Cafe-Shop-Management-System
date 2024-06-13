package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.DatabaseMetaData;


public class database {
    public static Connection connectDB() {
        try {
            // Load the JDBC driver for SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Connect to the SQL Server database
            String url = "jdbc:sqlserver://MSI;databaseName=FoodStall;encrypt=false;";
            String user = "sa";
            String password = "123";

            Connection connect = DriverManager.getConnection(url, user, password);
            return connect;
        } catch (ClassNotFoundException e) {
            System.err.println("SQLServerDriver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed. Check the URL, username, and password.");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Connection connection = connectDB();
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
