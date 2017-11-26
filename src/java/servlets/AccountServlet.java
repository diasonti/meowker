/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.*;
import entities.User;

/**
 *
 * @author Vladimir
 */
public class AccountServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String action = request.getParameter("action");
	if(action == null){
	    
	}else{
	    
	    if(action.equals("signout")){
		
		User u = Utility.getSession(request);
		if(u != null){
		    Utility.destroySession(response, u);
		}
		
	    }
	    
	}
	
	response.sendRedirect("nav");
	
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String action = request.getParameter("action");
	if(action == null){
	    
	}else{
	    
	    if(action.equals("signin")){
		
		String login = (String)request.getAttribute("login");
		String password = (String)request.getAttribute("password");
		User user = Utility.getUser(login, password);
		try(PrintWriter out = response.getWriter()){
		    if(user != null){
			Utility.createSession(response, user);
			out.print("0");
		    }
		}
		
	    }else if(action.equals("signup")){
		
		String fullname = (String)request.getAttribute("fullname");
		String login = (String)request.getAttribute("login");
		String password = (String)request.getAttribute("password");
		boolean loginUnique = Utility.isLoginUnique(login);
		try(PrintWriter out = response.getWriter()){
		    if(loginUnique){
			Utility.registerUser(login, password, fullname);
			out.print("0");
		    }
		}
		
	    }else if(action.equals("follow")){
		
		String login = request.getParameter("login");
		User currentUser = Utility.getSession(request);
		boolean success = Utility.follow(currentUser.getLogin(), login);
		try(PrintWriter out = response.getWriter()){
		    if(success)
			out.print("true");
		}
		
	    }else if(action.equals("unfollow")){
		
		String login = request.getParameter("login");
		User currentUser = Utility.getSession(request);
		boolean success = Utility.unfollow(currentUser.getLogin(), login);
		try(PrintWriter out = response.getWriter()){
		    if(success)
			out.print("true");
		}
		
	    }else if(action.equals("followcheck")){
		
		String login = request.getParameter("login");
		User currentUser = Utility.getSession(request);
		boolean following = Utility.isFollowing(currentUser.getLogin(), login);
		try(PrintWriter out = response.getWriter()){
		    if(following)
			out.print("true");
		}
		
	    }
	    
	}
    }

}
