/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * User class
 * @author Vladimir
 */
public class User {
    
    private String login, fullName;
    
    /**
     * Default constructor
     */
    public User(){
	login = "";
	fullName = "";
    }

    /**
     * Parametrised constructor
     * @param login user's login
     * @param fullName user's full name
     */
    public User(String login, String fullName){
	this.login = login;
	this.fullName = fullName;
    }

    /**
     * Get login
     * @return login
     */
    public String getLogin() {
	return login;
    }

    /**
     * Set login
     * @param login new login
     */
    public void setLogin(String login) {
	this.login = login;
    }

    /**
     * Get full name
     * @return full name
     */
    public String getFullName() {
	return fullName;
    }

    /**
     * Set full name
     * @param fullName new full name
     */
    public void setFullName(String fullName) {
	this.fullName = fullName;
    }
    
    
}
