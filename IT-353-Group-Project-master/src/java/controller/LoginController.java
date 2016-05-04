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
    private String prevPage;
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        this.currentUser = new User();
        this.numAttempts = 0;
        this.loggedIn = "N";
        this.prevPage = "/index.xhtml";
    }

    public void authenticate() {
        MainDaoImpl dao = new MainDaoImpl();
        
        int status = dao.authenticate(currentUser);
        if (status == 1) {
            currentUser = (User) dao.getUser(currentUser.getUserName());
            loggedIn = "Y";
            navigateTo(getCurrentPage().replace("faces", ""));
        } else {
            numAttempts++;
            navigateTo("login.xhtml");
        }
    }
    
    public void navigateTo(String url) {
        prevPage = getCurrentPage().replace("faces", "");
        FacesContext fc = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
        nav.performNavigation(url + "?faces-redirect=true");
    }

    // get current page string
    private String getCurrentPage() {
        FacesContext context = FacesContext.getCurrentInstance(); 
        String currPage = context.getViewRoot().getViewId();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");
        System.out.println(this.prevPage);
        System.out.println(currPage);
        // Don't return to the login page after logging in
        if (currPage.equals("/login.xhtml")) {
            
            // unless returning to a previous page
            if (prevPage.equals("/donationPage.xhtml")) {
                currPage = prevPage;
            } else {
                currPage = "/index.xhtml";
            }
        }
        return "faces" + currPage;
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
    public void registerUser() {
        MainDAO aUserDAO = new MainDaoImpl();    // Creating a new object each time.
        int rowCount = aUserDAO.createUser(currentUser); // Doing anything with the object after this?
        System.out.println(rowCount);
        if (rowCount == 1) {
//            return "faces/response.xhtml"; // navigate to "response.xhtml"
            loggedIn = "Y";
            navigateTo("response.xhtml");
        } else 
//            return "faces/register.xhtml";
            navigateTo("register.xhtml");
    }

    // Redirect user if they're not logged in
    public String checkLoggedIn(ComponentSystemEvent event) {
        String navi = null;
        System.out.println("Logged in: " + loggedIn);
        if (loggedIn.equals("N")) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("login.xhtml?faces-redirect=true");
        }
        return navi;
    }

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return getCurrentPage() + "?faces-redirect=true";
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

    /**
     * @return the prevPage
     */
    public String getPrevPage() {
        return prevPage;
    }

    /**
     * @param prevPage the prevPage to set
     */
    public void setPrevPage(String prevPage) {
        this.prevPage = prevPage;
    }

}
