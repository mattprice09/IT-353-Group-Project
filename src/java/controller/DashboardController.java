/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
        progress = 34;
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
    
}
