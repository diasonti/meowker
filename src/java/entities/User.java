/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Vladimir
 */
public class User {
    
    private String login, fullName;
    
    public User(){
	login = "";
	fullName = "";
    }
    public User(String login, String fullName){
	this.login = login;
	this.fullName = fullName;
    }

    public String getLogin() {
	return login;
    }
    public void setLogin(String login) {
	this.login = login;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }
    
    
}
