/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.MainDaoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import model.User;

/**
 *
 * @author LPrice
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private User currentUser;
    private int numAttempts;
    private boolean loggedIn;
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        this.currentUser = new User();
        this.numAttempts = 0;
        this.loggedIn = false;
    }
    
//    public String isLoggedIn(ComponentSystemEvent event) {
//        String navi = null;
//        if (!loggedIn) {
//            FacesContext fc = FacesContext.getCurrentInstance();
//            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
//            nav.performNavigation("login.xhtml?faces-redirect=true");
//        }
//        return navi;
//    }
    
    public String authenticate() {
        return "update.xhtml";
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
    
}
