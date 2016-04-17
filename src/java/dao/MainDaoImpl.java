/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import model.User;

/**
 *
 * @author LPrice
 */
public class MainDaoImpl implements MainDAO {

    @Override
    public int createUser(User user) {
        
        // Create credentials in UserLogin table
        int authStatus = createCredentials(user);
        if (authStatus == 0) {
            return 0;
        }
        
        // Copied DB code formatting from Dr. Bee Lim's ProfileMatchApp
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String temp = "";
            
            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO Project353.Users VALUES ('"
                    + user.getFirstName().replace("'", "''")
                    + "','" + user.getLastName().replace("'", "''")
                    + "','" + user.getUserID().replace("'", "''")
                    + "','" + user.getEmail().replace("'", "''")
                    + "','" + user.getSecurityQuestion().replace("'", "''")
                    + "','" + user.getSecurityAnswer().replace("'", "''")
                    + "')";

            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }
    
    @Override
    public int updateUser(User user) {
        
        // Create credentials in UserLogin table
        int authStatus = updatePassword(user);
        if (authStatus == 0) {
            return 0;
        }
        
        // Copied DB code formatting from Dr. Bee Lim's ProfileMatchApp
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String temp = "";
            
            String updateString;
            Statement stmt = DBConn.createStatement();
            updateString = "UPDATE Project353.Users Set "
                    + "firstName = '" + user.getFirstName().replace("'", "''") + "', "
                    + "lastName = '" + user.getLastName().replace("'", "''") + "', "
                    + "email = '" + user.getEmail().replace("'", "''") + "', "
                    + "securityQuestion = '" + user.getSecurityQuestion().replace("'", "''") + "', "
                    + "securityAnswer = '" + user.getSecurityAnswer().replace("'", "''") + "' "
                    + "WHERE userid = '" + user.getUserID().replace("'", "''") + "'";

            rowCount = stmt.executeUpdate(updateString);
            System.out.println("insert string =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }
    
    public int createCredentials(User user) {
        // Copied DB code formatting from Dr. Bee Lim's ProfileMatchApp
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String temp = "";
            
            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO Project353.LoginInfo VALUES ('"
                    + user.getUserID().replace("'", "''")
                    + "','" + user.getPassword().replace("'", "''")
                    + "')";

            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }
    
    public int updatePassword(User user) {
        if (user.getPassword() == null) {
            return 1;
        }
        // Copied DB code formatting from Dr. Bee Lim's ProfileMatchApp
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String temp = "";
            
            String updateString;
            Statement stmt = DBConn.createStatement();
 
            updateString = "UPDATE Project353.LoginInfo Set "
                    + "password = '" + user.getPassword() + "' "
                    + "WHERE userid = '" + user.getUserID() + "'";

            rowCount = stmt.executeUpdate(updateString);
            System.out.println("insert string =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }

    @Override
    public int authenticate(User user) {
        int success = 0;
        
        String userID = user.getUserID();
        String password = user.getPassword();
        
        String query = "SELECT * FROM Project353.LoginInfo " + "WHERE userID='"
                + userID + "'";
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        
        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // Authenticate
            if (rs.next()) {
                if (userID.equals(rs.getString("userid")) && password.equals(rs.getString("password"))) {
                    success = 1;
                }
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return success;
    }

    @Override
    public boolean idExists(String userID) {
        boolean found = false;
        
        String query = "SELECT * FROM Project353.LoginInfo " + "WHERE userID='"
                + userID + "'";
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // If the result set has data, then the ID exists
            if (rs.next()) {
                found = true;
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return found;
    }
    
    public User getUser(String userID) {
        User user = new User();
        
        String query = "SELECT * FROM Project353.Users " + "WHERE userID='"
                + userID + "'";
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // If the result set has data, then the ID exists
            int count = 0;
            while (rs.next()) {
                count++;
                
                // Multiple users. Return 'null' User object
                if (count > 1) {
                    user = null;
                    break;
                }
                
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setUserID(rs.getString("userid"));
                user.setEmail(rs.getString("email"));
                user.setSecurityQuestion(rs.getString("securityquestion"));
                user.setSecurityAnswer(rs.getString("securityanswer"));
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return user;
    }
}
