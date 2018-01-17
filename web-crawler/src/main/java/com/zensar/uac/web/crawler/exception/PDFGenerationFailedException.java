package com.zensar.uac.web.crawler.exception;

/**
 * Created by KK48481 on 24-08-2017.
 * Exception thrown when PDF report cannot be generated
 */
public class PDFGenerationFailedException extends RuntimeException {

    /**
     * Constructor for PDFGenerationFailedException.
     *
     * @param msg the detail message
     */
    public PDFGenerationFailedException(String msg) {
        super(msg);
    }
}
