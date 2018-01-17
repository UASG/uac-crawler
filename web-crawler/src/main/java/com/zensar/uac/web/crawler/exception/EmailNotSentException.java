package com.zensar.uac.web.crawler.exception;

/**
 * Created by KK48481 on 18-08-2017.
 * Exception thrown when email is not sent
 */
public class EmailNotSentException extends RuntimeException {

    /**
     * Constructor for EmailNotSentException.
     *
     * @param msg the detail message
     */
    public EmailNotSentException(String msg) {
        super(msg);
    }
}
