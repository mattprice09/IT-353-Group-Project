package controller;

import dao.MainDAO;
import dao.MainDaoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import model.User;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private User currentUser;
    private int numAttempts;
    private String loggedIn;
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        this.currentUser = new User();
        this.numAttempts = 0;
        this.loggedIn = "N";
    }
    
    public String authenticate() {
        MainDAO aUserDAO = new MainDaoImpl();    // Creating a new object each time.
        int validPass = aUserDAO.authenticate(currentUser); // Doing anything with the object after this?
        if(validPass == 1){
            this.loggedIn = "Y";
            return "index.xhtml";
        }
        return "faces/index.xhtml";
//        MainDaoImpl dao = new MainDaoImpl();
////        if (numAttempts == 3) {
////            return "MaxAttempts.xhtml";
////        }
//        int status = dao.authenticate(this.currentUser);
//        if (status == 1) {
//            currentUser = (User) dao.getUser(currentUser.getUserID());
//            this.loggedIn = true;
//            return "update.xhtml";
//        } else {
//            numAttempts++;
//            return "LoginBad.xhtml";
//        }
    }
    
    // Update's the current user's login info
    public String updateUser() {
        MainDaoImpl dao = new MainDaoImpl();
        int response = dao.updateUser(this.currentUser);
        
        if (response == 0) {
            return "error.xhtml";
        }
        return "update.xhtml";
    }
    
    // Incomplete
    public String registerUser() {
        MainDAO aUserDAO = new MainDaoImpl();    // Creating a new object each time.
        int rowCount = aUserDAO.createUser(currentUser); // Doing anything with the object after this?
        System.out.println(rowCount);
        if (rowCount == 1)
            return "response.xhtml"; // navigate to "response.xhtml"
        else
            return "register.xhtml";
    }

    /**
     * @return the currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the numAttempts
     */
    public int getNumAttempts() {
        return numAttempts;
    }

    /**
     * @param numAttempts the numAttempts to set
     */
    public void setNumAttempts(int numAttempts) {
        this.numAttempts = numAttempts;
    }

    /**
     * @return the loggedIn
     */
    public String getLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
    }
    
}
