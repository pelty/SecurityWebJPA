/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.servlet;

import fr.devprojet.dl.securitywebjpa.dao.DaoFactory;
import fr.devprojet.dl.securitywebjpa.dao.UserImplement;
import fr.devprojet.dl.securitywebjpa.entities.User;
import fr.devprojet.dl.securitywebjpa.security.CookieUtils;
import fr.devprojet.dl.securitywebjpa.security.SessionUtils;
import fr.devprojet.dl.securitywebjpa.security.UserUtils;
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
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //private final UserImplement userImplement =  DaoFactory.getInstance().getUserImplement();


    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        initDisptacher(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        User user = UserUtils.findByName(userName, password);
        System.out.println("LoginServlet doPost() user "+ userName.toString());
        if (user == null) {
            String errorMessage = "Username ou mot de passe invalide";
            request.setAttribute("errorMessage", errorMessage);

            initDisptacher(request, response);
            return;
        }
        
        CookieUtils.storeUserNameCookie(response, user);
        HttpSession session = request.getSession();
        SessionUtils.storeSessionUser(session, user);    
        SessionUtils.storeLoginedUser(request.getSession(), user);

        int redirectId = -1;
        try {redirectId = Integer.parseInt(request.getParameter("redirectId"));} 
        catch (Exception e) {}
        
        String requestUri = SessionUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
        if (requestUri != null) response.sendRedirect(requestUri);
        else {
            // Par défaut, après l'achèvement de la connexion; redirigez à la page /profiluser
            response.sendRedirect(request.getContextPath()+"/profiluser");
        }
    }
    
    public void initDisptacher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = SessionUtils.getLoginedUser(session);
        if(user != null){
            request.setAttribute("admin", user.getRole());
        }
        
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/security/loginView.jsp");
        dispatcher.forward(request, response);
    }

}