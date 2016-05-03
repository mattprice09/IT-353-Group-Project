package controller;

import services.SOAPClientSAAJ;
import dao.MainDaoImpl;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@Named(value = "transactionController")
@SessionScoped
public class TransactionController implements Serializable{

    private int userNum;
    private int numDonations;
    private String totalCost;
    private String customName;
    private String costStr;
    private String token;
    private String payerID;
    private final DecimalFormat df = new DecimalFormat("#.00");
    
    /**
     * Creates a new instance of TransactionController
     */
    public TransactionController() {
        userNum = 0;
        numDonations = 0;
        costStr = "";
        totalCost = "";
        customName = "";
    }
    
    private void navigateTo(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
        nav.performNavigation(url + "?faces-redirect=true");
    }

    // get current page string
    private String getCurrentPage() {
        FacesContext context = FacesContext.getCurrentInstance(); 
        String currPage = context.getViewRoot().getViewId();
        
        // Don't return to the login page after logging in
        if (currPage.equals("/login.xhtml")) {
            currPage = "/index.xhtml";
        }
        return "faces" + currPage;
    }
    
    public void updateCost() {
        double cost = (((double)numDonations) * 0.22);
        totalCost = "$" + this.df.format(cost);
        costStr = "Your current total is: " + totalCost;
    }
    
    public void purchase() throws IOException {
        updateCost();
        
        // Request token from PayPal
        SOAPClientSAAJ requester = new SOAPClientSAAJ();
        token = requester.getPurchaseToken(this.numDonations);
        
        // Redirect user to PayPal
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + token);
    }
    
    public void confirmPurchase() {
        MainDaoImpl dao = new MainDaoImpl();
        int response = dao.makePurchase(userNum, numDonations);
        
        // Do something?
        if (response != -1) {
//            masterBean.getCurrentUser().
        }
        SOAPClientSAAJ saaj = new SOAPClientSAAJ();
        payerID = saaj.getPayerID(token);
        
        String responsePage = saaj.executePurchase(token, payerID, numDonations);
        
        // Reset transactionController vars if purchase was successful
        if (responsePage.equals("faces/thankyou.xhtml")) {
//            numDonations = 0;
            costStr = "";
            totalCost = "";
        }
        navigateTo(responsePage);
    }

    /**
     * @return the userNum
     */
    public int getUserNum() {
        return userNum;
    }

    /**
     * @param userNum the userNum to set
     */
    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    /**
     * @return the numDonations
     */
    public int getNumDonations() {
        return numDonations;
    }

    /**
     * @param numDonations the numDonations to set
     */
    public void setNumDonations(int numDonations) {
        this.numDonations = numDonations;
        double cost = (((double)numDonations) * 0.22);
        totalCost = "$" + this.df.format(cost);
        if (numDonations == 0) {
            this.costStr = "Please enter a value greater than 0.";
        } else if (numDonations > 1000000) {
            this.costStr = "Please enter a value of less than 1,000,000.";
        } else {
            this.costStr = "Your current total is: $" + this.df.format((((double)numDonations) * 0.22));
        }
    }

    /**
     * @return the costStr
     */
    public String getCostStr() {
        return costStr;
    }

    /**
     * @param costStr the costStr to set
     */
    public void setCostStr(String costStr) {
        this.costStr = costStr;
    }   

    /**
     * @return the customName
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * @param customName the customName to set
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * @return the totalCost
     */
    public String getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the payerID
     */
    public String getPayerID() {
        return payerID;
    }

    /**
     * @param payerID the payerID to set
     */
    public void setPayerID(String payerID) {
        this.payerID = payerID;
    }
}
