/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.MainDAO;
import dao.MainDaoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author LPrice
 */
@Named(value = "dashboardController")
@SessionScoped
public class DashboardController implements Serializable {

    private int progress;
    private int numSold;
    private int numRemaining;
    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
        int total = 1000230;
        updateProgress();
    }
    
    public int updateProgress(){
        int total = 1000230;
        MainDaoImpl aUserDAO = new MainDaoImpl();    // Creating a new object each time.
        numSold = aUserDAO.getNumPixels(); // Doing anything with the object after this?
        numRemaining = total - numSold;
        progress = numSold / total;
        return progress;
    }

    /**
     * @return the progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * @return the numSold
     */
    public int getNumSold() {
        return numSold;
    }

    /**
     * @param numSold the numSold to set
     */
    public void setNumSold(int numSold) {
        this.numSold = numSold;
    }

    /**
     * @return the numRemaining
     */
    public int getNumRemaining() {
        return numRemaining;
    }

    /**
     * @param numRemaining the numRemaining to set
     */
    public void setNumRemaining(int numRemaining) {
        this.numRemaining = numRemaining;
    }
    
}
