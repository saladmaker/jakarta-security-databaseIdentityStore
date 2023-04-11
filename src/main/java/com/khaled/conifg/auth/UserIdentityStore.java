
package com.khaled.conifg.auth;

import com.khaled.conifg.exception.CredentialException;
import com.khaled.logins.model.Users;
import com.khaled.logins.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

/**
 *
 * @author khaled
 */
@ApplicationScoped
public class UserIdentityStore implements IdentityStore{
    @Inject
    UserService userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential)credential;
        return validate(usernamePasswordCredential);
    }
    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        String userName = credential.getCaller();
        String clearPassword = credential.getPasswordAsString();
        try{
            Users user = userService.getByUserNameAndPassword(userName, clearPassword);
            return new CredentialValidationResult(new UserPrincipal(userName, user), user.getRolesAsStrings());
        }catch(CredentialException ce){
            return CredentialValidationResult.INVALID_RESULT;
        }
    } 
    
    
}
