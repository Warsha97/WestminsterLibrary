package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public Connection connectingDB(){
        //setting the connection to the database using jdbc connectivity
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_of_westminster", "root", "");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR IN CONNECTION TO DATA BASE");
            return null;

        }

    }
}
