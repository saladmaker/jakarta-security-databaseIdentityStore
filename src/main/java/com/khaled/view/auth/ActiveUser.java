/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khaled.view.auth;

import com.khaled.conifg.auth.UserPrincipal;
import com.khaled.logins.model.Group;
import com.khaled.logins.model.Role;
import com.khaled.logins.model.Users;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.SecurityContext;
import java.io.Serializable;

/**
 *
 * @author khaled
 */
@Named
@SessionScoped
public class ActiveUser implements Serializable{
    private Users activeUser;
    @Inject
    SecurityContext securityContext;
    
    public Users get(){
        if(activeUser == null){
            activeUser = securityContext
                    .getPrincipalsByType(UserPrincipal.class)
                    .stream()
                    .map(UserPrincipal::getUser)
                    .findAny().orElse(null);
        }
        return activeUser;
    }
    public boolean isPresent(){
        return get()!= null;
    }
    public boolean hasGroup(Group group){
        return isPresent() && activeUser.getGroups().contains(group);
    }
    public boolean hasRole(Role role){
        return isPresent() && activeUser.getRoles().contains(role);
    }
}
