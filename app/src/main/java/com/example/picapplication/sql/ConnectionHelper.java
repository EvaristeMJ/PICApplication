package com.example.picapplication.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private final String url = "jdbc:postgresql://localhost/dontknow";
    private final String user = "sa";
    private final String password = "pic";

    public static Connection connection() throws SQLException {
        /*
            Connect to PostgreSQL database
            return a Connection object
        */
        return DriverManager.getConnection("jdbc:postgresql://localhost/dontknow","sa","pic");
    }
}
