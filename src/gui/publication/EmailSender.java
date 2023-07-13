/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.publication;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author safdh
 */
public class EmailSender {
        String sender = "contact.padeler@gmail.com";
    final String senderPassword = "gkbdnropuyadyesy";

    String reciever = "saf.dhaifallah@gmail.com";
    String message = "Bonjour ,/n vous avez ajouté une publication ";
    String obj = "Padeler";

public void sendEmail() {
    System.out.println("offffffff");
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    // Créer une session avec l'authentification
    Session session = Session.getInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sender, senderPassword);
        }
    });

    try {
        // Créer le message
        Message emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress(sender));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciever));
        emailMessage.setSubject(obj);
        emailMessage.setText(message);

        // Envoyer le message
        Transport.send(emailMessage);

        System.out.println("Email sent successfully.");
    } catch (MessagingException e) {
        System.out.println("Failed to send email. Error: " + e.getMessage());
    }
}
}
    

