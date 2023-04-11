package com.khaled.conifg.auth;

import com.khaled.logins.model.Users;
import jakarta.security.enterprise.CallerPrincipal;

/**
 *
 * @author khaled
 */
public class UserPrincipal extends CallerPrincipal{
    private final Users user;
    public UserPrincipal(String name, Users user) {
        super(name);
        this.user = user;
    }

    public Users getUser() {
        return user;
    }
    
    
}
