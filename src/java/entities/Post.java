/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author Vladimir
 */
public class Post implements Serializable {
    private String id, name, login, text;

    public Post(String id, String login, String fullName, String text) {
	this.id = id;
	this.name = fullName;
	this.login = login;
	this.text = text;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    
    
    
    
}
