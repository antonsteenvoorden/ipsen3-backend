package IIIPSEN2.control;

import IIIPSEN2.interfaces.MailInhoud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import IIIPSEN2.knopHandlers.FacturenKnopHandler;
import IIIPSEN2.knopHandlers.HerinneringenKnopHandler;
import IIIPSEN2.knopHandlers.UitnodigingenHandler;
import IIIPSEN2.model.*;
import IIIPSEN2.view.HerinneringMailView;
import IIIPSEN2.view.MailOverzichtView;
import IIIPSEN2.view.UitnodigingMailView;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.prefs.Preferences;

/**
 * deze klas bestuurt de mailsender en de bijbehorende schermen om te kunnen mailen.
 * informatie over de volgorde van verzenden staat in MailSender.
 *
 * @author dennis
 */
@SuppressWarnings("PointlessBooleanExpression")
public class MailController implements UitnodigingenHandler, HerinneringenKnopHandler, FacturenKnopHandler {

    private MailOverzichtView mailOverzichtView;
    private HerinneringMailView herinneringMailView;
    private UitnodigingMailView uitnodigingMailView;
    private OrderController orderController;
    private KlantController klantController;
    private FactuurController factuurController;

    private Preferences prefs;

    private ArrayList<Factuur> facturenLijst;

    private ObservableList<Klant> selectie;
    private ObservableList<Klant> geenSelectie;
    private ArrayList<Order> orders;
    private MailSender es;
    private Alert alert;
    private boolean facturenIsVerzondenView;
    private boolean uitnodigingenView;
    private boolean restart;

    private File file;

    public boolean isFacturenIsVerzondenView() {
        return facturenIsVerzondenView;
    }

    public void setFacturenIsVerzondenView(boolean facturenIsVerzondenView) {
        this.facturenIsVerzondenView = facturenIsVerzondenView;
    }

    /**
     * CONSTRUCTOR
     * Hier worden de preferences opgehaald voor de toegang tot het register waarin de templates voor emails worde
     * opgeslagen.
     *
     * @param orderController
     * @param klantController
     * @param factuurController
     */
    public MailController(OrderController orderController, KlantController klantController, FactuurController
            factuurController) {
        this.orderController = orderController;
        this.klantController = klantController;
        this.factuurController = factuurController;
        this.facturenIsVerzondenView = false;
        prefs = Settings.getInstance().getPrefs();
    }

    /**
     * Deze methode wordt aangeroepen wanneer erop de uitnodigingen knop is gedrukt.
     * Als eerst wordt gekeken of er een geldig emailadres als verzend adres is meegegeven.
     * Daarna wordt de juiste server informatie toegewezen in emailsender aan de hand van het opgehaalde emailadres.
     * Lijsten voor de selectie en geen selectie worden gemaakt.
     * klant objecten worden opgehaald vanaf de controllers en in deze klas gezet.
     * De opgehaalde klanten worden in de selectie gezet om een email te ontvangen.
     * De mail uitnodigingen IIIPSEN2.view wordt geopend.
     */
    @Override
    public void uitnodigingenClicked() {
        uitnodigingenView = true;

        // TODO Auto-generated method stub
        String serverAdres = prefs.get("mail_id", "");
        if (serverAdres.isEmpty() || !serverAdres.contains("@")) {
            giveUsernameOrPasswordAlert(false);
        } else {
            String[] parts = serverAdres.split("@");
        
        /*singleton mail sender en ��n keer gmail properties goed gezet.*/
            if (es == null) {
                this.es = new MailSender(this);
            }
            if (parts[1].equals("gmail.com")) {
                es.setUpGmailMailProperties();
            } else {
                es.setUpOutlookMailProperties();
            }

            selectie = FXCollections.observableArrayList();
            geenSelectie = FXCollections.observableArrayList();

            //laat mailoverzicht zien
            if (this.uitnodigingMailView == null) {
                this.uitnodigingMailView = new UitnodigingMailView(this);
            }

            //in programma
            selectie.addAll(klantController.getActieveKlanten());
        }
    }

    /**
     * Deze methode wordt aangeroepen wanneer erop de herinnering knop is gedrukt.
     * Als eerst wordt gekeken of er een geldig emailadres als verzend adres is meegegeven.
     * Daarna wordt de juiste server informatie toegewezen in emailsender aan de hand van het opgehaalde emailadres.
     * Lijsten voor de selectie en geen selectie worden gemaakt.
     * Order objecten, klant objecten en actieve factuurobjecten worden opgehaald vanaf de controllers en in deze
     * klas gezet.
     * De opgehaalde klanten worden in de geen selectie gezet om een email te ontvangen.
     * De herinnering IIIPSEN2.view wordt geopend.
     */
    @Override
    public void herineringenClicked() {
        // TODO Auto-generated method stub

        String serverAdres = prefs.get("mail_id", "");
        if (serverAdres.isEmpty() || !serverAdres.contains("@")) {
            giveUsernameOrPasswordAlert(false);
        } else {
            String[] parts = serverAdres.split("@");
      
      /*singleton mail sender en ��n keer gmail properties goed gezet.*/
            if (es == null) {
                this.es = new MailSender(this);
            }
            if (parts[1].equals("gmail.com")) {
                es.setUpGmailMailProperties();
            } else {
                es.setUpOutlookMailProperties();
            }

            orders = new ArrayList<>();
            facturenLijst = new ArrayList<>();
            selectie = FXCollections.observableArrayList();
            geenSelectie = FXCollections.observableArrayList();

            facturenLijst.addAll(factuurController.getActieveFaceturenByIsVerzonden((true))); //laad actieve facturen
            // die WEL verzonden zijn.
            orders.addAll(orderController.getOrdersByFacturen(facturenLijst));

            //laat mailoverzicht zien
            if (this.herinneringMailView == null) {
                this.herinneringMailView = new HerinneringMailView(this);
            }
            geenSelectie.addAll(klantController.getKlantenByOrders(orders));
        }
    }

    /**
     * Deze methode wordt aangeroepen wanneer erop de facturen knop is gedrukt.
     * Als eerst wordt gekeken of er een geldig emailadres als verzend adres is meegegeven.
     * Daarna wordt de juiste server informatie toegewezen in emailsender aan de hand van het opgehaalde emailadres.
     * Lijsten voor de selectie en geen selectie worden gemaakt.
     * Order objecten, klant objecten en actieve factuurobjecten worden opgehaald vanaf de controllers en in deze
     * klas gezet.
     * De opgehaalde klanten worden in de selectie gezet om een email te ontvangen.
     * De mail facturen IIIPSEN2.view wordt geopend
     */
    @Override
    public void facturenClicked() {
        String serverAdres = prefs.get("mail_id", "");
        if (serverAdres.isEmpty() || !serverAdres.contains("@")) {
            giveUsernameOrPasswordAlert(false);
        } else {
            String[] parts = serverAdres.split("@");
      /*mail sender en ��n keer gmail properties of outlook properties goed gezet.*/
            if (es == null) {
                this.es = new MailSender(this);
            }
            if (parts[1].equals("gmail.com")) {
                es.setUpGmailMailProperties();
            } else {
                es.setUpOutlookMailProperties();
            }

            orders = new ArrayList<>();
            facturenLijst = new ArrayList<>();
            selectie = FXCollections.observableArrayList();
            geenSelectie = FXCollections.observableArrayList();

            //laat mailoverzicht zien
            if (this.mailOverzichtView == null) {
                this.mailOverzichtView = new MailOverzichtView(this);
            }
            facturenLijst.addAll(factuurController.getActieveFaceturenByIsVerzonden((facturenIsVerzondenView)));
            orders.addAll(orderController.getOrdersByFacturen(facturenLijst));
            selectie.addAll(klantController.getKlantenByOrders(orders));
        }
    }

    /**
     * methode haalt een lijst op met mail adressen, namen aan wie de mails verstuurt
     * worden, met behulp van de personen die geselecteerd zijn.
     * vervolgens wordt gechecked of er er een null waarde bij de email zit en wordt de opgehaalde informatie
     * doorgegeven aan de mailsender en
     * in een email gezet.
     *
     * @author dennis
     */
    public void setDOAInformation() {
        ArrayList<String> ontvangerLijst = new ArrayList<>();
        ArrayList<String> namenLijst = new ArrayList<>();
      
      /*de ontvanger emails worden geset
       * de namen worden geset,
       * aan de hand van klantObject.
       * 
       */
        for (int i = 0; i < selectie.size(); i++) {
            ontvangerLijst.add(selectie.get(i).getEmail());
            String selectieVoornaam = selectie.get(i).getVoornaam();
            String selectieAchternaam = selectie.get(i).getAchternaam();
            String selectieTussenvoegsel = selectie.get(i).getTussenvoegsel();
            if (selectieVoornaam == null || selectieVoornaam.isEmpty() || selectieVoornaam.equals("null")) {
                selectieVoornaam = "";
            }
            if (selectieAchternaam == null || selectieAchternaam.isEmpty() || selectieAchternaam.equals("null")) {
                selectieAchternaam = "";
            }
            if (selectieTussenvoegsel == null || selectieTussenvoegsel.isEmpty() || selectieTussenvoegsel.equals
                    ("null")) {
                selectieTussenvoegsel = "";
            }
            namenLijst.add(selectieVoornaam + " " + selectieTussenvoegsel + " " + selectieAchternaam);
        }        /*ontvangers worden hier aan de emailsender doorgegeven*/
        es.addOntvangers(ontvangerLijst, namenLijst);
        es.setKlanten(selectie);
    }

    /**
     * hier wordt de attachmentlijst en de ordernummers van de email opgehaald en de attachments naar de emailsender
     * klas gestuurd in de vorm van een java.io.file bestand.
     * de bestanden worden omgezet naar Files door het ophalen van de BLOB uit een Factuur of wanneer het een
     * uitnodigingsmail betreft opgehaald
     * uit het opgegeven pad door middel van een filChooser.
     * Als laatst worden de files aan de emailsender klas toegevoegd.
     */
    public void setDAOAttachments() {
        if (!uitnodigingenView && file == null) {
            ArrayList<String> orderNummerLijst = new ArrayList<>();
            ArrayList<File> attachmentLijst = new ArrayList<>();

            for (int i = 0; i < orders.size(); i++) {
                for (int j = 0; j < facturenLijst.size(); j++) {
                    if (orders.get(i).getOrderID() == facturenLijst.get(j).getOrderID()) {
                        attachmentLijst.add(facturenLijst.get(i).getFactuur());
                    }
                }
            }
           /*ordernummers worden toegevoegd aan mailsender klas  	  */
            for (int index = 0; index < orders.size(); index++) {
                String ordernummer = String.valueOf(orders.get(index).getOrderID());
                orderNummerLijst.add(ordernummer);
            }

            es.addOrderNummers(orderNummerLijst);
            es.addOrders(orders);
            es.addFiles(attachmentLijst);

        } else if (uitnodigingenView && file != null) {
            ArrayList<File> attachmentLijst = new ArrayList<>();

            for (int i = 0; i < selectie.size(); i++) {
                attachmentLijst.add(file);
            }
            es.addFiles(attachmentLijst);

        } else if (uitnodigingenView && file == null) {
            giveNoFileChoosedAlert();
        }
    }

    /**
     * hier wordt een uitnodiging file gekoppeld aan een file en een filepath.
     * vervolgens wordt de file aan de emailsender klas toegevoegd.
     *
     * @param file
     */
    public void setUitnodiging(File file) {
        this.file = file;
        Path filename = Paths.get(file.getAbsolutePath());
        es.setFileName(filename.getFileName().toString());
    }

    /**
     * wanneer er geen stabiele internetverbinding is zal in deze methode een dialog worden aangeroepen met de
     * informatie dat er geen verbinding mogelijk is.
     * vervolgens wordt de reset() methode aangeroepen.
     *
     * @author dennis
     */
    public void giveConnectionAlert() {
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Er is geen communicatie met de server mogelijk!");
        alert.setContentText("Sluit dit scherm en zorg voor een stabiele internetverbinding.");
        reset();
    }

    /**
     * in deze methode wordt een dialog opgeroepen en aangemaakt wanneer er nog geen dialog bestaat, met de
     * informatie dat de username of password die ingevoerd
     * is niet klopt.
     * als parameter kan aangegeven worden door een boolean of een reset wordt uitgevoerd of niet.
     * wanneer de reset uitgevoerd moet worden wordt de reset() methode aangeroeoen.
     *
     * @param reset
     */
    public void giveUsernameOrPasswordAlert(boolean reset) {
        if (alert == null) {
            alert = new Alert(AlertType.ERROR);
        }
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Er is geen communicatie met de server mogelijk!");
        alert.setContentText("controleer het ingestelde username en password.");
        alert.showAndWait();
        if (reset) {
            reset();
        }
    }

    /**
     * In deze methode wordt een dialog aangeroepen die een error weergeeft die niet gebruikelijk is.
     * De error message en de Exeception moeten meegegeven worden als parameter zodat deze in de uitschuifbare dialog
     * weergegeven kunnen worden.
     * Als laatste wordt een reset() aangeroepen.
     *
     * @param message
     * @param exception
     */
    public void giveNotDefaultAlert(String message, Exception exception) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setAlertType(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Er is geen communicatie met de server mogelijk!");
        alert.setContentText(message);

        Label label = new Label("De exception voluit is:");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        reset();
    }

    /**
     * Wanneer deze methode wordt aangeroepen wordt een confirmation dialog weergegeven die vraagt of de gebruiker
     * zeker weet dat er een mail verstuurt wordt zonder bijlage.
     * Wanneer vervolgens op "OK" wordt gedrukt worden de bijlagen wel aan de emails toegevoegd en maar omdat deze
     * leeg is worden de emails zonder bijlage verzonden .
     * Waneer op "CANCEL" wordt gedrukt wordt het dialog gesloten. De boolean restart wordt of true gezet zodat de
     * emails niet verzonden worden.
     */
    public void giveNoFileChoosedAlert() {
        this.alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Er is geen uitnodiging geselecteerd!");
        alert.setContentText("Weet u zeker dat u een mail stuurt zonder uitnodiging in de bijlage?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ArrayList<File> attachmentLijst = new ArrayList<>();
            for (int i = 0; i < selectie.size(); i++) {
                attachmentLijst.add(file);
            }
        } else if (result.get() == ButtonType.CANCEL) {
            alert.close();
            restart = true;
        }

    }

    public void giveNoSelectionAlert() {
        this.alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Er zijn geen ontvangers geselecteerd!");
        alert.setContentText("Alle dank e-mails zijn al verzonden of voeg een ontvanger toe aan de selectie.");
        alert.showAndWait();
    }

    /**
     * Wanneer deze methode wordt aangeroepen wordt een warning dialog weergegeven die een waarschuwing geeft dat het
     * template voor de email niet is gevuld.
     * Met integers wordt vervolgens aangegeven om welk onderdeel het van de email betreft.
     * 0 = de aanhef is niet gevuld
     * 1 = de body is niet gevuld
     * 2 = het onderwerp is niet gevuld.
     * wanneer op "OK" wordt gedrukt wordt de dialog weer gesloten.
     *
     * @param mailOnderdeel
     */
    public void giveTemplateNotFilledAlert(int mailOnderdeel) {
        if (mailOnderdeel == 0) {
            this.alert.setAlertType(AlertType.WARNING);
            alert.setTitle("Waarschuwing");
            alert.setHeaderText("De e-mail template is niet gevuld!");
            alert.setContentText("Vul de aanhef bij de e-mail template in het instellingen menu");
        } else if (mailOnderdeel == 1) {
            this.alert.setAlertType(AlertType.WARNING);
            alert.setTitle("Waarschuwing");
            alert.setHeaderText("De e-mail template is niet gevuld!");
            alert.setContentText("Vul de body bij de e-mail template in het instellingen menu");
        } else if (mailOnderdeel == 2) {
            this.alert.setAlertType(AlertType.WARNING);
            alert.setTitle("Waarschuwing");
            alert.setHeaderText("De e-mail template is niet gevuld!");
            alert.setContentText("Vul het onderwerp bij de e-mail template in het instellingen menu");
        }
    }

    /**
     * Deze methode geeft de namenlijst van de ontvangers terug.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getNamenLijst() {
        return es.getNamenLijst();
    }

    /**
     * Deze methode geeft de email adressenlijst van de ontvangers terug.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getEmailAdressenLijst() {
        return es.getEmailAdresLijst();
    }

    /**
     * Deze methode geeft de orderlijst van de ontvangers terug.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getOrderLijst() {
        return es.getNamenLijst();
    }

    /**
     * Deze methode geeft de selectielijst voor de ontvangers van de emails terug in de vorm van een Observablelist.
     *
     * @return ObservableList<Klant>
     */
    public ObservableList<Klant> getKlantLijst() {
        return selectie;
    }

    /**
     * In deze methode wordt de informatie die bij een ontvanger hoort die niet geselecteerd is, terug gezet naar de
     * selectie. Als parameter klant meegegeven die weer aan de selectie toegevoegd moet worden.
     * informatie die voor de email opnieuw opgehaald moet worden is:
     * - orders
     * - facturen
     * Nadat deze informatie opnieuw opgehaald is zal dit aan de lijst met orders en facturen meegegeven worden en de
     * klant aan de selectie toegevoegd tabel toegevoegd worden.
     * ook zal de klant uit het geenselectie tabel verwijderd worden.
     *
     * @param Klant
     */
    public void removeGeenSelectieKlant(Klant klant) {
        ArrayList<Factuur> alleFacturen = new ArrayList<>();
        ArrayList<Order> alleOrders = new ArrayList<>();

        alleFacturen.addAll(factuurController.getFacturenByIsVerzonden(facturenIsVerzondenView));
        alleOrders.addAll(orderController.getOrderByKlant(klant));

        for (int i = 0; i < alleOrders.size(); i++) {
            for (int j = 0; j < alleFacturen.size(); j++) {
                if (alleOrders.get(i).getOrderID() == alleFacturen.get(j).getOrderID()) {
                    orders.add(alleOrders.get(i));
                }
            }
        }

        for (int i = 0; i < alleFacturen.size(); i++) {
            for (int j = 0; j < klant.getOrders(klantController).size(); j++) {
                if (alleFacturen.get(i).getOrderID() == klant.getOrders(klantController).get(j).getOrderID()) {
                    facturenLijst.add(alleFacturen.get(i));
                }
            }
        }
        geenSelectie.remove(klant);
        selectie.add(klant);
        alleFacturen = null;
        alleOrders = null;
    }

    /**
     * Met deze methode worden de klant objecten opgehaald die geen selectie zijn voor de email. Deze worden
     * teruggegeven in de vorm van een Observablelist.
     *
     * @return ObservableList<Klant>
     */
    public ObservableList<Klant> getGeenSelectieLijst() {
        return geenSelectie;
    }

    /**
     * personen aan wie de mails worden verstuurd worden hier uit de selectieLijst gehaald.
     * de niet gebruikt orders worden verwijderd.
     * de facturen worden verwijderd wanneer het id gelijk is aan het order id van de te verwijderde orders van deze
     * klant.
     *
     * @author dennis
     */
    public void removeKlant(Klant klant) {
        ArrayList<Order> teVerwijderenOrders;
        teVerwijderenOrders = orderController.getOrderByKlant(klant);

        for (int i = 0; i < selectie.size(); i++) {
            selectie.remove(klant);
        }
        if (!isUitnodigingenView()) {
            //verwijderen van orders van klant
            for (int i = 0; i < orders.size(); i++) {
                for (int j = 0; j < teVerwijderenOrders.size(); j++) {
                    if (orders.get(i).getOrderID() == teVerwijderenOrders.get(j).getOrderID()) {
                        orders.remove(i);
                    }
                }
            }

            //verwijderen van facturen klant
            for (int j = 0; j < facturenLijst.size(); j++) {
                for (int i = 0; i < teVerwijderenOrders.size(); i++) {
                    if (facturenLijst.get(j).getOrderID() == teVerwijderenOrders.get(i).getOrderID()) {
                        facturenLijst.remove(j);
                    }
                }
            }
        }
        geenSelectie.add(klant);
        teVerwijderenOrders = null;
    }

    public boolean isUitnodigingenView() {
        return uitnodigingenView;
    }

    /**
     * Hier word de een boolean meegegeven die aangeeft of de uitnodigingenview actief is
     *
     * @param uitnodigingenView
     */
    public void setUitnodigingenView(boolean uitnodigingenView) {
        this.uitnodigingenView = uitnodigingenView;
    }

    /**
     * in deze methode worden alle dank e-mails verstuurd.
     * alle informatie is opgehaald en er wordt gekeken hoeveel e-mails er nog zijn te verzenden in e-mailsender.
     * Wanneer er geen selectie is voor ontvangers wordt een foutmelding weergegeven.
     * 1. Er wordt een dialog weergegeven die aangeeft hoeveel mails er worden verzonden.
     * 2. In emailsender wordt de email gemaakt, dat wordt hier aangeroepen.
     * 3. Aan de hand van de huidige mailindex en hoeveel mails verzonden moeten worden in emailsender klas, wordt
     * gekeken of er nog zijn mails om te verzenden
     * 4. De mails worden per onderdeel apart gevuld in de emailsender klas.
     * 5. Er wordt een transport in mailsender klas klaargezet.
     * 6. De emails zijn verzonden en het transport wordt afgesloten.
     * De email gegevens worden gereset door middel van de reset() methode.
     */
    public void sendDankMail() {

        if (selectie.size() > 0) {
      /*dank mail dao informatie naar email sender gestuurd*/
            setDOAInformation();
            setDAOAttachments();
      
      /*alertbox gemaakt die de aantal verzonden mails weergeeft*/
            this.alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Even geduld");
            alert.setHeaderText("De bedank e-mails worden verzonden met het factuur in de bijlage.");
            alert.setContentText("Even geduld, gereedmaken voor verzenden..");
            alert.show();
	
      /*mail object gemaakt*/
            es.createMail();

            while (es.getHuidigeMailIndex() < es.getMailsToSend()) {
        /*een nieuwe dankmail wordt gemaakt.
         * eerst wordt de mail volgens de goede volgorde aangemaakt.
         * vervolgens wordt de email sender klaargemaakt.
         * @author dennis*/

                MailInhoud dm = new DankMail(this);
                dm.setDefaultAanhef();
                dm.setName(es.getNamenLijst().get(es.getHuidigeMailIndex()));
                dm.setDefaultBody();
                dm.setOrderNummer(es.getOrderLijst().get(es.getHuidigeMailIndex()));
                dm.setDefaultSubject();
                es.setOntvanger();
                es.setSubject(dm.getMailSubject());
                es.setBody(dm.getMailBody());
                es.setFileAttachment();
                es.setContentInMail();
        
        /*wanneer er nog geen connectie met de server is wordt deze klaargemaakt*/
                if (!es.getTransportReady()) {
                    es.setTransportReady();
                }
        /*de mail wordt verzonden wanneer de connectie met de server is opgezet.*/
                if (es.getTransportReady()) {
                    es.sendMail();
                    alert.setContentText("e-mail " + es.getHuidigeMailIndex() + " van de " + es.getMailsToSend() + " " +
                            "verzonden..");
                }
            }
    /*transport wordt afgesloten en de gegevens worden gereset. De gegevens die worden gereset zijn:
     * ontvangerlijst
     * namenlijst
     * ordernummerlijst
     * filelijst
     * mailsToSend
     * transportReady
     * @author dennis
     */
            es.closeTransport();
            reset();
        } else {
            giveNoSelectionAlert();
        }
    }

    /**
     * in deze methode worden alle herinnering e-mails verstuurd.
     * alle informatie is opgehaald en er wordt gekeken hoeveel e-mails er nog zijn te verzenden in e-mailsender.
     * Wanneer er geen selectie is voor ontvangers wordt een foutmelding weergegeven.
     * 1. Er wordt een dialog weergegeven die aangeeft hoeveel mails er worden verzonden.
     * 2. In emailsender wordt de email gemaakt, dat wordt hier aangeroepen.
     * 3. Aan de hand van de huidige mailindex en hoeveel mails verzonden moeten worden in emailsender klas, wordt
     * gekeken of er nog zijn mails om te verzenden
     * 4. De mails worden per onderdeel apart gevuld in de emailsender klas.
     * 5. Er wordt een transport in mailsender klas klaargezet.
     * 6. De emails zijn verzonden en het transport wordt afgesloten.
     * De email gegevens worden gereset door middel van de reset() methode.
     */
    public void sendHerinneringMail() {

        if (selectie.size() > 0) {
      /*dank mail dao informatie naar email sender gestuurd*/
            setDOAInformation();
            setDAOAttachments();
      
      /*alertbox gemaakt die de aantal verzonden mails weergeeft*/
            this.alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Even geduld");
            alert.setHeaderText("De herinnering e-mails worden verzonden met het factuur in de bijlage.");
            alert.setContentText("Even geduld, gereedmaken voor verzenden..");
            alert.show();
    
      /*mail object gemaakt*/
            es.createMail();

            while (es.getHuidigeMailIndex() < es.getMailsToSend()) {
      
        /*een nieuwe dankmail wordt gemaakt.
         * eerst wordt de mail volgens de goede volgorde aangemaakt.
         * vervolgens wordt de email sender klaargemaakt.
         * @author dennis*/
                MailInhoud hm = new HerinneringMail(this);

                hm.setDefaultAanhef();
                hm.setName(es.getNamenLijst().get(es.getHuidigeMailIndex()));
                hm.setDefaultBody();
                hm.setOrderNummer(es.getOrderLijst().get(es.getHuidigeMailIndex()));
                hm.setDefaultSubject();
                es.setOntvanger();
                es.setSubject(hm.getMailSubject());
                es.setBody(hm.getMailBody());
                es.setFileAttachment();
                es.setContentInMail();
        
        /*wanneer er nog geen connectie met de server is wordt deze klaargemaakt*/
                if (!es.getTransportReady()) {
                    es.setTransportReady();
                }
      
        /*de mail wordt verzonden wanneer de connectie met de server is opgezet.*/
                if (es.getTransportReady()) {
                    es.sendMail();
                    alert.setContentText("e-mail " + es.getHuidigeMailIndex() + " van de " + es.getMailsToSend() + " " +
                            "verzonden..");
                }
            }
    /*transport wordt afgesloten en de gegevens worden gereset. De gegevens die worden gereset zijn:
     * ontvangerlijst
     * namenlijst
     * ordernummerlijst
     * filelijst
     * mailsToSend
     * transportReady
     * @author dennis
     */
            es.closeTransport();
            reset();
        } else {
            giveNoSelectionAlert();
        }
    }

    /**
     * in deze methode worden alle dank e-mails verstuurd.
     * alle informatie is opgehaald en er wordt gekeken hoeveel e-mails er nog zijn te verzenden in e-mailsender.
     * Wanneer er geen selectie is voor ontvangers wordt een foutmelding weergegeven.
     * 1. Er wordt een dialog weergegeven die aangeeft hoeveel mails er worden verzonden.
     * 2. In emailsender wordt de email gemaakt, dat wordt hier aangeroepen.
     * 3. Aan de hand van de huidige mailindex en hoeveel mails verzonden moeten worden in emailsender klas, wordt
     * gekeken of er nog zijn mails om te verzenden
     * 4. De mails worden per onderdeel apart gevuld in de emailsender klas.
     * 5. Er wordt een transport in mailsender klas klaargezet.
     * 6. De emails zijn verzonden en het transport wordt afgesloten.
     * De email gegevens worden gereset door middel van de reset() methode.
     */
    public void sendUitnodigingMail() {

        if (selectie.size() > 0) {
        /*dank mail dao informatie naar email sender gestuurd*/
            setDOAInformation();
            setDAOAttachments();

            if (!restart) {
          /*alertbox gemaakt die de aantal verzonden mails weergeeft*/
                this.alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Even geduld");
                alert.setHeaderText("De uitnodiging e-mails worden verzonden met het factuur in de bijlage.");
                alert.setContentText("Even geduld, gereedmaken voor verzenden..");
                alert.show();
        
          /*mail object gemaakt*/
                es.createMail();

                while (es.getHuidigeMailIndex() < es.getMailsToSend()) {
          
            /*een nieuwe dankmail wordt gemaakt.
             * eerst wordt de mail volgens de goede volgorde aangemaakt.
             * vervolgens wordt de email sender klaargemaakt.
             * @author dennis*/
                    MailInhoud um = new UitnodigingsMail(this);

                    um.setDefaultAanhef();
                    um.setName(es.getNamenLijst().get(es.getHuidigeMailIndex()));
                    um.setDefaultBody();
                    um.setDefaultSubject();
                    es.setOntvanger();
                    es.setSubject(um.getMailSubject());
                    es.setBody(um.getMailBody());
            
            /*gechecked wordt of er een file aanwezig is. Wanneer dit niet zo is omdat een uitnodiging verstuurd
            dient te worden zonder bijlage
             *wordt er geen file attachment in de mailsender toegevoegd
             *@author dennis*/
                    if (file != null) {
                        es.setFileAttachment();
                    }
                    es.setContentInMail();
            
            /*wanneer er nog geen connectie met de server is wordt deze klaargemaakt*/
                    if (!es.getTransportReady()) {
                        es.setTransportReady();
                    }
          
            /*de mail wordt verzonden wanneer de connectie met de server is opgezet.*/
                    if (es.getTransportReady()) {
                        es.sendMail();
                        alert.setContentText("e-mail " + es.getHuidigeMailIndex() + " van de " + es.getMailsToSend()
                                + " verzonden..");
                    }
                }
        /*transport wordt afgesloten en de gegevens worden gereset. De gegevens die worden gereset zijn:
         * ontvangerlijst
         * namenlijst
         * ordernummerlijst
         * filelijst
         * mailsToSend
         * transportReady
         * @author dennis
         */
                es.closeTransport();
                reset();
            } else {
          /*omdat restart true is betekend het dat de IIIPSEN2.view opnieuw moet worden gestart vanwege het niet selecteren
          van een uitnodigingbestand
           * het nieuwe aanroepen van het scherm wordt in de IIIPSEN2.view afgehandeld.
           *  @author dennis
           */
            }
        } else {
            giveNoSelectionAlert();
        }
    }

    /**
     * De methode geeft een boolean terug die aangeeft of er opnieuw opgestart is.
     *
     * @return boolean
     */
    public boolean isRestart() {
        return restart;
    }

    /**
     * In de methode kan de restart boolean veranderd worden.
     *
     * @param restart
     */
    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    /**
     * In deze methode wordt alle informatie omtrent de ontvangers voor het ontvangen van een email verwijderd en
     * attributen weer naar hun standaard waarden gebracht.
     * Als laatst wordt ook de emailsender reset.
     */
    public void reset() {
        selectie.remove(selectie);
        geenSelectie.remove(geenSelectie);
        if (orders != null) {
            orders.remove(orders);
        }
        if (facturenLijst != null) {
            facturenLijst.remove(facturenLijst);
        }
        mailOverzichtView = null;
        file = null;
        uitnodigingenView = false;
        restart = false;
        es.reset();
    }

    /**
     * Deze methode geeft door dat een factuur is verzonden. Als parameter wordt de order die verzonden is meegegeven.
     *
     * @param order
     */
    public void updateVerzonden(Order order) {
        factuurController.updateIsVerzondenByOrder(order, true);
    }
}


