/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author LPrice
 */
public class User {

    private String userNum;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String securityQuestion;
    private String securityAnswer;
    private String state;
    private String country;

    public User(){
        
    }
    public User(String userNum, String firstName, String lastName, String state, String country, String userName, String password) {
        this.userNum = userNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.state = state;
        this.country = country;
        this.userName = userName;
        this.password = password;
    }
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the userID
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * @return the securityAnswer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * @param securityAnswer the securityAnswer to set
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        System.out.println("The is the country!!!!!!!!!!!!!: " + country);
        this.country = country;
    }

    /**
     * @return the userNum
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * @param userNum the userNum to set
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
