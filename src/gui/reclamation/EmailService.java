/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reclamation;

/**
 *
 * @author Nouha
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
    public void sendEmail(String recipientEmail, String subject, String content) {
        // Adresse e-mail de l'expéditeur
        String senderEmail = "contact.padeler@gmail.com";
        // Mot de passe de l'e-mail de l'expéditeur
        String senderPassword = "gkbdnropuyadyesy";
        
        
        // Vérifier si l'adresse e-mail du destinataire est null
    if (recipientEmail == null) {
        System.out.println("Adresse e-mail du destinataire invalide.");
        return;
    }

        // Propriétés du serveur SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Remplacez par l'adresse de votre serveur SMTP
        properties.put("mail.smtp.port", "587"); // Remplacez par le port de votre serveur SMTP
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Créez une session avec authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Créez un nouveau message
            Message message = new MimeMessage(session);
            // Définissez l'adresse de l'expéditeur
            message.setFrom(new InternetAddress(senderEmail));
            // Définissez l'adresse du destinataire
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            // Définissez l'objet de l'e-mail
            message.setSubject(subject);
            // Définissez le contenu de l'e-mail
            message.setText(content);

            // Envoyez l'e-mail
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès.");
        } catch (MessagingException ex) {
        }
    }
}