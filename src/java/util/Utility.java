/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.*;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.util.ArrayList;

/**
 *
 * @author Vladimir
 */
public class Utility {
    
    // Database connection details
    private static final String DBURL = "jdbc:postgresql://localhost:2009/meowker";
    private static final String DBLOGIN = "netbeans";
    private static final String DBPASSWORD = "netbeans";
    
    public static User getSession(HttpServletRequest request) {
	Cookie[] cookies = request.getCookies();
	Cookie session = null;
	if(cookies == null)
	    cookies = new Cookie[0];
	for(Cookie c : cookies){
	    if(c.getName().equals("session")){
		session = c;
		break;
	    }
	}
	if(session == null){
	    return null;
	}else{
	    String login = session.getValue();
	    return getUser(login);
	}
    }
    
    public static void createSession(HttpServletResponse response, User user){
	Cookie session = new Cookie("session", user.getLogin());
	session.setHttpOnly(true);
	session.setMaxAge(1800);
	response.addCookie(session);
    }
    
    public static void destroySession(HttpServletResponse response, User user){
	Cookie session = new Cookie("session", "expired");
	session.setHttpOnly(true);
	session.setMaxAge(0);
	response.addCookie(session);
    }
    
    public static void extendSession(HttpServletResponse response, User user){
	createSession(response, user);
    }
    
    public static User getUser(String login){
	User user = null;
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT fullName FROM users WHERE login='" + login + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    if(rs.next()){
		String fullName = rs.getString(1);
		user = new User(login, fullName);
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
	return user;
    }
    
    public static User getUser(String login, String password){
	User user = null;
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT fullName FROM users WHERE login='" + login + "' AND password='" + password + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    if(rs.next()){
		String fullName = rs.getString(1);
		user = new User(login, fullName);
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
	return user;
    }
    
    public static void registerUser(String login, String password, String fullname){
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "INSERT INTO users (login, password, fullname) VALUES ('"+login+"', '"+password+"', '"+fullname+"')";
	    stmt.executeUpdate(sql);
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    public static boolean isLoginUnique(String login){
	boolean unique = true;
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT login FROM users WHERE login='" + login + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    if(rs.next()){
		unique = false;
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
	return unique;
    }
    
    private static String getFullName(String login){
	String fullName = null;
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT fullName FROM users WHERE login='" + login + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    if(rs.next()){
		fullName = rs.getString(1);
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
	return fullName;
    }
    
    public static String[] getNewMeows(String login, String fromid){
	String[] list = new String[0];
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT * FROM meows WHERE author='"+login+"' AND id>"+fromid+" ORDER BY id ASC";
	    ResultSet rs = stmt.executeQuery(sql);
	    ArrayList<String> alist = new ArrayList<>();
	    while(rs.next()){
		alist.add(rs.getString(1) + " " + rs.getString(2) + " " + getUser(rs.getString(2)).getFullName() + " " + rs.getString(3));
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	    
	    if(alist.isEmpty())
		return list;
	    
	    list = new String[alist.size()];
	    for(int i = 0; i < alist.size(); i++){
		list[i] = alist.get(i);
	    }
	    
	}catch(Exception e){
	    e.printStackTrace();
	}
	return list;
    }
 
    public static String[] getNewFeed(User user, String fromid){
	
	String[] feed = new String[0]; // id login fullname text\n
	String[] follows = getFollows(user);
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT * FROM meows WHERE id>"+fromid+" AND (author='"+user.getLogin()+"'";
	    for(String login : follows){
		sql += " OR author='"+login+"'";
	    }
	    sql += ") ORDER BY id ASC";
	    if(fromid.equals("0")){
		sql += " LIMIT 25";
	    }
	    ResultSet rs = stmt.executeQuery(sql);
	    ArrayList<String> alist = new ArrayList<>();
	    while(rs.next()){
		alist.add(rs.getString(1) + " " + rs.getString(2) + " " + getUser(rs.getString(2)).getFullName() + " " + rs.getString(3));
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	    feed = new String[alist.size()];
	    for(int i = 0; i < alist.size(); i++){
		feed[i] = alist.get(i);
	    }
	}catch(Exception e){
	    e.printStackTrace();
	}
	return feed;
	
    }
    
    public static String[] getFollows(User user){
	String[] list = new String[0];
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT follow FROM links WHERE follower='"+user.getLogin()+"'";
	    ResultSet rs = stmt.executeQuery(sql);
	    ArrayList<String> alist = new ArrayList<>();
	    while(rs.next()){
		alist.add(rs.getString(1));
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	    list = new String[alist.size()];
	    for(int i = 0; i < alist.size(); i++){
		list[i] = alist.get(i);
	    }
	}catch(Exception e){
	    e.printStackTrace();
	}
	return list;
    }
    
    public static void postMeow(User poster, String text){
	try{
	    //Class.forName("org.postgresql.Driver");
	    Connection conn = DriverManager.getConnection(DBURL);
	    Statement stmt = conn.createStatement();
	    String sql = "INSERT INTO meows (author, text) VALUES ('"+poster.getLogin()+"', '"+text+"')";
	    stmt.executeUpdate(sql);
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    
}
