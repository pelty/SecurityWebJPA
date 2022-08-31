/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.servlet;


import fr.devprojet.dl.securitywebjpa.entities.User;
import fr.devprojet.dl.securitywebjpa.security.CookieUtils;
import fr.devprojet.dl.securitywebjpa.security.SessionUtils;
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
 * @author dev-pro
 */
@WebServlet(urlPatterns = {"/profiluser"})
public class ProfilServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public ProfilServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CookieUtils.initCookie(req, resp);// #################   Ah supprimer  #############
        HttpSession session = req.getSession();
        User userSession = SessionUtils.getLoginedUser(session);        
        
        req.setAttribute("admin", "ADMIN");
        RequestDispatcher dispatcher = this.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/user/profilUser.jsp");
        dispatcher.forward(req, resp);
        
    }        
}
