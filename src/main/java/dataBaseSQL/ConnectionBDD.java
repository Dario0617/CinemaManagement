package dataBaseSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBDD {

    public static Connection ConnectionBDD(){

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/learnit", "root", "");
        } catch (SQLException ex) {
            //Handle any errors
            System.out.println("SQLException : " +ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
        }

        return conn;

    }

}
