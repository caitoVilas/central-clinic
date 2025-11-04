package com.clinic.commonservice.exceptions;

/**
 * Custom exception class for handling broker message errors in the application.
 * This exception is thrown when there is an issue related to message brokering.
 *
 * @author caito
 *
 */
public class BrokerMsgException extends RuntimeException {
    public BrokerMsgException(String message) {
        super(message);
    }
}
