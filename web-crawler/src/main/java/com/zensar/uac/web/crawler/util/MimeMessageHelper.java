package com.zensar.uac.web.crawler.util;

/**
 * Created by Srikant Singh on 10/20/2016.
 * Purpose of the class: Mime message helper
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.FileTypeMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class MimeMessageHelper {
    private final MimeMessage mimeMessage;
    private MimeMultipart rootMimeMultipart;
    private MimeMultipart mimeMultipart;
    private final String encoding;
    private FileTypeMap fileTypeMap;
    private boolean validateAddresses;

    private static final String FROM_ADDRESS_MUST_NOT_BE_NULL = "From address must not be null";
    private static final String TO_ADDRESS_MUST_NOT_BE_NULL = "To address must not be null";
    private static final String TO_ADDRESS_ARRAY_MUST_NOT_BE_NULL = "To address array must not be null";
    private static final String REPLY_TO_ADDRESS_MUST_NOT_BE_NULL = "Reply-to address must not be null";
    private static final String CC_ADDRESS_MUST_NOT_BE_NULL = "Cc address must not be null";
    private static final String CC_ADDRESS_ARRAY_MUST_NOT_BE_NULL = "Cc address array must not be null";
    private static final String BCC_ADDRESS_MUST_NOT_BE_NULL = "Bcc address must not be null";
    private static final String BCC_ADDRESS_ARRAY_MUST_NOT_BE_NULL = "Bcc address array must not be null";
    private static final String DATA_SOURCE_MUST_NOT_BE_NULL = "DataSource must not be null";
    private static final String FILE_MUST_NOT_BE_NULL = "File must not be null";
    
    public MimeMessageHelper(MimeMessage mimeMessage) {
        this(mimeMessage, (String) null);
    }

    public MimeMessageHelper(MimeMessage mimeMessage, String encoding) {
        this.validateAddresses = false;
        this.mimeMessage = mimeMessage;
        this.encoding = encoding != null ? encoding : "UTF-8";
    }

    public MimeMessageHelper(MimeMessage mimeMessage, boolean multipart) throws MessagingException {
        this(mimeMessage, multipart, (String) null);
    }

    public MimeMessageHelper(MimeMessage mimeMessage, boolean multipart, String encoding) throws MessagingException {
        this(mimeMessage, multipart ? 3 : 0, encoding);
    }

    public MimeMessageHelper(MimeMessage mimeMessage, int multipartMode) throws MessagingException {
        this(mimeMessage, multipartMode, (String) null);
    }

    public MimeMessageHelper(MimeMessage mimeMessage, int multipartMode, String encoding) throws MessagingException {
        this.validateAddresses = false;
        this.mimeMessage = mimeMessage;
        this.createMimeMultiparts(mimeMessage, multipartMode);
        this.encoding = encoding != null ? encoding : "UTF-8";
    }

    public final MimeMessage getMimeMessage() {
        return this.mimeMessage;
    }

    protected void createMimeMultiparts(MimeMessage mimeMessage, int multipartMode) throws MessagingException {
        switch (multipartMode) {
            case 0:
                this.setMimeMultiparts((MimeMultipart) null, (MimeMultipart) null);
                break;
            case 1:
                MimeMultipart mixedMultipart = new MimeMultipart("mixed");
                mimeMessage.setContent(mixedMultipart);
                this.setMimeMultiparts(mixedMultipart, mixedMultipart);
                break;
            case 2:
                MimeMultipart relatedMultipart = new MimeMultipart("related");
                mimeMessage.setContent(relatedMultipart);
                this.setMimeMultiparts(relatedMultipart, relatedMultipart);
                break;
            case 3:
                MimeMultipart rootMixedMultipart = new MimeMultipart("mixed");
                mimeMessage.setContent(rootMixedMultipart);
                MimeMultipart nestedRelatedMultipart = new MimeMultipart("related");
                MimeBodyPart relatedBodyPart = new MimeBodyPart();
                relatedBodyPart.setContent(nestedRelatedMultipart);
                rootMixedMultipart.addBodyPart(relatedBodyPart);
                this.setMimeMultiparts(rootMixedMultipart, nestedRelatedMultipart);
                break;
            default:
                throw new IllegalArgumentException("Only multipart modes MIXED_RELATED, RELATED and NO supported");
        }

    }

    protected final void setMimeMultiparts(MimeMultipart root, MimeMultipart main) {
        this.rootMimeMultipart = root;
        this.mimeMultipart = main;
    }

    public final boolean isMultipart() {
        return this.rootMimeMultipart != null;
    }

    private void checkMultipart() {
        if (!this.isMultipart()) {
            throw new IllegalStateException("Not in multipart mode - create an appropriate MimeMessageHelper via a constructor that takes a \'multipart\' flag if you need to set alternative texts or add inline elements or attachments.");
        }
    }

    public final MimeMultipart getRootMimeMultipart() {
        this.checkMultipart();
        return this.rootMimeMultipart;
    }

    public final MimeMultipart getMimeMultipart() {
        this.checkMultipart();
        return this.mimeMultipart;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public FileTypeMap getFileTypeMap() {
        return this.fileTypeMap;
    }

    public void setValidateAddresses(boolean validateAddresses) {
        this.validateAddresses = validateAddresses;
    }

    public boolean isValidateAddresses() {
        return this.validateAddresses;
    }

    protected void validateAddress(InternetAddress address) throws AddressException {
        if (this.isValidateAddresses()) {
            address.validate();
        }

    }

    protected void validateAddresses(InternetAddress[] addresses) throws AddressException {
        InternetAddress[] var2 = addresses;
        int var3 = addresses.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            InternetAddress address = var2[var4];
            this.validateAddress(address);
        }

    }

    public void setFrom(InternetAddress from) throws MessagingException {
        Assert.notNull(from, FROM_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(from);
        this.mimeMessage.setFrom(from);
    }

    public void setFrom(String from) throws MessagingException {
        Assert.notNull(from, FROM_ADDRESS_MUST_NOT_BE_NULL);
        this.setFrom(this.parseAddress(from));
    }

    public void setFrom(String from, String personal) throws MessagingException, UnsupportedEncodingException {
        Assert.notNull(from, FROM_ADDRESS_MUST_NOT_BE_NULL);
        this.setFrom(this.getEncoding() != null ? new InternetAddress(from, personal, this.getEncoding()) : new InternetAddress(from, personal));
    }

    public void setReplyTo(InternetAddress replyTo) throws MessagingException {
        Assert.notNull(replyTo, REPLY_TO_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(replyTo);
        this.mimeMessage.setReplyTo(new InternetAddress[]{replyTo});
    }

    public void setReplyTo(String replyTo) throws MessagingException {
        Assert.notNull(replyTo, REPLY_TO_ADDRESS_MUST_NOT_BE_NULL);
        this.setReplyTo(this.parseAddress(replyTo));
    }

    public void setReplyTo(String replyTo, String personal) throws MessagingException, UnsupportedEncodingException {
        Assert.notNull(replyTo, REPLY_TO_ADDRESS_MUST_NOT_BE_NULL);
        InternetAddress replyToAddress = this.getEncoding() != null ? new InternetAddress(replyTo, personal, this.getEncoding()) : new InternetAddress(replyTo, personal);
        this.setReplyTo(replyToAddress);
    }

    public void setTo(InternetAddress to) throws MessagingException {
        Assert.notNull(to, TO_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(to);
        this.mimeMessage.setRecipient(RecipientType.TO, to);
    }

    public void setTo(InternetAddress[] to) throws MessagingException {
        Assert.notNull(to, TO_ADDRESS_ARRAY_MUST_NOT_BE_NULL);
        this.validateAddresses(to);
        this.mimeMessage.setRecipients(RecipientType.TO, to);
    }

    public void setTo(String to) throws MessagingException {
        Assert.notNull(to, TO_ADDRESS_MUST_NOT_BE_NULL);
        this.setTo(this.parseAddress(to));
    }

    public void setTo(String[] to) throws MessagingException {
        Assert.notNull(to, TO_ADDRESS_ARRAY_MUST_NOT_BE_NULL);
        InternetAddress[] addresses = new InternetAddress[to.length];

        for (int i = 0; i < to.length; ++i) {
            addresses[i] = this.parseAddress(to[i]);
        }

        this.setTo(addresses);
    }

    public void addTo(InternetAddress to) throws MessagingException {
        Assert.notNull(to, TO_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(to);
        this.mimeMessage.addRecipient(RecipientType.TO, to);
    }

    public void addTo(String to) throws MessagingException {
        Assert.notNull(to, TO_ADDRESS_MUST_NOT_BE_NULL);
        this.addTo(this.parseAddress(to));
    }

    public void addTo(String to, String personal) throws MessagingException, UnsupportedEncodingException {
        Assert.notNull(to, TO_ADDRESS_MUST_NOT_BE_NULL);
        this.addTo(this.getEncoding() != null ? new InternetAddress(to, personal, this.getEncoding()) : new InternetAddress(to, personal));
    }

    public void setCc(InternetAddress cc) throws MessagingException {
        Assert.notNull(cc, CC_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(cc);
        this.mimeMessage.setRecipient(RecipientType.CC, cc);
    }

    public void setCc(InternetAddress[] cc) throws MessagingException {
        Assert.notNull(cc, CC_ADDRESS_ARRAY_MUST_NOT_BE_NULL);
        this.validateAddresses(cc);
        this.mimeMessage.setRecipients(RecipientType.CC, cc);
    }

    public void setCc(String cc) throws MessagingException {
        Assert.notNull(cc, CC_ADDRESS_MUST_NOT_BE_NULL);
        this.setCc(this.parseAddress(cc));
    }

    public void setCc(String[] cc) throws MessagingException {
        Assert.notNull(cc, CC_ADDRESS_ARRAY_MUST_NOT_BE_NULL);
        InternetAddress[] addresses = new InternetAddress[cc.length];

        for (int i = 0; i < cc.length; ++i) {
            addresses[i] = this.parseAddress(cc[i]);
        }

        this.setCc(addresses);
    }

    public void addCc(InternetAddress cc) throws MessagingException {
        Assert.notNull(cc, CC_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(cc);
        this.mimeMessage.addRecipient(RecipientType.CC, cc);
    }

    public void addCc(String cc) throws MessagingException {
        Assert.notNull(cc, CC_ADDRESS_MUST_NOT_BE_NULL);
        this.addCc(this.parseAddress(cc));
    }

    public void addCc(String cc, String personal) throws MessagingException, UnsupportedEncodingException {
        Assert.notNull(cc, CC_ADDRESS_MUST_NOT_BE_NULL);
        this.addCc(this.getEncoding() != null ? new InternetAddress(cc, personal, this.getEncoding()) : new InternetAddress(cc, personal));
    }

    public void setBcc(InternetAddress bcc) throws MessagingException {
        Assert.notNull(bcc, BCC_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(bcc);
        this.mimeMessage.setRecipient(RecipientType.BCC, bcc);
    }

    public void setBcc(InternetAddress[] bcc) throws MessagingException {
        Assert.notNull(bcc, BCC_ADDRESS_ARRAY_MUST_NOT_BE_NULL);
        this.validateAddresses(bcc);
        this.mimeMessage.setRecipients(RecipientType.BCC, bcc);
    }

    public void setBcc(String bcc) throws MessagingException {
        Assert.notNull(bcc, BCC_ADDRESS_MUST_NOT_BE_NULL);
        this.setBcc(this.parseAddress(bcc));
    }

    public void setBcc(String[] bcc) throws MessagingException {
        Assert.notNull(bcc, BCC_ADDRESS_ARRAY_MUST_NOT_BE_NULL);
        InternetAddress[] addresses = new InternetAddress[bcc.length];

        for (int i = 0; i < bcc.length; ++i) {
            addresses[i] = this.parseAddress(bcc[i]);
        }

        this.setBcc(addresses);
    }

    public void addBcc(InternetAddress bcc) throws MessagingException {
        Assert.notNull(bcc, BCC_ADDRESS_MUST_NOT_BE_NULL);
        this.validateAddress(bcc);
        this.mimeMessage.addRecipient(RecipientType.BCC, bcc);
    }

    public void addBcc(String bcc) throws MessagingException {
        Assert.notNull(bcc, BCC_ADDRESS_MUST_NOT_BE_NULL);
        this.addBcc(this.parseAddress(bcc));
    }

    public void addBcc(String bcc, String personal) throws MessagingException, UnsupportedEncodingException {
        Assert.notNull(bcc, BCC_ADDRESS_MUST_NOT_BE_NULL);
        this.addBcc(this.getEncoding() != null ? new InternetAddress(bcc, personal, this.getEncoding()) : new InternetAddress(bcc, personal));
    }

    private InternetAddress parseAddress(String address) throws MessagingException {
        InternetAddress[] parsed = InternetAddress.parse(address);
        if (parsed.length != 1) {
            throw new AddressException("Illegal address", address);
        } else {
            InternetAddress raw = parsed[0];

            try {
                return this.getEncoding() != null ? new InternetAddress(raw.getAddress(), raw.getPersonal(), this.getEncoding()) : raw;
            } catch (UnsupportedEncodingException var5) {
                throw new MessagingException("Failed to parse embedded personal name to correct encoding", var5);
            }
        }
    }

    public void setPriority(int priority) throws MessagingException {
        this.mimeMessage.setHeader("X-Priority", Integer.toString(priority));
    }

    public void setSentDate(Date sentDate) throws MessagingException {
        Assert.notNull(sentDate, "Sent date must not be null");
        this.mimeMessage.setSentDate(sentDate);
    }

    public void setSubject(String subject) throws MessagingException {
        Assert.notNull(subject, "Subject must not be null");
        if (this.getEncoding() != null) {
            this.mimeMessage.setSubject(subject, this.getEncoding());
        } else {
            this.mimeMessage.setSubject(subject);
        }

    }

    public void setText(String text) throws MessagingException {
        this.setText(text, false);
    }

    public void setText(String text, boolean html) throws MessagingException {
        Assert.notNull(text, "Text must not be null");
        Object partToUse;
        if (this.isMultipart()) {
            partToUse = this.getMainPart();
        } else {
            partToUse = this.mimeMessage;
        }

        if (html) {
            this.setHtmlTextToMimePart((MimePart) partToUse, text);
        } else {
            this.setPlainTextToMimePart((MimePart) partToUse, text);
        }

    }

    public void setText(String plainText, String htmlText) throws MessagingException {
        Assert.notNull(plainText, "Plain text must not be null");
        Assert.notNull(htmlText, "HTML text must not be null");
        MimeMultipart messageBody = new MimeMultipart("alternative");
        this.getMainPart().setContent(messageBody, "text/alternative");
        MimeBodyPart plainTextPart = new MimeBodyPart();
        this.setPlainTextToMimePart(plainTextPart, plainText);
        messageBody.addBodyPart(plainTextPart);
        MimeBodyPart htmlTextPart = new MimeBodyPart();
        this.setHtmlTextToMimePart(htmlTextPart, htmlText);
        messageBody.addBodyPart(htmlTextPart);
    }

    private MimeBodyPart getMainPart() throws MessagingException {
        MimeMultipart mimeMultipart = this.getMimeMultipart();
        MimeBodyPart bodyPart = null;

        for (int mimeBodyPart = 0; mimeBodyPart < mimeMultipart.getCount(); ++mimeBodyPart) {
            BodyPart bp = mimeMultipart.getBodyPart(mimeBodyPart);
            if (bp.getFileName() == null) {
                bodyPart = (MimeBodyPart) bp;
            }
        }

        if (bodyPart == null) {
            MimeBodyPart var5 = new MimeBodyPart();
            mimeMultipart.addBodyPart(var5);
            bodyPart = var5;
        }

        return bodyPart;
    }

    private void setPlainTextToMimePart(MimePart mimePart, String text) throws MessagingException {
        if (this.getEncoding() != null) {
            mimePart.setText(text, this.getEncoding());
        } else {
            mimePart.setText(text);
        }

    }

    private void setHtmlTextToMimePart(MimePart mimePart, String text) throws MessagingException {
        if (this.getEncoding() != null) {
            mimePart.setContent(text, "text/html;charset=" + this.getEncoding());
        } else {
            mimePart.setContent(text, "text/html");
        }

    }

    public void addInline(String contentId, DataSource dataSource) throws MessagingException {
        Assert.notNull(contentId, "Content ID must not be null");
        Assert.notNull(dataSource, DATA_SOURCE_MUST_NOT_BE_NULL);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setDisposition("inline");
        mimeBodyPart.setHeader("Content-ID", "<" + contentId + ">");
        mimeBodyPart.setDataHandler(new DataHandler(dataSource));
        this.getMimeMultipart().addBodyPart(mimeBodyPart);
    }

    public void addInline(String contentId, File file) throws MessagingException {
        Assert.notNull(file, FILE_MUST_NOT_BE_NULL);
        FileDataSource dataSource = new FileDataSource(file);
        dataSource.setFileTypeMap(this.getFileTypeMap());
        this.addInline(contentId, (DataSource) dataSource);
    }

    public void addInline(String contentId, Resource resource) throws MessagingException {
        Assert.notNull(resource, "Resource must not be null");
        String contentType = this.getFileTypeMap().getContentType(resource.getFilename());
        this.addInline(contentId, resource, contentType);
    }

    public void addInline(String contentId, InputStreamSource inputStreamSource, String contentType) throws MessagingException {
        Assert.notNull(inputStreamSource, "InputStreamSource must not be null");
        if (inputStreamSource instanceof Resource && ((Resource) inputStreamSource).isOpen()) {
            throw new IllegalArgumentException("Passed-in Resource contains an open stream: invalid argument. JavaMail requires an InputStreamSource that creates a fresh stream for every call.");
        } else {
            DataSource dataSource = this.createDataSource(inputStreamSource, contentType, "inline");
            this.addInline(contentId, dataSource);
        }
    }

    public void addAttachment(String attachmentFilename, DataSource dataSource) throws MessagingException {
        Assert.notNull(attachmentFilename, "Attachment filename must not be null");
        Assert.notNull(dataSource, DATA_SOURCE_MUST_NOT_BE_NULL);

        try {
            MimeBodyPart ex = new MimeBodyPart();
            ex.setDisposition("attachment");
            ex.setFileName(MimeUtility.encodeText(attachmentFilename));
            ex.setDataHandler(new DataHandler(dataSource));
            this.getRootMimeMultipart().addBodyPart(ex);
        } catch (UnsupportedEncodingException var4) {
            throw new MessagingException("Failed to encode attachment filename", var4);
        }
    }

    public void addAttachment(String attachmentFilename, File file) throws MessagingException {
        Assert.notNull(file, FILE_MUST_NOT_BE_NULL);
        FileDataSource dataSource = new FileDataSource(file);
        dataSource.setFileTypeMap(this.getFileTypeMap());
        this.addAttachment(attachmentFilename, (DataSource) dataSource);
    }

    public void addAttachment(String attachmentFilename, InputStreamSource inputStreamSource) throws MessagingException {
        String contentType = this.getFileTypeMap().getContentType(attachmentFilename);
        this.addAttachment(attachmentFilename, inputStreamSource, contentType);
    }

    public void addAttachment(String attachmentFilename, InputStreamSource inputStreamSource, String contentType) throws MessagingException {
        Assert.notNull(inputStreamSource, "InputStreamSource must not be null");
        if (inputStreamSource instanceof Resource && ((Resource) inputStreamSource).isOpen()) {
            throw new IllegalArgumentException("Passed-in Resource contains an open stream: invalid argument. JavaMail requires an InputStreamSource that creates a fresh stream for every call.");
        } else {
            DataSource dataSource = this.createDataSource(inputStreamSource, contentType, attachmentFilename);
            this.addAttachment(attachmentFilename, dataSource);
        }
    }

    protected DataSource createDataSource(final InputStreamSource inputStreamSource, final String contentType, final String name) {
        return new DataSource() {
            public InputStream getInputStream() throws IOException {
                return inputStreamSource.getInputStream();
            }

            public OutputStream getOutputStream() {
                throw new UnsupportedOperationException("Read-only javax.activation.DataSource");
            }

            public String getContentType() {
                return contentType;
            }

            public String getName() {
                return name;
            }
        };
    }
}
