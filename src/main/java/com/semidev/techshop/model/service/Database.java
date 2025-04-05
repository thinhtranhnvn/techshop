package com.semidev.techshop.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class Database {
    
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static Connection connection;

    static {
        URL = "jdbc:mysql://localhost:3306/" + "techshop";
        USERNAME = "admin";
        PASSWORD = "123456789";
    }

    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
    
}
