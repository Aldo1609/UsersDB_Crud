package org.aldob.util;

import java.sql.Connection;

public class ConexionDB {

    private static final String URL= "jdbc:mysql://localhost:3306/projectomantenedorusuariosjdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    private ConexionDB() {
            throw new IllegalStateException("Utility class");
    }

    public static Connection getConexion(){
        Connection conn;
        try{
            conn = java.sql.DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (java.sql.SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return conn;
    }
}
