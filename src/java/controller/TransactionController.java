package controller;

import dao.MainDaoImpl;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named(value = "transactionController")
@SessionScoped
public class TransactionController implements Serializable{

    private int userNum;
    private int numDonations;
    
    /**
     * Creates a new instance of TransactionController
     */
    public TransactionController() {
        userNum = 0;
        numDonations = 0;
    }
    
    public String testBuy() {
        MainDaoImpl dao = new MainDaoImpl();
        
        int response = dao.makePurchase(userNum, numDonations);
        
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
    
}
