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
import java.util.HashMap;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author LPrice
 */
@Named(value = "imageController")
@SessionScoped
public class ImageController implements Serializable {
    
    private String userInfo;
    private String coordString;
    private Map<String, String> map;

    public ImageController() {
        userInfo = "";
        coordString = "";
        
        MainDaoImpl dao = new MainDaoImpl();
        map = dao.getCoordNameMap();
    }
    
    public void setUserInfoDB(){
        
        userInfo = map.get(coordString);
        System.out.println("Must find: " + coordString);
        System.out.println("Found: " + map.get(coordString));
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

    /**
     * @return the coordString
     */
    public String getCoordString() {
        return coordString;
    }

    /**
     * @param coordString the coordString to set
     */
    public void setCoordString(String coordString) {
        this.coordString = coordString;
    }

    /**
     * @return the map
     */
    public Map<String, String> getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
    
}
