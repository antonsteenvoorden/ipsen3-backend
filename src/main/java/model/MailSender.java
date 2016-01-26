package model;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Collection;
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

  private Mail bericht;
  private InternetAddress[] ontvangers;


  public void setNieuwsbrief(Mail bericht) {
    this.bericht = bericht;
  }

  public void setOntvangers(Collection<Klant> ontvangers) throws AddressException {
    this.ontvangers = new InternetAddress[ontvangers.size()];

    int i = 0;
    for (Klant ontvanger : ontvangers) {
      this.ontvangers[i] = new InternetAddress(ontvanger.getEmail());
      i++;
    }
  }

  public void setOntvangers(String ontvanger) throws AddressException {
    this.ontvangers = new InternetAddress[1];
    ontvangers[0] =  new InternetAddress(ontvanger);
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
    systeemProperties.put("mail.smtp.user", username);
    systeemProperties.put("mail.smtp.password", password);
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
    systeemProperties.put("mail.smtp.user", username);
    systeemProperties.put("mail.smtp.password", password);
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
      session = Session.getInstance(systeemProperties, new GMailAuthenticator(username, password));
    } else {
      setUpOutlookMailProperties();
    }
    // Create a default MimeMessage object.
    MimeMessage message = new MimeMessage(session);

    try {
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.BCC, ontvangers);

      // Set Subject: header field
      message.setSubject(bericht.getOnderwerp());

      // Now set the actual message
      message.setContent(bericht.getTekst(), "text/html");

      Transport.send(message);
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}
class GMailAuthenticator extends Authenticator {
  String user;
  String pw;
  public GMailAuthenticator (String username, String password)
  {
    super();
    this.user = username;
    this.pw = password;
  }
  public PasswordAuthentication getPasswordAuthentication()
  {
    return new PasswordAuthentication(user, pw);
  }
}

