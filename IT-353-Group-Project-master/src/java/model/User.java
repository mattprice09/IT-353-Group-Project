package model;

public class User {
    private int userNum;
    private String firstName;
    private String lastName;
    private String state;
    private String country;
    private String userName;
    private String password;
    private int numDonated;

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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
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
     * @return the state
     */
    public String getState() {
        if(state == null)
            return "";
        else
            return state;
    }

    /**
     * @param state the homeState to set
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
        this.country = country;
    }

    /**
     * @return the numDonated
     */
    public int getNumDonated() {
        return numDonated;
    }

    /**
     * @param numDonated the numDonated to set
     */
    public void setNumDonated(int numDonated) {
        this.numDonated = numDonated;
    }
}