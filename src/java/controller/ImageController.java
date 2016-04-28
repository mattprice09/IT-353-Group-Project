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
import java.util.Random;

/**
 *
 * @author LPrice
 */
@Named(value = "imageController")
@SessionScoped
public class ImageController implements Serializable {
    
    private String userInfo = "";

    private void getUserInfoDB(int x, int y){
        MainDaoImpl dao = new MainDaoImpl();
        
        int userNum = dao.getUsernumFromDonation((y * 1155) + x);
        System.out.println(userNum);
    }
    
    private String message;
    /**
     * Creates a new instance of ImageController
     */
    public ImageController() {
        message = "";
    }
    
    public void setMessage(String msg) {
        message = msg;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String updateMessage(int x, int y) {
        String output = "X-coord is " + x + ", y-coord is " + y;
        return output;
    }

    /**
     * @return the userInfo
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
    
}
