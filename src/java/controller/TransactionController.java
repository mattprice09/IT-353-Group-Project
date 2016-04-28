package controller;

import dao.MainDaoImpl;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;

@Named(value = "transactionController")
@SessionScoped
public class TransactionController implements Serializable{

    private int userNum;
    private int numDonations;
    private String costStr;
    
    @ManagedProperty(value="#{LoginController}")
    private LoginController masterBean;
    
    /**
     * Creates a new instance of TransactionController
     */
    public TransactionController() {
        userNum = 0;
        numDonations = 0;
        costStr = "";
    }
    
    public void updateCost() {
        costStr = "Your current total is: $" + (((double)numDonations) * 0.22);
    }
    
    public String purchase() {
        updateCost();
        return "faces/thankyou.xhtml";
    }
    
    public String testBuy() {
        MainDaoImpl dao = new MainDaoImpl();
        
        int response = dao.makePurchase(userNum, numDonations);
        
        if (response != -1) {
//            masterBean.getCurrentUser().
        }
        
        // error
//        if (response == -1) {
//            // return error page
//        }
        return "faces/index.xhtml";
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
    
}
