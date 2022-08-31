/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devprojet.dl.securitywebjpa.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author dev-pro
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    
    @Column(unique = true, length = 25)
    private String username;
    
    @Column(nullable=false, length=30)
    private String password;
    
    private LocalDateTime createDate;
    private Role role;   
    private Gender gender;
        
    public User() {}

    /**
     * Constructeur Create
     * @param username
     * @param password
     * @param createDate
     * @param role
     * @param gender 
     */
    public User(String username, String password, LocalDateTime createDate, Role role, Gender gender) {
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.role = role;
        this.gender = gender;
    }

    /**
     * Constructeur Update
     * @param username
     * @param password
     * @param role
     * @param gender 
     */
    public User(String username, String password, Role role, Gender gender) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.gender = gender;
    }
    
    

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{idUser=").append(idUser);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", createDate=").append(createDate);
        sb.append('}');
        return sb.toString();
    }

    
}