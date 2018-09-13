/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author erwin
 */
public class dbConnection {
    public Connection connection ;

    public Connection getConnection()throws Exception{
    
    String dbName = "ta_dd";
    String username="root";
    String password="";
    
        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/"+dbName,username,password);
            System.out.println("Success to connect "+dbName);
            return connection;
        } catch (Exception e) {
            System.out.println(e);
        }
    
    
    return null;
    
    }
}
