/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khaled.conifg.exception;

/**
 *
 * @author khaled
 */
public class InvalidPasswordException extends CredentialException{
    public InvalidPasswordException(){
        this("invalid password");
    }
    public InvalidPasswordException(String message) {
        super(message);
    }
    
}
