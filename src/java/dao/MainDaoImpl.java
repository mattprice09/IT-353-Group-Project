package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.User;

public class MainDaoImpl implements MainDAO {

    @Override
    public int createUser(User user) {
        
        // Copied DB code formatting from Dr. Bee Lim's ProfileMatchApp
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/FinalProject";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "itkstu");
            String temp = "";
            
            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO FinalProject.Users VALUES ('"
                    + ("" + user.getUserNum()).replace("'", "''")
                    + "','" + user.getFirstName().replace("'", "''")
                    + "','" + user.getLastName().replace("'", "''")
                    + "','" + user.getHomeState().replace("'", "''")
                    + "','" + user.getCountry().replace("'", "''")
                    + "','" + user.getUserName().replace("'", "''")
                    + "','" + user.getPassword().replace("'", "''")
                    + "','" + ("" + user.getNumDonated()).replace("'", "''")
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
        return 0;
//        // Create credentials in UserLogin table
//        int authStatus = updatePassword(user);
//        if (authStatus == 0) {
//            return 0;
//        }
//        
//        // Copied DB code formatting from Dr. Bee Lim's ProfileMatchApp
//        try {
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//        } catch (ClassNotFoundException e) {
//            System.err.println(e.getMessage());
//            System.exit(0);
//        }
//
//        int rowCount = 0;
//        try {
//            String myDB = "jdbc:derby://localhost:1527/FinalProject";
//            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "itkstu");
//            String temp = "";
//            
//            String updateString;
//            Statement stmt = DBConn.createStatement();
//            updateString = "UPDATE Project353.Users Set "
//                    + "firstName = '" + user.getFirstName().replace("'", "''") + "', "
//                    + "lastName = '" + user.getLastName().replace("'", "''") + "', "
//                    + "email = '" + user.getEmail().replace("'", "''") + "', "
//                    + "securityQuestion = '" + user.getSecurityQuestion().replace("'", "''") + "', "
//                    + "securityAnswer = '" + user.getSecurityAnswer().replace("'", "''") + "' "
//                    + "WHERE userid = '" + user.getUserID().replace("'", "''") + "'";
//
//            rowCount = stmt.executeUpdate(updateString);
//            System.out.println("insert string =" + updateString);
//            DBConn.close();
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//
//        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
//        return rowCount;
    }
    
    @Override
    public int authenticate(User user) {
        int success = 0;
        
        String userName = user.getUserName();
        String password = user.getPassword();
        
        String query = "SELECT * FROM ITKSTU.Users " + "WHERE userName='"
                + userName + "'";
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/FinalProject";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "itkstu");
        
        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // Authenticate
            if (rs.next()) {
                if (userName.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
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
    public boolean idExists(String userName) {
        boolean found = false;
        
        String query = "SELECT * FROM FinalProject.Users " + "WHERE userName='"
                + userName + "'";
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/FinalProject";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "itkstu");

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
    
    public User getUser(String userName) {
        User user = new User();
        
        String query = "SELECT * FROM ITKSTU.Users " + "WHERE userName='"
                + userName + "'";
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/FinalProject";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "itkstu");

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
                
                // Assign values
                user.setUserNum(rs.getInt("usernum"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setHomeState(rs.getString("homestate"));
                user.setCountry(rs.getString("country"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNumDonated(rs.getInt("numdonated"));
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
