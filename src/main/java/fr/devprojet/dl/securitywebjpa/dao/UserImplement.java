/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.dao;

import fr.devprojet.dl.securitywebjpa.entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dev-pro
 */
public class UserImplement extends EntityManagerOption implements DaoInterface<User>{
    private final DaoFactory DAO_FACTORY;

    public UserImplement(DaoFactory DaoFactory) {
        this.DAO_FACTORY = DaoFactory;
    }

    /**
     * La fonction creer un utilisateur
     * @param objet
     * @return 
     */
    @Override
    public User create(User objet) {
        try {
            entityManager = DAO_FACTORY.getEntityManager();
            setTransaction();
            transaction.begin();
            //entityManager.merge(objet);
            entityManager.persist(objet);
            transaction.commit();} 
        catch (Exception e) {
            System.out.println("Err creation User \n" + e);
            if(transaction != null) rollbackTransaction();}
        finally{if(entityManager != null) closeEntityManager();}
        return objet;
    }
    
    /**
     * La fonction va recuperer la liste de tous les utilisateurs 
     * @return liste
     */
    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try {
            entityManager = DAO_FACTORY.getEntityManager();
            list = entityManager
                    .createQuery("SELECT u FROM User u")
                    .getResultList();} 
        catch (Exception e) {System.out.println("Err : FindAll \n"+ e);}
        finally{if(entityManager != null) closeEntityManager();}
        return list;
    }
    
    /**
     * La fonction recuperer un utilisateur par id
     * @param id
     * @return 
     */
    @Override
    public User findById(long id) {
        User user = null;
        try {
            entityManager = DAO_FACTORY.getEntityManager();
            user = entityManager.find(User.class, id);} 
        catch (Exception e) {System.out.println("Err findById User \n" + e);} 
        finally {if (entityManager != null) closeEntityManager(); }
        return user;
    }
    
    /**
     * La fonction mets à jour l'utilisateur
     * @param newObjet
     * @param id
     * @return 
     */
    @Override
    public User update(User newObjet, long id) {
        User user = findById(id);
        user.setGender(newObjet.getGender());
        user.setRole(newObjet.getRole());
        user.setUsername(newObjet.getUsername());
        user.setPassword(newObjet.getPassword());
        
        if(user != null){
            try {
                entityManager = DAO_FACTORY.getEntityManager();
                setTransaction();
                transaction.begin();
                entityManager.merge(user);
                transaction.commit();} 
            catch (Exception e) {rollbackTransaction();}
            finally{if(entityManager != null)closeEntityManager();}
        }
        return user;
    }
    
    /**
     * La fonction mets à jour l'utilisateur
     * @param newObjet
     * @param id
     * @return 
     */
    public User updateUsers(User newObjet, long id){
        User users = findById(id);
        users.setGender(newObjet.getGender());
        users.setRole(newObjet.getRole());
        users.setUsername(newObjet.getUsername());
        users.setPassword(newObjet.getPassword());
        try {
            entityManager = DAO_FACTORY.getEntityManager();
            setTransaction();
            transaction.begin();
            entityManager.merge(users);
            transaction.commit();} 
        catch (Exception e) {rollbackTransaction();}
        finally{if(entityManager != null)closeEntityManager();}
        return users;
    }

    /**
     * La fonction l'id passé en parametre
     * @param id
     * @return 
     */
    @Override
    public boolean delete(long id) {
        User user = findById(id);
        boolean result = false;
        if(user != null){
            try {
                entityManager = DAO_FACTORY.getEntityManager();
                setTransaction();
                transaction.begin();
                entityManager.remove(entityManager.merge(user));
                transaction.commit();
                result = true;} 
            catch (Exception e) {rollbackTransaction();}
            finally{if(entityManager != null) closeEntityManager();}
        }
        return result;
    }
    
    /**
     * La fonction recuperer l'utilisateur par son nom
     * @param username
     * @return 
     */
    public User findByName(String username){
        User user = null;
        try {
            entityManager = DAO_FACTORY.getEntityManager();                
            user = (User) entityManager.createQuery("SELECT u FROM User u WHERE u.username LIKE : custName ")
                    .setParameter("custName", username)
                    .getSingleResult();} 
        catch (Exception e) {
            System.out.println("Err : class = UserImplement method = findByName()" + e);
            return null;}
        finally{if (entityManager != null) closeEntityManager();}
        return user;
    }

}


