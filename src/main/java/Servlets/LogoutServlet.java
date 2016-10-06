/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DidrikKvanvik
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        HttpSession session = req.getSession();
        session.invalidate();
        RequestDispatcher rd = req.getRequestDispatcher("/index.xhtml"); //the URL to redirect after logout
        rd.forward(req,res);
    }
  
}
