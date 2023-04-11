package com.khaled.logins.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;
import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.EnumType.STRING;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author khaled
 */
@NamedQuery(
        name = Users.FIND_BY_USERNAME,
        query = "SELECT u From Users u WHERE u.email = :userName"
)
@Entity
public class Users implements Serializable {

    public static final String FIND_BY_USERNAME = "Users.findByUserName";

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;
    
    @ElementCollection(fetch = EAGER)
    private @Enumerated(STRING) Set<Group> groups = new HashSet<>();
    
    @Transient
    @NotNull
    @NotBlank
    private String clearPassword;
    

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Users user)
                && Objects.equals(this.email, user.email);
    }
    public Set<Role> getRoles(){
        return groups.stream()
                .flatMap(group -> group.getRoles().stream())
                .collect(toSet());
    }
    public Set<String> getRolesAsStrings(){
        return getRoles().stream()
                .map(Role::name)
                .collect(toSet());
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClearPassword() {
        return clearPassword;
    }

    public void setClearPassword(String clearPassword) {
        this.clearPassword = clearPassword;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
    public void addGroup(Group group){
        groups.add(group);
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.email);
        return hash;
    }

}
