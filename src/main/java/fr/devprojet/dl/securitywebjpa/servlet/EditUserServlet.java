/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.servlet;

import fr.devprojet.dl.securitywebjpa.entities.User;
import fr.devprojet.dl.securitywebjpa.security.UserUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dev-pro
 */
@WebServlet(urlPatterns = {"/edituser"})
public class EditUserServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idUserEditString = req.getParameter("idedituser");
        User user = UserUtils.findIdeditUser(idUserEditString);
        
        if(user!=null){
            req.setAttribute("users", user);
            initDispatcher(req, resp);
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime dateNow = LocalDateTime.now();
        long idUser = Long.parseLong(req.getParameter("id"));
        String gender = req.getParameter("gender");
        String role = req.getParameter("role");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String errMsg = null;        
        
        if(!gender.isEmpty() && !role.isEmpty() && !password.isEmpty() && !username.isEmpty() && idUser > -1){
            boolean result = UserUtils.updateUser(gender, role, username, password, idUser, dateNow);
            if (result) {
                resp.sendRedirect(req.getContextPath()+"/home");
                return;}
            else{
                errMsg = "La mise à jour ne c'est pas effectué";
                req.setAttribute("errMsg",errMsg);
            }}
        else{
            errMsg = "Les champs ne sont pas remplis";
            req.setAttribute("errMsg", errMsg);
        }
        resp.sendRedirect(req.getContextPath()+"/home");
        
    }
    
    
    private void initDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setAttribute("admin", "ADMIN");
        RequestDispatcher dispatcher = req
                .getRequestDispatcher("/WEB-INF/views/user/editUser.jsp");
        dispatcher.forward(req, resp);
    }
    
}
