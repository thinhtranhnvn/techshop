package com.semidev.techshop.model.service;

import java.sql.Connection;
import java.sql.DriverManager;


class Database {
    private static String url;
    private static String username;
    private static String password;
    private static Connection connection;

    static {
        url = "jdbc:mysql://localhost:3306/" + "techshop";
        username = "admin";
        password = "123456789";
    }

    public static Connection getConnection() throws Exception {
        try {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (Exception exc) {
            throw exc;
        }
    }

    public static void closeConnection() throws Exception {
        try {
            connection.close();
        }
        catch (Exception exc) {
            throw exc;
        }
    }

    public static String escapeString(String data) {
        return data.replaceAll("\\\\", "\\\\\\\\")
                   .replaceAll("\b","\\b")
                   .replaceAll("\n","\\n")
                   .replaceAll("\r", "\\r")
                   .replaceAll("\t", "\\t")
                   .replaceAll("\\x1A", "\\Z")
                   .replaceAll("\\x00", "\\0")
                   .replaceAll("'", "\\'")
                   .replaceAll("\"", "\\\"");
    }

    public static String unescapeString(String data) {
        return data.replaceAll("\\\\\\\\", "\\\\")
                   .replaceAll("\\b", "\b")
                   .replaceAll("\\n", "\n")
                   .replaceAll("\\r", "\r")
                   .replaceAll("\\t", "\t")
                   .replaceAll("\\Z", "\\x1A")
                   .replaceAll("\\0", "\\x00")
                   .replaceAll("\\'", "'")
                   .replaceAll("\\\"", "\"");
    }
}
