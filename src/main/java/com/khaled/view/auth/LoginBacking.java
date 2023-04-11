package com.khaled.view.auth;

import com.khaled.logins.model.Users;
import com.khaled.logins.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import static jakarta.security.enterprise.AuthenticationStatus.*;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import static com.khaled.logins.model.Group.*;
import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import java.io.IOException;
import org.omnifaces.cdi.Param;
import static org.omnifaces.util.Faces.getRequest;
import static org.omnifaces.util.Faces.getResponse;
import static org.omnifaces.util.Faces.redirect;
import static org.omnifaces.util.Faces.responseComplete;
import static org.omnifaces.util.Faces.validationFailed;
import static org.omnifaces.util.Messages.addFlashGlobalWarn;
import static org.omnifaces.util.Messages.addGlobalError;

/**
 *
 * @author khaled
 */
@Named
@RequestScoped
public class LoginBacking {

    private Users user;

    @Inject
    @Param(name = "continue")
    private boolean loginToContinue;

    @Inject
    UserService userService;

    @Inject
    SecurityContext securityContext;

    @Inject
    ActiveUser activeUser;

    @Inject
    FacesContext facesContext;

    @PostConstruct
    public void init() {
        if (activeUser.isPresent()) {
            addFlashGlobalWarn("auth.message.warn.already_logged_in");
        } else {
            user = new Users();
        }
    }

    public void login() throws IOException {
        this.athenticate(
                withParams()
                        .credential(
                                new UsernamePasswordCredential(
                                        user.getEmail(),
                                        user.getClearPassword()
                                ))
                        .newAuthentication(!loginToContinue)
        );
    }

    void athenticate(AuthenticationParameters parameters) {

        AuthenticationStatus status = securityContext
                .authenticate(
                        getRequest(),
                        getResponse(),
                        parameters
                );
        if (status == SEND_FAILURE) {
            addGlobalError("auth.message.error.failure");
            validationFailed();
        } else if (status == SEND_CONTINUE) {
            responseComplete();
        } else if (activeUser.hasGroup(USER)) {
            redirect("user/profile");
        } else if (activeUser.hasGroup(ADMIN)) {
            redirect("admin/profile");
        } else {
            redirect("");
        }
    }
    public Users getUser(){
        return this.user;
    }
}
