/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.security;

import fr.devprojet.dl.securitywebjpa.entities.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dev-pro
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {

    public SecurityFilter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest wrapRequest = request;
        String servletPath = request.getServletPath();

        // L'informstion d'utilisateur est stockée dans Session
        // (Après l'achèvement de connexion).
        User loginedUser = SessionUtils.getLoginedUser(request.getSession());
        
        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        
        if (loginedUser != null) {
            // User Name
            String username = loginedUser.getUsername();
            
            // Des rôles (Role).
            String roles = String.valueOf(loginedUser.getRole());
            
            // Envelopper l'ancienne demande (request) par une nouvelle demande avec les informations pseudo et Roles.
            wrapRequest = new UserRoleRequestWrapper(username, roles, request);
        }        
        
        // Les pages doivent être connectées.
        if (SecurityUtils.isSecurityPage(request)) {
            // Si l'utilisateur n'est pas connecté,
            // Redirect (redirigez) vers la page de connexion
            if (loginedUser == null) {
                String requestUri = request.getRequestURI();

                // Stockez la page en cours à rediriger après l'achèvement de la connexion.
                int redirectId = SessionUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);                
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }
                        
            // Vérifiez si l'utilisateur a un rôle valide?
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/security/testAuth.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

}