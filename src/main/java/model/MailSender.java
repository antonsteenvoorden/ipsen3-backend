package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Nieuwsbrief;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailSender {

  /*mail properties*/
  private String username;
  private String password;

  private String host;
  private int portNumber;
  private Session session;
  private Transport transport;
  private Properties systeemProperties;

  private Nieuwsbrief nieuwsBrief;
  private InternetAddress[] ontvangers;


  public void setNieuwsbrief(Nieuwsbrief nieuwsBrief) {
    this.nieuwsBrief = nieuwsBrief;
  }
  public void setOntvangers(InternetAddress[] ontvangers) {
    this.ontvangers = ontvangers;
  }

  public MailSender(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void setUpGmailMailProperties() {
    systeemProperties = new Properties();
    host = "smtp.gmail.com";
    portNumber = 587;

    systeemProperties.put("mail.smtp.host", host);
    systeemProperties.put("mail.smtp.port", portNumber);
    systeemProperties.put("mail.smtp.user", "username");
    systeemProperties.put("mail.smtp.auth", "true");
    systeemProperties.put("mail.smtp.starttls.enable", "true");
    systeemProperties.put("mail.smtp.debug", "true");
    systeemProperties.put("mail.smtp.reportsucces", true);

    session = Session.getInstance(systeemProperties);
    session.setDebug(true);


  }

  public void setUpOutlookMailProperties() {
    systeemProperties = new Properties();
    host = "smtp-mail.outlook.com";
    portNumber = 587;

    systeemProperties.put("mail.smtp.host", host);
    systeemProperties.put("mail.smtp.port", portNumber);
    systeemProperties.put("mail.smtp.user", "username");
    systeemProperties.put("mail.smtp.auth", "true");
    systeemProperties.put("mail.smtp.starttls.enable", "true");
    systeemProperties.put("mail.smtp.debug", "true");
    systeemProperties.put("mail.smtp.reportsucces", true);
    session = Session.getInstance(systeemProperties);
    session.setDebug(true);

  }

  public void sendMail() {
    if(username.contains("gmail.com")) {
      setUpGmailMailProperties();
    } else {
      setUpOutlookMailProperties();
    }
    Session session = Session.getDefaultInstance(systeemProperties);
    // Create a default MimeMessage object.
    MimeMessage message = new MimeMessage(session);
    // Get the default Session object.


    try {
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.BCC, ontvangers);

      // Set Subject: header field
      message.setSubject(nieuwsBrief.getOnderwerp());

      // Now set the actual message
      message.setContent(nieuwsBrief.getTekst(), "text/html");

      // Send message
      Transport.send(message);

    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}

