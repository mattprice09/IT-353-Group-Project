package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.User;

public class MainDaoImpl implements MainDAO {
    
    @Override
    public int updateUser(User user) {
        return 0;
    }
    
    //Ryan's code
    @Override
    public int createUser(User user) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            if(!checkIfUsed(user.getUserName())){
                String insertString;
                Statement stmt = DBConn.createStatement();
                insertString = "INSERT INTO USERS (userNum, firstName, lastName, homeState, country, userName, password) VALUES("
                        + createNextUserNum()
                        + ",'" + user.getFirstName()
                        + "','" + user.getLastName()
                        + "','" + user.getState()
                        + "','" + user.getCountry()
                        + "','" + user.getUserName()
                        + "','" + user.getPassword()
                        + "')";      

                rowCount = stmt.executeUpdate(insertString);
                 System.out.println("insert string =" + insertString);
            }
            else{
                rowCount = 2;//error code for username already used
            }
           
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }
    
    public boolean checkIfUsed(String userName) {
        Connection DBConn = null;
        boolean exists = false;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            PreparedStatement stmt = DBConn.prepareStatement("SELECT userName FROM USERS WHERE userName = ?");
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                exists = true;
            }
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return exists;
    }
    
    //automatically creates userNum primary keys
    public String createNextUserNum() {

        String query = "SELECT MAX(userNum) FROM USERS";
        String numStr = "0";
        int num;
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                numStr = rs.getString(1);
            }
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        num = Integer.parseInt(numStr) + 1;
        //System.out.print(rs.getString(1));
        return Integer.toString(num);
    }
    //End Ryan's Code
    
    // Retrieve the # of donations
    public String getNumDonations() {

        String query = "SELECT MAX(pixelEnd) FROM DONATIONS";
        String numStr = "0";
        int num;
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                numStr = rs.getString(1);
            }
            if(numStr == null){
                numStr = "0";
            }
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //System.out.print(rs.getString(1));
        return numStr;
    }
    
    public String createNextDonationNum() {

        String query = "SELECT MAX(donationNum) FROM DONATIONS";
        String numStr = "0";
        int num;
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                numStr = rs.getString(1);
            }
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        num = Integer.parseInt(numStr) + 1;
        //System.out.print(rs.getString(1));
        return Integer.toString(num);
    }
    
    public int makePurchase(int userNum, int numDonations) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int fistPixel = Integer.parseInt(getNumDonations()) + 1;
        int lastPixel = fistPixel + numDonations - 1;
        if(lastPixel > 1000230){
            lastPixel = 1000230;
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
                
                String insertString;
                Statement stmt = DBConn.createStatement();
                insertString = "INSERT INTO DONATIONS (donationNum, userNum, pixelStart, pixelEnd) VALUES("
                        + createNextDonationNum()
                        + "," + userNum
                        + "," + fistPixel
                        + "," + lastPixel
                        + ")";      

                rowCount = stmt.executeUpdate(insertString);
                System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
    
    public int updateUserNumDonated(int user, int additionalDonations) {
        int numDonated = 0;
        String query1 = "SELECT NUMDONATED FROM USERS "
                + "WHERE USERNUM = " + user;
        String query2;
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            System.out.println(query1);
            ResultSet rs = stmt.executeQuery(query1);
            if(rs.next()){
                numDonated = Integer.parseInt(rs.getString(1));
                numDonated += additionalDonations; 
            }
            query2 = "UPDATE USERS "
                + "SET NUMDONATED = " + numDonated +
                " WHERE USERNUM = " + user;
            
            System.out.println(query2);
            stmt.executeUpdate(query2);
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        //System.out.print(rs.getString(1));
        return numDonated;
    }
    
    public int getUsernumFromDonation(int pixelNum){
        String query = "SELECT USERNUM FROM DONATIONS "
                + "WHERE PIXELSTART <= " + pixelNum + "AND"
                + "PIXELEND >= " + pixelNum;
        String userNum = "";
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/MealProject";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                userNum = rs.getString(1);
            }
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        //System.out.print(rs.getString(1));
        return Integer.parseInt(userNum);
    }
    
     @Override
    public int authenticate(User user) {
        int success = 0;
        
        String userName = user.getUserName();
        String password = user.getPassword();
        
        if (userName.equals("") || password.equals("")) {
            return 0;
        }
        
        System.out.println("these ar the vals of current user!!!!!!!");
        System.out.println(userName + "    " + password);
        
        String query = "SELECT * FROM USERS " + "WHERE userName='"
                + userName + "'";
        
        System.out.println(query);
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/MealProject";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        
        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("these ar the vals of the result set!!!!!!!");
            //System.out.println(rs.getString("userName") + "    " + rs.getString("password"));
            // Authenticate
            if (rs.next()) {
                if (userName.equals(rs.getString(6)) && password.equals(rs.getString(7))) {
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
        
        String query = "SELECT * FROM Users " + "WHERE userName='"
                + userName + "'";
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/MealProject";
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
                
                // Assign values
                user.setUserNum(rs.getInt("usernum"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setState(rs.getString("homestate"));
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
