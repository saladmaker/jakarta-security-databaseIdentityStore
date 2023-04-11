
package com.khaled.conifg.exception;

import jakarta.ejb.ApplicationException;

/**
 *
 * @author khaled
 */
@ApplicationException(rollback = true)
public class BusinessException extends RuntimeException{

    public BusinessException() {
    }
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(Throwable cause) {
        super(cause);
    }
    public BusinessException(Throwable cause, String message) {
        super(message, cause);
    }
    
    
}
