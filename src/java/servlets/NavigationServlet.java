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
import javax.servlet.http.HttpSession;

import util.Utility;
import entities.User;

/**
 *
 * @author Vladimir
 */
public class NavigationServlet extends HttpServlet {

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
	if (user == null) {
	    response.sendRedirect("pages/welcome.html");
	} else {
	    String userQuery = request.getParameter("u");
	    if (userQuery == null || userQuery.equals(user.getLogin())) {
		response.sendRedirect("pages/home.jsp");
	    }else{
		User searchfor = Utility.getUser(userQuery);
		if(searchfor == null){
		    response.sendRedirect("pages/home.jsp?searchfail=true");
		}else{
		    response.sendRedirect("pages/guest.jsp?u="+searchfor.getLogin());
		}
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
	
    }

}
