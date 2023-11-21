package org.aldob.util;

import java.sql.Connection;

public class ConexionDB {

    private static String url= "jdbc:mysql://localhost:3306/projectomantenedorusuariosjdbc";
    private static String username = "root";
    private static String password = "root";
    private static Connection conn;

    public static Connection getConexion(){
        try{
            if(conn == null){
                conn = java.sql.DriverManager.getConnection(url, username, password);
            }
        }catch (java.sql.SQLException e){
            throw new RuntimeException(e);
        }
        return conn;
    }

}
