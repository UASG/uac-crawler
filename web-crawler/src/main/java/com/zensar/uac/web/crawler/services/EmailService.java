package com.zensar.uac.web.crawler.services;

import com.zensar.uac.web.crawler.exception.MessageBuilderException;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by srikant.singh on 10/12/2016.
 * Purpose of the class: ReportService is responsible for Sending emails
 * This is basic interface
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface EmailService {

    /**
     * Sets the various recipients of email message like To, CC and BCC.
     *
     * @param var1                      the type of recipient
     * @param var2                      the email address of recipient
     * @return
     * @throws MessageBuilderException  thrown when there is some exception while setting recipients of email
     */
    EmailService addRecipients(Message.RecipientType var1, String[] var2) throws MessageBuilderException;


    /**
     * Sets the subject of email message.
     *
     * @param var1                      the subject that has to be set
     * @return
     * @throws MessageBuilderException  thrown when there is some exception while setting subject of email
     */
    EmailService setSubject(String var1) throws MessageBuilderException;


    /**
     * Sets the text as the body of email message.
     *
     * @param var1                      the text that has to be set in the message body
     * @return
     * @throws MessageBuilderException  thrown when there is some exception while setting text as message body
     */
    EmailService addContent(String var1) throws MessageBuilderException;


    /**
     * Adds the attachment to the email message.
     *
     * @param var1                      the attachment to be added to the email
     * @return
     * @throws MessageBuilderException  thrown when there is some exception while adding attachment to the email
     */
    EmailService addAttachment(File var1) throws MessageBuilderException;


    /**
     * Adds the inline attachment to the email message.
     *
     * @param var1                      the attachment to be added to the email
     * @return
     * @throws MessageBuilderException  thrown when there is some exception while adding attachment to the email
     */
    EmailService addAttachmentInline(File var1) throws MessageBuilderException;


    /**
     *
     * @return
     * @throws MessageBuilderException  thrown when there is some exception
     */
    MimeMessage asEmailMessage() throws MessageBuilderException;


    /**
     * Sends the email message.
     *
     * @param var1                      the email message that has to be sent
     * @throws MessageBuilderException  thrown when there is some exception while sending the email
     */
    void sendEmail(MimeMessage var1) throws MessageBuilderException;


    /**
     * Sets the html content as the body of email message.
     *
     * @param var1                      the html content that has to be set in the message body
     * @return
     * @throws MessageBuilderException  thrown when there is some exception while setting html content as message body
     */
    EmailService addHtmlContent(String var1) throws MessageBuilderException;
}
