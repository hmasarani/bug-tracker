package com.bugtracker.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres";
        String user = "postgres.dymjhodproctpkapkujo";  // Your PostgreSQL username
        String password = "Hmasaran1234@";                // Your PostgreSQL password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
