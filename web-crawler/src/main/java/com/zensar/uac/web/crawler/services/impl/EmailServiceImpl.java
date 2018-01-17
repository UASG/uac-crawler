package com.zensar.uac.web.crawler.services.impl;

import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import com.zensar.uac.web.crawler.exception.EmailNotSentException;
import com.zensar.uac.web.crawler.exception.MessageBuilderException;
import com.zensar.uac.web.crawler.pojo.EmailConfig;
import com.zensar.uac.web.crawler.services.EmailService;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.zensar.uac.web.crawler.util.MimeMessageHelper;

/**
 * Created by srikant.singh on 10/12/2016.
 */

/**
 * Created by srikant.singh on 10/12/2016.
 * Purpose of the class: EmailServiceImpl is responsible for Sending emails
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class.getName());
    private static final String ENCODING = "UTF-8";
    protected MimeMessage mimeMessage;
    private MimeMessageHelper messageHelper;

    public EmailServiceImpl(EmailConfig emailConfig) throws MessageBuilderException {
        this.mimeMessage = new MimeMessage(this.createSession(emailConfig));

        try {
            this.messageHelper = new MimeMessageHelper(this.mimeMessage, true, ENCODING);
            this.messageHelper.setFrom(new InternetAddress(emailConfig.getFrom()));
        } catch (MessagingException var3) {
            throw new MessageBuilderException(var3);
        }
    }

    /*
    This method is used to create a session for sending email.
    */
    protected Session createSession(final EmailConfig emailConfig) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailConfig.getHost());
        properties.put("mail.smtp.port", emailConfig.getPort());
        properties.put("mail.smtp.starttls.enable", emailConfig.isTls());
        properties.put("mail.smtp.auth", emailConfig.getSmtpAuth());
        properties.put("mail.smtp.ssl.enable", emailConfig.isSsl());
        properties.put("mail.smtp.from", emailConfig.getBounceAddress());
        properties.put("mail.mime.charset", ENCODING);
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUserName(), emailConfig.getPassKey());
            }
        });
    }

    public EmailService setSubject(String subject) throws MessageBuilderException {
        if ("".equals(subject)) {
            return this;
        } else {
            try {
                this.messageHelper.setSubject(subject);
                return this;
            } catch (MessagingException var3) {
                throw new MessageBuilderException(var3);
            }
        }
    }

    public EmailService addContent(String content) throws MessageBuilderException {
        try {
            this.messageHelper.setText(content);
            return this;
        } catch (MessagingException var3) {
            throw new MessageBuilderException(var3);
        }
    }

    public EmailService addHtmlContent(String content) throws MessageBuilderException {
        try {
            this.messageHelper.setText(content, true);
            return this;
        } catch (MessagingException var3) {
            throw new MessageBuilderException(var3);
        }
    }

    public EmailService addAttachment(File file) throws MessageBuilderException {
        try {
            this.messageHelper.addAttachment(file.getName(), file);
            return this;
        } catch (MessagingException var3) {
            throw new MessageBuilderException(var3);
        }
    }

    public EmailService addAttachmentInline(File file) throws MessageBuilderException {
        try {
            if (file == null) {
                return this;
            } else {
                String e = this.createContentId(file);
                this.messageHelper.addInline(e, file);
                return this;
            }
        } catch (MessagingException var3) {
            throw new MessageBuilderException(var3);
        }
    }

    /*
    This method is used to create content id for file to be sent as attachment in the email message.
    */
    private String createContentId(File file) {
        return file.getName().replace(" ", "_") + "_" + System.currentTimeMillis();
    }

    public EmailService addRecipients(Message.RecipientType type, String[] recipient) throws MessageBuilderException {
        if (type == null) {
            return this;
        } else {
            try {
                String e = type.toString();
                byte var4 = -1;
                switch (e.hashCode()) {
                    case 2176:
                        if ("Cc".equals(e)) {
                            var4 = 1;
                        }
                        break;
                    case 2715:
                        if ("To".equals(e)) {
                            var4 = 0;
                        }
                        break;
                    case 66594:
                        if ("Bcc".equals(e)) {
                            var4 = 2;
                        }
                        break;
                    default:
                        break;
                }

                switch (var4) {
                    case 0:
                        this.messageHelper.setTo(recipient);
                        break;
                    case 1:
                        this.messageHelper.setCc(recipient);
                        break;
                    case 2:
                        this.messageHelper.setBcc(recipient);
                        break;
                    default:
                        throw new MessageBuilderException("Invalid recipient type " + type.toString());
                }

                return this;
            } catch (MessagingException var5) {
                throw new MessageBuilderException(var5);
            }
        }
    }

    public MimeMessage asEmailMessage() throws MessageBuilderException {
        return this.messageHelper.getMimeMessage();
    }

    protected void setSubType(String type) throws MessagingException {
        this.messageHelper.getMimeMultipart().setSubType(type);
    }

    public void sendEmail(MimeMessage mimeMessage) throws MessageBuilderException {
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException var3) {
            throw new MessageBuilderException(var3);
        }
    }

    /*
    This method is used to send the email message.
    */
    public static void sendEmail(File filename, String to, String sendMessage, String subject) {
        // Recipient's oid email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned
        String from = "harshal.jawale@zensar.com";

        final String username = "AKIAJ7NKQD6N4ITKL6BQ";//change accordingly
        final String passKey = "Aqw25yKH1deqRGTRc2zw+uINnU2sS3uBqhOcZl+l0Cl3";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "email-smtp.us-east-1.amazonaws.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, passKey);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            if (filename != null) {
                BodyPart messageBodyPart = new MimeBodyPart();

                // Now set the actual message
                messageBodyPart.setText(sendMessage);

                // Create a multipar message

                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment


                messageBodyPart = new MimeBodyPart();

                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename.getName());

                multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                message.setContent(multipart);
            } else {
                message.setText(sendMessage);
            }
            // Send message
            Transport.send(message);


        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
            throw new EmailNotSentException("Failed to send email.");
        }
    }
}
