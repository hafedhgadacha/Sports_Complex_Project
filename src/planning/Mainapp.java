package planning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mainapp {

    public static void main(String[] args) {
        try {
            // JDBC URL, username, and password of MySQL server
            String url = "jdbc:mysql://127.0.0.1:3306/complexe_sportif";
            String user = "root";
            String password = "";

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Create a table based on the grid dimensions
            int numRows = 7; // Number of rows in your grid
            int numCols = 7; // Number of columns in your grid
            String tableName = "planningbasket";

            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + tableName + " (");

            for (int col = 1; col <= numCols; col++) {
                for (int row = 1; row <= numRows; row++) {
                    createTableQuery.append("row").append(row).append("_col").append(col).append(" VARCHAR(255), ");
                }
            }

            // Remove the trailing comma and space
            createTableQuery.delete(createTableQuery.length() - 2, createTableQuery.length());
            createTableQuery.append(")");

            // Execute the create table query
            statement.executeUpdate(createTableQuery.toString());

            System.out.println("Table created successfully.");

            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
