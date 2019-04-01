/*Copyright (c) 2018-2019 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.services.emailmanager;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
import com.wavemaker.runtime.service.annotations.HideFromClient;


//import com.services.emailmanager.model.*;

/**
 * This is a singleton class with all its public methods exposed as REST APIs via generated controller class.
 * To avoid exposing an API for a particular public method, annotate it with @HideFromClient.
 *
 * Method names will play a major role in defining the Http Method for the generated APIs. For example, a method name
 * that starts with delete/remove, will make the API exposed as Http Method "DELETE".
 *
 * Method Parameters of type primitives (including java.lang.String) will be exposed as Query Parameters &
 * Complex Types/Objects will become part of the Request body in the generated API.
 *
 * NOTE: We do not recommend using method overloading on client exposed methods.
 */
@ExposeToClient
public class EmailManager {

    private static final Logger logger = LoggerFactory.getLogger(EmailManager.class);
    private Session session;

    private boolean authentication=true;
    private boolean smtpServerTTLSEnabled = true;
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String username="<Username>";
    private String password="<password>";

    @PostConstruct
    public void init() throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", String.valueOf(authentication));
        props.put("mail.smtp.starttls.enable",smtpServerTTLSEnabled);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendEmail(String toEmailAddress, String emailSubject, String emailMessage) {
        logger.info("toEmailAddress {}, emailSubject {}, emailMessage {} ",
        toEmailAddress,emailSubject,emailMessage);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            String[] recipientList = toEmailAddress.split(",");
            InternetAddress[] recipientAddresses = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient: recipientList) {
                recipientAddresses[counter] = new InternetAddress(recipient.trim());
                counter++;
                }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);
            message.setSubject(emailSubject);
            message.setText(emailMessage);
            Transport.send(message);
            logger.info("Sent message successfully....");
             } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
}