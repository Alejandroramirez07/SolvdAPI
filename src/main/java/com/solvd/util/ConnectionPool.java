package com.solvd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionPool {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = dotenv.get("USER");
    private static final String PASSWORD = dotenv.get("PASSWORD");
    private static final int POOL_SIZE = 10;

    private static BlockingQueue<Connection> pool;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            pool = new ArrayBlockingQueue<>(POOL_SIZE);
            for (int i = 0; i < POOL_SIZE; i++) {
                pool.add(DriverManager.getConnection(URL, USER, PASSWORD));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing connection pool", e);
        }
    }

    public static Connection getConnection() throws InterruptedException {
        return pool.take();
    }

    public static void releaseConnection(Connection conn) {
        if (conn != null) pool.offer(conn);
    }
}
