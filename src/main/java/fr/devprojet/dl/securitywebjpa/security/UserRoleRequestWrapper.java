/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.security;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author dev-pro
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper{
    private String user;
    private String roles;
    private HttpServletRequest realRequest;

    public UserRoleRequestWrapper(String user, String roles, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.roles = roles;
        this.realRequest = request;
    }
    
    @Override
    public boolean isUserInRole(String role) {
        if (roles == null) {
            return this.realRequest.isUserInRole(role);
        }
        return roles.contains(role);
    }


    @Override
    public Principal getUserPrincipal() {
        if (this.user == null) {
            return realRequest.getUserPrincipal();
        }

        // Faire une implémentation anonyme pour simplement renvoyer notre utilisateur
        return new Principal() {
            @Override
            public String getName() {
                return user;
            }
        };
    }
}
