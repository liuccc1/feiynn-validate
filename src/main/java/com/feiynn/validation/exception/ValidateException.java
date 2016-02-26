package com.feiynn.validation.exception;

/** 
 * 
 * @author: Dean
 */
public class ValidateException extends RuntimeException{

	private static final long serialVersionUID = -7358773631702311527L;
	
	public ValidateException() {
		super();
	}

    public ValidateException(String message) {
    	super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

}
