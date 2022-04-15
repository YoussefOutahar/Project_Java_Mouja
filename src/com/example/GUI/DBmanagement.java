package com.example.GUI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.image.BufferedImage;

import com.example.Product;
import com.example.Accounts.Admin;
import com.example.Accounts.User;
import com.example.DataBase.DBget;
import com.example.DataBase.DBset;
import com.example.DataBase.DataBase;
import com.example.GUI.TestUI.AdminScreen;
import com.example.GUI.TestUI.UserScreen;

public class DBmanagement {
    public static boolean signIn(String login, String password) {
        int ID = DBmanagement.searchForUsers(login, password);
        if (ID == -1)
            return false;
        if (ID == -2)
            return false;
        DBmanagement.checkIfAdmin(ID);
        if (DBmanagement.checkIfAdmin(ID)) {
            Admin admin = DBget.getAdmin(ID);
            AdminScreen.startAdminScreen();
            return true;
        } else {
            User user = DBget.getUser(ID);
            UserScreen.startUserScreen();
            return true;
        }
    }

    public static int searchForUsers(String login, String password) {
        int ID = -1;
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT ID,username,password FROM Users";
        try {
            statement = DataBase.getConnection().createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                if (login.equals(resultSet.getString("username"))) {
                    if (password.equals(resultSet.getString("password"))) {
                        ID = resultSet.getInt("ID");
                    } else {
                        ID = -2;
                        System.out.println("Incorrect password");
                    }
                    break;
                }
            }
            if (ID == -1) {
                System.out.println("User not Found");
            } else {
                System.out.println("User Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }

    public static boolean checkIfAdmin(int ID) {
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT ID,isAdmin FROM Users";
        try {
            statement = DataBase.getConnection().createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                if (resultSet.getInt("isAdmin") == 1 && resultSet.getInt("ID") == ID) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean signUp(String login, String password, BufferedImage image, String email, String phoneNumber) {
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT username FROM Users";
        try {
            statement = DataBase.getConnection().createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(login)) {
                    System.out.println(resultSet.getString("username"));
                    System.out.println("User Already Exists");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBset.addUser(login, password, false, image, email, phoneNumber);
        System.out.println("User created");
        return true;
    }

    public static LinkedList<Product> selectProducts() {
        LinkedList<Product> products = new LinkedList<Product>();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT ID FROM Products;";
        try {
            statement = DataBase.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                products.add(DBget.getProduct(resultSet.getInt("ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static TableModel getUsersTableModel() {
        String[] columns = new String[] {
            "Id", "Name", "Password", "Email","PhoneNumber","Creation Date"
        };
        int userCount = DBget.getUserCount();
        LinkedList<User> admins = DBget.getAllUsers();
        Object[][] data = new Object[userCount][6];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 6; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = admins.get(i).getId();
                        break;
                    case 1:
                        data[i][j] = admins.get(i).getUsername();
                        break;
                    case 2:
                        data[i][j] = admins.get(i).getPassword();
                        break;
                    case 3:
                        data[i][j] = admins.get(i).getEmail();
                        break;
                    case 4:
                        data[i][j] = admins.get(i).getPhoneNumber();
                        break;
                    case 5:
                        data[i][j] = admins.get(i).getDate();
                        break;
                    default:
                        break;
                }
            }
        }
        TableModel model = new DefaultTableModel(data,columns);
        return model;
    }

    public static TableModel getAdminsTableModel() {
        String[] columns = new String[] {
            "Id", "Name", "Password", "Email","PhoneNumber","Creation Date"
        };
        int userCount = DBget.getAdminCount();
        LinkedList<Admin> admins = DBget.getAllAdmins();
        Object[][] data = new Object[userCount][6];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 6; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = admins.get(i).getId();
                        break;
                    case 1:
                        data[i][j] = admins.get(i).getUsername();
                        break;
                    case 2:
                        data[i][j] = admins.get(i).getPassword();
                        break;
                    case 3:
                        data[i][j] = admins.get(i).getEmail();
                        break;
                    case 4:
                        data[i][j] = admins.get(i).getPhoneNumber();
                        break;
                    case 5:
                        data[i][j] = admins.get(i).getDate();
                        break;
                    default:
                        break;
                }
            }
        }
        TableModel model = new DefaultTableModel(data,columns);
        return model;
    }

    public static TableModel getProductTableModel(){
        String[] columns = new String[] {
            "Id", "Name", "Price", "image","Creation Date"
        };
        int productCount = DBget.getProductCount();
        LinkedList<Product> products = DBget.getAllProducts();
        Object[][] data = new Object[productCount][6];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = products.get(i).getId();
                        break;
                    case 1:
                        data[i][j] = products.get(i).getName();
                        break;
                    case 2:
                        data[i][j] = products.get(i).getPrice();
                        break;
                    case 3:
                        data[i][j] = new ImageIcon(products.get(i).getProductPicture());
                        break;
                    case 4:
                        data[i][j] = products.get(i).getDate();
                        break;
                    default:
                        break;
                }
            }
        }
        TableModel model = new DefaultTableModel(data,columns);
        return model;
    }

    public static void removeSelectedUserRows(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        for (int i = rows.length - 1; i >= 0; i--) {
            DBset.delUser(Integer.parseInt(table.getModel().getValueAt(i,0).toString()));
            model.removeRow(rows[i]);
        }
    }

    public static void removeSelectedProductRows(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        for (int i = rows.length - 1; i >= 0; i--) {
            DBset.delProduct(Integer.parseInt(table.getModel().getValueAt(i,0).toString()));
            model.removeRow(rows[i]);
        }
    }
}
