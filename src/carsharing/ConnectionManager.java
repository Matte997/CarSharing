package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private static final String TBL_COMPANY = "COMPANY";
    private static final String TBL_CAR = "CAR";
    private static final String TBL_CUSTOMER = "CUSTOMER";

    private static Connection conn = null;
    private static Statement statement = null;

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            try {
                conn = DriverManager.getConnection(DB_URL);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return conn;
    }

    public static void createTableCompanyAndTableCar() {
        try {
            Class.forName(JDBC_DRIVER);

            conn = getConnection();
            statement = conn.createStatement();

            String dropTableCustomer = "DROP TABLE IF EXISTS CUSTOMER";
            String dropTableCar = "DROP TABLE IF EXISTS CAR";
            String dropTableCompany = "DROP TABLE IF EXISTS COMPANY";

            statement.executeUpdate(dropTableCustomer);
            statement.executeUpdate(dropTableCar);
            statement.executeUpdate(dropTableCompany);

            String tableCompanyCreation = "CREATE TABLE IF NOT EXISTS " + TBL_COMPANY + "(" +
                    "ID INT AUTO_INCREMENT NOT NULL, " +
                    "NAME VARCHAR(50) UNIQUE NOT NULL," +
                    "PRIMARY KEY (ID))";

            String tableCarCreation = "CREATE TABLE IF NOT EXISTS " + TBL_CAR + "(" +
                    "ID INT AUTO_INCREMENT, " +
                    "NAME VARCHAR(100) NOT NULL UNIQUE, " +
                    "COMPANY_ID INT NOT NULL, " +
                    "PRIMARY KEY (ID), " +
                    "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";

            String tableCustomerCreation = "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMER + "(" +
                    "ID INT AUTO_INCREMENT, " +
                    "NAME VARCHAR(100) NOT NULL UNIQUE, " +
                    "RENTED_CAR_ID INT, " +
                    "PRIMARY KEY (ID), " +
                    "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";

            statement.executeUpdate(tableCompanyCreation);
            statement.executeUpdate(tableCarCreation);
            statement.executeUpdate(tableCustomerCreation);

            // for testing purpose
            String alterTableCompanyId = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1";
            String alterTableCarId = "ALTER TABLE CAR ALTER COLUMN ID RESTART WITH 1";
            String alterTableCustomerId = "ALTER TABLE CUSTOMER ALTER COLUMN ID RESTART WITH 1";

            statement.executeUpdate(alterTableCompanyId);
            statement.executeUpdate(alterTableCarId);
            statement.executeUpdate(alterTableCustomerId);

            statement.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
