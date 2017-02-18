package com.along.android.healthmanagement.helpers;

import android.os.AsyncTask;
import android.util.Log;

import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHelper extends AsyncTask<String, Void, Void> {
    private void sendEmail(String emailAddress, String subject, String content) {
        final String fromUsername = "noreply.healthmanagement@gmail.com";
        final String fromPassword = "healthmanagerment";

        Properties props = new Properties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromUsername, fromPassword);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Void doInBackground(String... args) {
        String emailAddress = args[0];
        String subject = args[1];
        String content = args[2];
        sendEmail(emailAddress, subject, content);
        return null;
    }
}
