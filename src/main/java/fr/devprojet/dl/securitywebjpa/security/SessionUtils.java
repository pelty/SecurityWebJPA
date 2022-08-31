/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.security;

import fr.devprojet.dl.securitywebjpa.entities.User;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dev-pro
 */
public class SessionUtils {
    private static final String SESSION_USER_KEY = "SESSION_USER_KEY";
    //public static final String SESSION_LOGINED_USER = "LOGINED_USER";
    private static final String SESSION_LOGINED_USER = "loginedUser";
    private static final Map<Integer, String> ID_URI_MAP = new HashMap<Integer, String>();
    private static final Map<String, Integer> URI_ID_MAP = new HashMap<String, Integer>();

    private static int REDIRECT_ID = 0;

    
    //Stock la session user
    public static void storeSessionUser(HttpSession session, User user) {
        session.setAttribute(SESSION_USER_KEY, user);
    }

    //recupere la key de l'inscription du user
    public static User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute(SESSION_USER_KEY);
        return sessionUser;
    }
    
    // Stockez l'information d'utilisateur dans Session.
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        session.setAttribute(SESSION_LOGINED_USER, loginedUser);        
    }

    // Obtenez de l'information d'utilisateur stockée dans Session.
    public static User getLoginedUser(HttpSession session) {
        User loginedUser = (User) session.getAttribute(SESSION_LOGINED_USER);
        return loginedUser;
    }
    
    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {        
        Integer id = URI_ID_MAP.get(requestUri);
        if (id == null) {
            id = REDIRECT_ID++;
            URI_ID_MAP.put(requestUri, id);
            ID_URI_MAP.put(id, requestUri);
            return id;
        }
        return id;
    }

    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        String url = ID_URI_MAP.get(redirectId);
        if (url != null) return url;
        
        return null;
    }
    
    

}
