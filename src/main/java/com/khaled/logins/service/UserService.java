package com.khaled.logins.service;

import com.khaled.conifg.exception.InvalidUserNameException;
import com.khaled.conifg.exception.InvalidPasswordException;
import com.khaled.logins.model.Users;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.Optional;

/**
 *
 * @author khaled
 */
@Stateless
public class UserService {

    @PersistenceContext(unitName = "PU")
    EntityManager em;
    
    @Inject
    Pbkdf2PasswordHash pbkdf2PasswordHash; 

    public void createUser(Users user) {
        String clearPassword = user.getClearPassword();
        user.setPassword(hash(clearPassword.toCharArray()));
        em.persist(user);
    }

    public void updatePassword(Users user) {
        em.merge(user);
        String clearPassword = user.getClearPassword();
        user.setPassword(hash(clearPassword.toCharArray()));
    }

    public Optional<Users> findByUserName(String userName) {
        return em.createNamedQuery(Users.FIND_BY_USERNAME, Users.class)
                .setParameter("userName", userName)
                .getResultStream()
                .findFirst();
    }
    public Optional<Users> findByUserNameAndPassword(String userName, String password){
        return findByUserName(userName)
                .filter(user -> matchPassword(user, password));                
    }
    public Users getByUserName(String userName){
        return findByUserName(userName).orElseThrow(InvalidUserNameException::new);
    }
    public Users getByUserNameAndPassword(String userName, String password){
        return findByUserNameAndPassword(userName, password).orElseThrow(InvalidPasswordException::new);
    }
    public boolean matchPassword(Users user,String password){
        return verify(password.toCharArray(), user.getPassword());
    }
    private String hash(char[] passwordChars){
        return pbkdf2PasswordHash.generate(passwordChars);
    }
    private boolean verify(char[] passwordChars, String passwordHash){
        return pbkdf2PasswordHash.verify(passwordChars, passwordHash);
    }
}
