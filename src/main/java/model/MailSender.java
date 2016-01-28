package model;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Collection;
import java.util.Properties;

/**
 * @author Anton Steenvoorden
 * Verstuurd de mail vanaf het email adres dat in de .yml staat
 * Maakt gebruikt van of GMAIL SMTP server of die van Microsoft
 */
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

  /**
   * Ontvangt een Collectie van Klanten als ontvangers, loopt hier doorheen om de bijhorende
   * email adressen als InternetAdress te converteren.
   * @param ontvangers
   * @throws AddressException
   */
  public void setOntvangers(Collection<Klant> ontvangers) throws AddressException {
    this.ontvangers = new InternetAddress[ontvangers.size()];

    int i = 0;
    for (Klant ontvanger : ontvangers) {
      this.ontvangers[i] = new InternetAddress(ontvanger.getEmail());
      i++;
    }
  }

  /**
   * Ontvangt een enkel emailadres in de parameter, deze wordt gebruikt voor het verzenden
   * van het wachtwoord naar de gebruiker, en eventueel een confirmatie mail
   * @param ontvanger
   * @throws AddressException
   */
  public void setOntvangers(String ontvanger) throws AddressException {
    this.ontvangers = new InternetAddress[1];
    ontvangers[0] =  new InternetAddress(ontvanger);
  }

  public MailSender(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Maakt de mailsender gereed om vanaf een gmail host de mail te versturen
   * Hierbij worden de gebruiker, wachtwoord, host, poortnummer en protocol eigenschappen gevuld
   */
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

  /**
   * Maakt de mailsender gereed om vanaf een outlook host de mail te versturen
   * * Hierbij worden de gebruiker, wachtwoord, host, poortnummer en protocol eigenschappen gevuld
   */
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

  /**
   * Methode die de mail daadwerkelijk verstuurd,
   * controleert of de mail waar vanaf wordt verzonden gmail of van microsoft is en kiest een van
   * de twee set properties methodes.
   * Gebruikt de ontvangers en het bericht om de mail te verzenden.
   * Gebruikt ook een GMailAuthenticator om het wachtwoord en de gebruikersnaam als authenticatie
   * te gebruiken
   */
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


