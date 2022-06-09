package com.example.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBase {
    private static Connection connection;

    private static int creationTimeout = 5;
    private static int connectionTimeout = 3;

    public static void initDB(String filePath) {
        System.out.println("Initializing DataBase");
        File DB = new File(filePath);

        if (DB.exists()) {
            try {
                System.out.println("File Found");
                connect(DB.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File not Found");
            if (creationTimeout != 0) {
                creationTimeout = creationTimeout - 1;
                generateNewDB(DB);
            } else {
                System.err.println("Creation Timeout exceeded");
            }
        }
    }

    private static void connect(String url) {

        try {
            if (connectionTimeout != 0) {
                connectionTimeout = connectionTimeout - 1;
                url = "jdbc:sqlite:" + url;
                setConnection(DriverManager.getConnection(url));
                System.out.println("Connection to SQLite has been established.");
            } else {
                System.err.println("Connection Timout Exceeded");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateNewDB(File DB) {
        System.out.println("Creating new DataBase");
        try {
            DB.createNewFile();
            System.out.println("DataBase Created Successfully Youpppiiiiiii !!!!!");
        } catch (IOException e) {
            System.err.println("Failed to Create new DataBase");
        }
        try {
            connect(DB.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataBase.createTables(DataBase.getConnection());
    }

    private static void createTables(Connection connection) {
        Statement statement = null;
        String sqlCreateUsersQuery = "CREATE TABLE USERS " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " username           CHAR(50)    NOT NULL, " +
                " password            CHAR(50)     NOT NULL, " +
                "isAdmin            BOOLEAN," +
                "image             BLOB     , " +
                "date        TEXT          NOT NULL,"+
                "email        TEXT          NOT NULL,"+
                "phoneNumber  TEXT          NOT NULL)";
        String sqlCreateProductsQuery = "CREATE TABLE Products " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " name           char(100)    NOT NULL," +
                "description     TEXT," +
                "image            BLOB," +
                "date        TEXT          NOT NULL,"+
                "price       DOUBLE        NOT NULL,"+
                "quantity    INTEGER       NOT NULL)";
        String sqlCreateFournisseurQuery = "CREATE TABLE Fournisseurs " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " name           CHAR(50)    NOT NULL," +
                "date        TEXT          NOT NULL)";
        String sqlCreateCommandeQuery = "CREATE TABLE Commandes " +
                "(ID      INT  PRIMARY KEY    NOT NULL," +
                " userID           INT      NOT NULL, " +
                " date        TEXT          NOT NULL,"  +
                "totalPrice    DOUBLE       NOT NULL,"   +
                "isAccepted     BOOLEAN     NOT NULL)";

        String sqlCreateCommandeProductQuery = "CREATE TABLE CommandeProducts " +
        "(CommandeID          INT      NOT NULL," +
        " ProductID           INT    NOT NULL)";
        String sqlCreateUserCommandesQuery = "CREATE TABLE CommandesUser " +
        "(CommandeID          INT      NOT NULL," +
        " UserID           INT    NOT NULL)";
        String sqlCreateFournisseurProductsQuery = "CREATE TABLE FournisseurProducts " +
                "(ProductID  INT PRIMARY KEY     NOT NULL," +
                " FournisseurID           INT    NOT NULL)";

        String addAdmin = "INSERT INTO USERS (ID,username,password,isAdmin,image,date,email,phoneNumber) " +
                "VALUES (0, 'Root', 'Root',true,null,datetime('now'),'Root@gmail.com','+212642965547');";
        
        try {
            statement = connection.createStatement();

            statement.executeUpdate(sqlCreateUsersQuery);
            statement.executeUpdate(sqlCreateProductsQuery);
            statement.executeUpdate(sqlCreateFournisseurQuery);
            statement.executeUpdate(sqlCreateCommandeQuery);
            statement.executeUpdate(sqlCreateFournisseurProductsQuery);
            statement.executeUpdate(sqlCreateUserCommandesQuery);
            statement.executeUpdate(sqlCreateCommandeProductQuery);
            statement.executeUpdate(addAdmin);
            statement.close();
        } catch (SQLException e) {
            System.err.println("SQL query failed");
            e.printStackTrace();
        }
    }

    public static void endConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        DataBase.connection = connection;
    }
}
