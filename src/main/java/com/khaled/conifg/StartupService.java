/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khaled.conifg;

import com.khaled.logins.model.Users;
import com.khaled.logins.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.util.Set;
import static com.khaled.logins.model.Group.*;

/**
 *
 * @author khaled
 */
@DataSourceDefinition(
        name = "java:app/identity/Datasource",
        className = "org.h2.jdbcx.JdbcDataSource",
        transactional = true,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED,
        initialPoolSize = 2,
        minPoolSize = 5,
        maxPoolSize = 10,
        maxStatements = 2,
        url = "jdbc:h2:mem:kickoff;DB_CLOSE_DELAY=-1;MODE=LEGACY",
        user = "sa",
        password = "sa"
)
@Startup
@Singleton
public class StartupService {
    @Inject
    private UserService userService;
    
    @PostConstruct
    public void startup(){
        Users user = new Users();
        
        user.setEmail("user@user.com");
        user.setClearPassword("123");
        userService.createUser(user);
        user.addGroup(USER);
        
        Users admin = new Users();
        admin.setEmail("admin@user.com");
        admin.setClearPassword("admin123");
        user.addGroup(ADMIN);
        userService.createUser(user);
        
    }
}
