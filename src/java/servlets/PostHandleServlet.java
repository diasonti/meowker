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

import util.Utility;
import entities.User;

/**
 *
 * @author Vladimir
 */
public class PostHandleServlet extends HttpServlet {

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
	
	User user = Utility.getSession(request);
	if(user == null){
	    response.sendRedirect("nav");
	    return;
	}
	Utility.extendSession(response, user);
	String target = request.getParameter("target");
	if("feed".equals(target)){
	    
	    String from = request.getParameter("from");
	    String feed = Utility.getNewFeed(user, from);
	    response.setContentType("application/json;charset=UTF-8");
	    try(PrintWriter out = response.getWriter()){
		out.print(feed);
	    }
	    
	}else if("own".equals(target)){
	    String login = request.getParameter("login");
	    String from = request.getParameter("from");
	    String posts = Utility.getNewMeows(login, from);
	    response.setContentType("application/json;charset=UTF-8");
	    try(PrintWriter out = response.getWriter()){
		out.print(posts);
	    }
	    
	}
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
	User user = Utility.getSession(request);
	if(user == null){
	    response.sendRedirect("nav");
	    return;
	}
	Utility.extendSession(response, user);
	String text = (String)request.getAttribute("text");
	Utility.postMeow(user, text);
	response.getWriter().print("0");
    }

}
