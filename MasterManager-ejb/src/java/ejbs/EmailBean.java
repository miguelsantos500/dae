package ejbs;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailBean {

    @Resource(name = "mail/dae")
    private Session mailSession;
    
    private static final Logger LOGGER = Logger.getLogger("ejbs.EmailBean");

    public void send(String to, String subject, String text) throws MessagingException {

        Message message = new MimeMessage(mailSession);

        LOGGER.log(Level.INFO, "Sending email to {0}, subject: {1}, text: {2}", 
                new Object[]{to, subject, text});
        LOGGER.log(Level.INFO, "PROPERTIES: {0}", 
                new Object[]{mailSession.getProperties()});
        
        try {
            // Adjust the recipients. Here we have only one recipient.
            // The recipient's address must be an object of the InternetAddress class.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Set the message's subject
            message.setSubject(subject);

            // Insert the message's body
            message.setText(text);

            // Adjust the date of sending the message
            Date timeStamp = new Date();
            message.setSentDate(timeStamp);

            // Use the 'send' static method of the Transport class to send the message
            Transport.send(message);

        } catch (MessagingException e) {
            throw e;
        }
    }
}