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
 * Class containing set of methods used for database handling
 * @author Vladimir Danilov
 */
public final class Utility {
    
    // Database connection details
    private static final String DBURL = "jdbc:postgresql://localhost:2009/meowker";
    private static final String DBLOGIN = "netbeans";
    private static final String DBPASSWORD = "netbeans";
    
    private static Connection getConnection() throws ClassNotFoundException, SQLException{
	Class.forName("org.postgresql.Driver");
	Connection connection = DriverManager.getConnection(DBURL, DBLOGIN, DBPASSWORD);
	return connection;
    }
    
    /**
     * Retrieves current current user or null if no session
     * @param request Servlet request
     * @return <code>User</code> object of current session or null if there is no session
     */
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
    
    /**
     * Creates new user session
     * @param response servlet response
     * @param user <code>User</code> object of current user
     */
    public static void createSession(HttpServletResponse response, User user){
	Cookie session = new Cookie("session", user.getLogin());
	session.setHttpOnly(true);
	session.setMaxAge(1800);
	response.addCookie(session);
    }
    
    /**
     * Destroys existing user session
     * @param response servlet response
     * @param user
     */
    public static void destroySession(HttpServletResponse response, User user){ // TODO remove user arg
	Cookie session = new Cookie("session", "expired");
	session.setHttpOnly(true);
	session.setMaxAge(0);
	response.addCookie(session);
    }
    
    /**
     * Extends existing user session
     * @param response servlet response
     * @param user <code>User</code> object
     */
    public static void extendSession(HttpServletResponse response, User user){
	createSession(response, user);
    }
    
    /**
     * Retrieves <code>User</code> object from the database, if it exists
     * @param login login of the user
     * @return <code>User</code> object or null
     */
    public static User getUser(String login){
	User user = null;
	try{
	    Connection conn = getConnection();
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
    
    /**
     * Retrieves <code>User</code> object from the database, if it exists and credentials are valid
     * @param login login
     * @param password password
     * @return <code>User</code> object or null
     */
    public static User getUser(String login, String password){
	User user = null;
	try{
	    Connection conn = getConnection();
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
    
    /**
     * Adds a new user in the database
     * @param login login
     * @param password password
     * @param fullname fullname
     */
    public static void registerUser(String login, String password, String fullname){
	try{
	    Connection conn = getConnection();
	    Statement stmt = conn.createStatement();
	    String sql = "INSERT INTO users (login, password, fullname) VALUES ('"+login+"', '"+password+"', '"+fullname+"')";
	    stmt.executeUpdate(sql);
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    /**
     * Check if there is no such login in the database
     * @param login login
     * @return true if the login is unique, otherwise false
     */
    public static boolean isLoginUnique(String login){
	boolean unique = true;
	try{
	    Connection conn = getConnection();
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
	    Class.forName("org.postgresql.Driver");
	    Connection conn = getConnection();
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
    
    /**
     * Retrieves the newest posts of the user
     * @param login login of the user
     * @param fromid id of the post to start from
     * @return Array of posts
     */
    public static String[] getNewMeows(String login, String fromid){
	String[] list = new String[0];
	try{
	    Connection conn = getConnection();
	    Statement stmt = conn.createStatement();
	    String sql = "SELECT * FROM meows WHERE author='"+login+"' AND id>"+fromid+" ORDER BY id ASC";
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
 
    /**
     * Retrieves new posts of user and all users they follow
     * @param user <code>User</code> object
     * @param fromid id of post to start from
     * @return Array of posts
     */
    public static String[] getNewFeed(User user, String fromid){
	
	String[] feed = new String[0]; // id login fullname text\n
	String[] follows = getFollows(user);
	try{
	    Connection conn = getConnection();
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
    
    /**
     * Retrieves logins of users who followed the user passed as argument
     * @param user <code>User</code> object
     * @return Array of logins
     */
    public static String[] getFollows(User user){
	String[] list = new String[0];
	try{
	    Connection conn = getConnection();
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
    
    /**
     * Adds new post to the database.
     * @param poster User object of author
     * @param text text of post
     */
    public static void postMeow(User poster, String text){
	try{
	    Connection conn = getConnection();
	    Statement stmt = conn.createStatement();
	    String sql = "INSERT INTO meows (author, text) VALUES ('"+poster.getLogin()+"', '"+text+"')";
	    stmt.executeUpdate(sql);
	    stmt.close();
	    conn.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    /**
     * Private default constructor prevents class from being instantiated
     */
    private Utility() {
    }
}
