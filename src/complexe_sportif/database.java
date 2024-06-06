/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package complexe_sportif;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class database {

    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String DB_NAME = "complexe_sportif";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection ;


   public static Connection getConnect (){
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
            System.out.println("cooooooooooooooooooonexted");
        } catch (SQLException ex) {        return null;

        }

        return  connection;
    }


    
}