/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.User;

/**
 *
 * @author LPrice
 */
public interface MainDAO {
    
    public int createUser(User user);
    public int updateUser(User user);
    public int authenticate(User user);
    public boolean idExists(String userID);
}
