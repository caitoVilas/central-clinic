package com.clinic.commonservice.exceptions;

/**
 * Custom exception class for handling token-related errors.
 * This exception is thrown when there is an issue with the JWT token,
 * such as invalid signature, expiration, or other parsing errors.
 *
 * @author caito
 *
 */
public class TokenException extends RuntimeException{
    public TokenException(String message) {
        super(message);
    }
}
