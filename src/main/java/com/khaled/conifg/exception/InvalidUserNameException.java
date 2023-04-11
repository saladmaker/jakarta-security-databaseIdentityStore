package com.khaled.conifg.exception;

/**
 *
 * @author khaled
 */
public class InvalidUserNameException extends CredentialException{
    public InvalidUserNameException(){
        this("invalid username");
    }
    public InvalidUserNameException(String message) {
        super(message);
    }
    
}
