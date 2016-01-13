//package _ToDo;
//
//
//import control.MailController;
//import javafx.collections.ObservableList;
//import model.Klant;
//import model.Order;
//
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Properties;
//import java.util.prefs.Preferences;
//
///**
// * deze klas heeft de verantwoordelijkheid om de emails te verzenden.
// * 1. gmail properties of outlookproperties moeten geset worden
// * 2. create mail moet de email cre�ren, hier wordt de afzender ook geset.
// * 3. mailOntvangers toegevoegd en attachments als File toegevoegd.
// * 4. setOntvanger() methode aangeroepen die "SET" de ontvangers daadwerkelijk als ontvangers.
// * 5. subject geset
// * 6. body geset
// * 7. setFileAttachment() aangeroepen, deze set de PDF bestanden daadwerkelijk in de mail
// * 8. de setContentInMail() aangeroepen om het mail object te vullen met de opgehaalde informatie.
// * 9. setTransportReady() om een verbinding met de server op te zetten.
// * 10.sendMail() het verzenden van de mail over smtp
// * 11.closeTransport() voor het afsluiten van de connectie met de server
// * 12. reset() zet alle gegevens vervolgens klaar voor een nieuwe verzending.
// * een voor een worden ze vervolgens verstuurd, door te kijken hoeveel mails nog te gaan zijn en wat de index is
// * kunnen de mails
// * die verstuurd moeten worden verstuurd worden.
// *
// * @author dennis
// */
//public class MailSender {
//
//  MailController mc;
//
//  private ArrayList<String> ontvangerlijst;
//  private ArrayList<String> namenLijst;
//  private ArrayList<String> orderLijst;
//  private ArrayList<File> fileLijst;
//
//  private ArrayList<Order> orders;
//  private ArrayList<Klant> klanten;
//
//  /*mail properties*/
//  private String username;
//  private String password;
//  private String host;
//  private String mailAdres;
//  private String fileName;
//
//  private int portNumber;
//  private Properties systeemProperties;
//  private Session session;
//  private Transport transport;
//  private boolean transportReady;
//  private int mailsToSend;
//
//  private int huidigeMailIndex;
//
//  /*mail*/
//  private MimeMessage message;
//  private MimeBodyPart messageBodyPart;
//  private MimeBodyPart messageAttachmentPart;
//  private MimeMultipart messageMultiPart;
//
//  private Preferences prefs;
//
//  /**
//   * CONSTRUCTOR
//   * De constructor haalt de preferences voor de instellingen van de e-mail server in het register op.
//   *
//   * @param mailcontroller
//   */
//  public MailSender(MailController mc) {
//    this.mc = mc;
//    transportReady = false;
//    prefs = Settings.getInstance().getPrefs();
//    mailsToSend = 0;
//    huidigeMailIndex = 0;
//  }
//
//  /**
//   * Hier worden alle mail properties voor een gmail account opgeslagen.
//   * Deze methode zorgt dat de gmail server wordt gebruikt als server.
//   * Als laatst worden de objecten voor de informatie van een e-mail gemaakt.
//   *
//   * @author dennis
//   */
//  public void setUpGmailMailProperties() {
//    systeemProperties = new Properties();
//    host = "smtp.gmail.com";
//    portNumber = 587;
//
//    username = prefs.get("mail_id", "");
//    password = prefs.get("mail_pw", "");
//
//    systeemProperties.put("mail.smtp.host", host);
//    systeemProperties.put("mail.smtp.port", portNumber);
//    systeemProperties.put("mail.smtp.user", "username");
//    systeemProperties.put("mail.smtp.auth", "true");
//    systeemProperties.put("mail.smtp.starttls.enable", "true");
//    systeemProperties.put("mail.smtp.debug", "true");
//    systeemProperties.put("mail.smtp.reportsucces", true);
//
//    session = Session.getInstance(systeemProperties);
//    session.setDebug(true);
//
//    ontvangerlijst = new ArrayList<>();
//    namenLijst = new ArrayList<>();
//    orderLijst = new ArrayList<>();
//    fileLijst = new ArrayList<>();
//    orders = new ArrayList<>();
//    klanten = new ArrayList<>();
//
//  }
//
//  /**
//   * Hier worden alle mail properties voor een outlook account opgeslagen.
//   * Deze methode zorgt dat de outlook server wordt gebruikt als server.
//   * Als laatst worden de objecten voor de informatie van een e-mail gemaakt.
//   *
//   * @author dennis
//   */
//  public void setUpOutlookMailProperties() {
//    systeemProperties = new Properties();
//    host = "smtp-mail.outlook.com";
//    portNumber = 587;
//
//    username = prefs.get("mail_id", "");
//    password = prefs.get("mail_pw", "");
//
//    systeemProperties.put("mail.smtp.host", host);
//    systeemProperties.put("mail.smtp.port", portNumber);
//    systeemProperties.put("mail.smtp.user", "username");
//    systeemProperties.put("mail.smtp.auth", "true");
//    systeemProperties.put("mail.smtp.starttls.enable", "true");
//    systeemProperties.put("mail.smtp.debug", "true");
//    systeemProperties.put("mail.smtp.reportsucces", true);
//    session = Session.getInstance(systeemProperties);
//    session.setDebug(true);
//
//    ontvangerlijst = new ArrayList<>();
//    namenLijst = new ArrayList<>();
//    orderLijst = new ArrayList<>();
//    fileLijst = new ArrayList<>();
//    orders = new ArrayList<>();
//    klanten = new ArrayList<>();
//  }
//
//  /**
//   * In deze methode wordt het mail object aangemaakt en de afzender geplaatst als internet adres
//   *
//   * @throws messagingExepception
//   */
//  public void createMail() {
//    try {
//
//      message = new MimeMessage(session);
//      messageBodyPart = new MimeBodyPart();
//      messageMultiPart = new MimeMultipart();
//      message.setFrom(new InternetAddress(username));
//      message.saveChanges();
//    } catch (MessagingException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  /**
//   * Methode voor het toevoegen van ontvangers bij een e-mail.
//   * Hierin wordt ook bepaald hoeveel e-mails er verzonden moeten worden.
//   *
//   * @param ArrayList<String> adressen, ArrayList<String> namen
//   */
//  public void addOntvangers(ArrayList<String> adressen, ArrayList<String> namen) {
//    for (int i = 0; i < adressen.size(); i++) {
//      ontvangerlijst.add(adressen.get(i));
//      namenLijst.add(namen.get(i));
//    }
//    this.mailsToSend = ontvangerlijst.size();
//  }
//
//  /**
//   * Methode voor het toevoegen van de ordernummers
//   *
//   * @param orderNummers
//   */
//  public void addOrderNummers(ArrayList<String> orderNummers) {
//    for (int i = 0; i < orderNummers.size(); i++) {
//      orderLijst.add(orderNummers.get(i));
//    }
//  }
//
//  /**
//   * In deze methode wordt de ontvangerlijst in het message object gezet.
//   *
//   * @throws messagingException
//   */
//  public void setOntvanger() {
//    try {
//      message.setRecipient(Message.RecipientType.TO, new InternetAddress(ontvangerlijst.get(huidigeMailIndex)));
//    } catch (MessagingException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }
//
//  /**
//   * In deze methode wordt het onderwerp in het message object gezet.
//   *
//   * @param subject
//   * @throws MessagingException
//   */
//  public void setSubject(String subject) {
//    try {
//      message.setSubject(subject);
//      message.saveChanges();
//    } catch (MessagingException e) {
//      // TODO Auto-generated catch block
//      throw new RuntimeException(e);
//    }
//  }
//
//  /**
//   * Hier worden de files in een arrayList opgeslagen.
//   *
//   * @param files
//   */
//  public void addFiles(ArrayList<File> files) {
//    for (int i = 0; i < ontvangerlijst.size(); i++) {
//      fileLijst.add(files.get(i));
//    }
//  }
//
//  /**
//   * hier wordt een nieuw object gemaakt en de bijlage aan de mail vast gemaakt
//   * Eerst wordt er gecontroleerd of er al een attachment bestaan en wanneer dit zo is wordt deze verwijderd en
//   * vervangen door de nieuwe.
//   * Wanneer de uitnodigingview actief is wordt er 1 path geselecteerd die door fileChooser is uitgekozen.
//   *
//   * @throws messagingException
//   * @author dennis
//   */
//  public void setFileAttachment() {
//    if (messageAttachmentPart != null) {
//      try {
//        messageMultiPart.removeBodyPart(messageAttachmentPart);
//        messageAttachmentPart = new MimeBodyPart();//nieuwe attachment gemaakt
//        message.saveChanges();
//      } catch (MessagingException e1) {
//        // TODO Auto-generated catch block
//        e1.printStackTrace();
//      }
//    } else {
//      messageAttachmentPart = new MimeBodyPart();//nieuwe attachment gemaakt
//    }
//    try {
//      FileDataSource source = new FileDataSource(fileLijst.get(huidigeMailIndex));
//      if (mc.isUitnodigingenView()) {
//        messageAttachmentPart.setFileName(fileName);
//      } else {
//        messageAttachmentPart.setFileName("factuur_" + this.namenLijst.get(huidigeMailIndex) + "_" +
//                orderLijst.get(huidigeMailIndex) + ".pdf");
//      }
//
//      messageAttachmentPart.setDataHandler(new DataHandler(source));
//      message.saveChanges();
//    } catch (MessagingException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }
//
//  /**
//   * In deze methode wordt de body inhoud in de mail gezet.
//   *
//   * @param body
//   * @throws messagingException
//   */
//  public void setBody(String body) {
//    if (messageBodyPart != null) {
//      try {
//        messageMultiPart.removeBodyPart(messageBodyPart);
//        messageBodyPart.setText(body);
//        message.saveChanges();
//      } catch (MessagingException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//    } else {
//      try {
//        messageBodyPart.setText(body);
//        message.saveChanges();
//      } catch (MessagingException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//    }
//  }
//
//  /**
//   * In deze methode wordt eerst het transport klaargemaakt met de smtp server, vervolgens wordt een connectie met
//   * de server gemaakt.
//   * Hier worden verschillende foutmelding onderschept en een dialog message voor gegeven wanneer er een foumelding
//   * plaats vindt.
//   * Bij een foutmelding wordt een reset() uitgevoerd.
//   *
//   * @throws messagingException
//   * @author dennis
//   */
//  public void setTransportReady() {
//    try {
//      this.transport = session.getTransport("smtp");
//      transport.connect(host, username, password);
//      transportReady = true;
//    } catch (MessagingException e) {
//      e.printStackTrace();
//      if (e.getMessage().contains("Username and Password not accepted") || e.getMessage().contains
//              ("Authentication Failed")) {
//        reset();
//        mc.giveUsernameOrPasswordAlert(true);
//      } else if (e.getMessage().contains("Couldn't connect to host")) {
//        reset();
//        mc.giveConnectionAlert();
//      } else {
//        reset();
//        mc.giveNotDefaultAlert(e.getMessage(), e);
//      }
//    }
//  }
//
//  /**
//   * In deze methode wordt alle content in het message object gezet.
//   * Wanneer er geen bijlage in attachmentpart is bijgevoegd wordt er geen bijlage meegestuurd in het message object.
//   * Automatisch wordt een verzend datum toegevoegd aan de e-mail.
//   *
//   * @throws messagingException
//   */
//  public void setContentInMail() {
//    try {
//      message.setSentDate(new Date());
//      messageMultiPart.addBodyPart(messageBodyPart);
//      if (messageAttachmentPart != null) {
//        messageMultiPart.addBodyPart(messageAttachmentPart);
//      }
//      message.setContent(messageMultiPart);
//      message.saveChanges();
//    } catch (MessagingException e) {
//      e.printStackTrace();
//    }
//  }
//
//  /**
//   * In deze methode wordt de mail via smtp verzonden.
//   * De account gegevens worden meegestuurd.
//   * De volgende e-mail index wordt aangeroepen.
//   * Wanneer er orders zijn worden de facturen op verzonden gezet.
//   *
//   * @throws messagingException, ook wordt hier een dialog aangeroepen die de exception weergeeft.
//   */
//  public void sendMail() {
//    try {
//      transport.sendMessage(message, message.getAllRecipients());
//
//      if (orders.size() > 0) {
//        mc.updateVerzonden(orders.get(huidigeMailIndex));
//      }
//      nextMail();
//    } catch (MessagingException e) {
//      // TODO Auto-generated catch block
//      mc.giveNotDefaultAlert(e.getMessage(), e);
//      throw new RuntimeException(e);
//    }
//  }
//
//  /**
//   * In deze methode wordt het transport afgesloten en dit als boolean opgeslagen.
//   *
//   * @throws messagingException, ook word een dialog weergegeven met de exception in het dialog.
//   */
//  public void closeTransport() {
//    try {
//      transport.close();
//      transportReady = false;
//    } catch (MessagingException e) {
//      mc.giveNotDefaultAlert(e.getMessage(), e);
//      e.printStackTrace();
//    }
//  }
//
//  /**
//   * Hier wordt opgevraagd of het transport klaar staat.
//   *
//   * @return boolean tranportReady
//   */
//  public boolean getTransportReady() {
//    return transportReady;
//  }
//
//  /**
//   * In deze methode  wordt alle informatie over de ontvangers verwijderd.
//   * Ook wordt de informatie over de e-mail en aantal e-mails verwijderd.
//   */
//  public void reset() {
//    if (ontvangerlijst != null) {
//      ontvangerlijst.removeAll(ontvangerlijst);
//    }
//    if (fileLijst != null) {
//      fileLijst.removeAll(fileLijst);
//    }
//    if (orderLijst != null) {
//      orderLijst.removeAll(orderLijst);
//    }
//    if (namenLijst != null) {
//      namenLijst.removeAll(namenLijst);
//    }
//    if (orders != null) {
//      orders.removeAll(orders);
//      klanten.removeAll(klanten);
//    }
//    messageAttachmentPart = null;
//    fileName = "";
//    mailsToSend = 0;
//    huidigeMailIndex = 0;
//    transportReady = false;
//  }
//
//  /**
//   * In deze methode wordt de mailindex verhoogd met 1
//   */
//  public void nextMail() {
//    this.huidigeMailIndex++;
//  }
//
//  /**
//   * In deze methode wordt het huidige mailindex opgevraagd
//   *
//   * @return int huidige mail index
//   */
//  public int getHuidigeMailIndex() {
//    return this.huidigeMailIndex;
//  }
//
//  /**
//   * In deze methode wordt de orderlijst opgevraagd van de ontvangers
//   *
//   * @return ArrayList<String> orderlijst
//   */
//  public ArrayList<String> getOrderLijst() {
//    return orderLijst;
//  }
//
//  /**
//   * In deze methode wordt de namenlijst van de ontvangers opgevraagd.
//   *
//   * @return ArrayList<String> namenlijst
//   */
//  public ArrayList<String> getNamenLijst() {
//    return namenLijst;
//  }
//
//  /**
//   * In deze methode wordt de e-mail adressenlijst opgevraagd.
//   *
//   * @return ArrayList<String> e-mail adressen lijst.
//   */
//  public ArrayList<String> getEmailAdresLijst() {
//    return ontvangerlijst;
//  }
//
//  /**
//   * In deze methode wordt een integer getal opgevraagd die het aantal te verzenden mails teruggeeft
//   *
//   * @return int mails to send
//   */
//  public int getMailsToSend() {
//    return mailsToSend;
//  }
//
//  /**
//   * In deze methode worden de ontvangers toegevoegd.
//   *
//   * @param klanten
//   */
//  public void setKlanten(ObservableList<Klant> klanten) {
//    this.klanten.addAll(klanten);
//  }
//
//  /**
//   * In deze methode worden de orders toegevoegd.
//   *
//   * @param orders
//   */
//  public void addOrders(ArrayList<Order> orders) {
//    this.orders.addAll(orders);
//  }
//
//  /**
//   * In deze methode worden de bestanden toegevoegd.
//   *
//   * @param file
//   */
//  public void addFile(File file) {
//    // TODO Auto-generated method stub
//    fileLijst.add(file);
//  }
//
//  /**
//   * Deze methode geeft de filenaam terug.
//   *
//   * @return filenaam
//   */
//  public String getFileName() {
//    return fileName;
//  }
//
//  /**
//   * In deze methode kan de filenaam opgeslagen worden.
//   *
//   * @param fileName
//   */
//  public void setFileName(String fileName) {
//    this.fileName = fileName;
//  }
//}
