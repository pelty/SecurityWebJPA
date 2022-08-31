/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.security;

import fr.devprojet.dl.securitywebjpa.dao.DaoFactory;
import fr.devprojet.dl.securitywebjpa.dao.UserImplement;
import fr.devprojet.dl.securitywebjpa.entities.Gender;
import fr.devprojet.dl.securitywebjpa.entities.Role;
import fr.devprojet.dl.securitywebjpa.entities.User;
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;


/**
 *
 * @author dev-pro
 */
public class UserUtils {
    private static final UserImplement userImplement =  DaoFactory.getInstance().getUserImplement();
    
    
    
    /**
     * La fonction creer un utilisateur avec les parametres ajouté
     * controle si l'utilisateur hexiste deja 
     * @param session
     * @param username
     * @param password
     * @param password2
     * @param gender
     * @param role
     * @param currentUser
     * @return 
     */
    public static String createUser(HttpSession session, String username, String password,String password2, Gender gender, Role role, String currentUser){
        String result = null;
        
        if(!username.isEmpty() && !password.isEmpty() && !password2.isEmpty() && gender!=null){
            if(password.equals(password2)){
                LocalDateTime dateCreate = LocalDateTime.now();
                User pseudoData = null;
                try {pseudoData = userImplement.findByName(username);} 
                catch (Exception e) {}
                
                if(pseudoData == null){
                    User user = new User(username, password, dateCreate, role, gender);
                    try{userImplement.create(user);}
                    catch(Exception e){}
                    
                    switch(currentUser){
                        case "ADMIN":
                            result = "Utilisateur ajouté";
                            break;
                        default:
                            SessionUtils.storeSessionUser(session, user);
                            return result = "creation utilisateur reussi";
                    }                    
                }else result = "Le pseudo est deja utilisé";
            }else result = "Mot de passe diffèrent";
            
        }else result = "Vous devez remplir tous les champs";
        
        return result; 
    }
    
    /**
     * La fonction renvoi le role passé en parametre
     * @param roleString
     * @return 
     */
    public static Role selectRole(String roleString) {
        Role role;
        if(!roleString.equals("ADMIN"))role = Role.USER;
        else {role = Role.ADMIN;}
        return role;
    }
    
    /**
     * La fonction revoi le genre passé en parametre
     * @param gender
     * @return 
     */
    public static Gender selectGender(String gender){
        switch(gender){
            case "M":
                return Gender.M;
            case "F":
                return Gender.F;
            default:
                return null;
        }
    }
    
    /**
     * La fonction cherche l'id user à modifier et renvoi l'objet associé
     * @param idUserString
     * @return 
     */
    public static User findIdeditUser(String idUserString){
        User user = null;
        if(!idUserString.isEmpty()){
            long idUser = Long.parseLong(idUserString);
            user = userImplement.findById(idUser);
        }
        return user;
    }
    
    /**
     * La fonction met à jour les données utilisateur
     * @param genderString
     * @param roleString
     * @param username
     * @param password
     * @param idUser
     * @param dateTime
     * @return 
     */
    public static boolean updateUser(String genderString, String roleString, String username, String password, long idUser, LocalDateTime dateTime){
        Role role = selectRole(roleString);
        Gender gender = selectGender(genderString);
        boolean result = false;
        try { 
            User userUpdate = new User(username, password, role, gender);
            userImplement.update(userUpdate, idUser);
            result = true;} 
        catch (Exception e) {}
        return result;
        
    }
    
    public static User findByName(String userName, String password){
        User user = checkPassword(userName, password);
        if(user != null) return user;
        return null;
    }
    
    public static User checkUsername(String username){
        User userBDD = null;
        if (!username.isEmpty()) return userBDD = userImplement.findByName(username);
        return userBDD;
    }
    
    public static User checkPassword(String username, String password ){
        User userBDD = checkUsername(username);
        if(password.isEmpty() && userBDD == null) return null;
        if(password.equals(userBDD.getPassword())) return userBDD;
        return null;
    }
    
    /**
     * La fonction supprime le compte de l'utilisateur
     * @param idUserDeleteString
     * @return 
     */
    public static boolean deleteUser(String idUserDeleteString) {
        boolean result = false;
        
        if(!idUserDeleteString.isEmpty()){
            long idUser = Long.parseLong(idUserDeleteString);
            result = userImplement.delete(idUser);
        }
        return result;
    }
    
    
}
