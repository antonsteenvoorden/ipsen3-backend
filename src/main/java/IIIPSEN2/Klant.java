package IIIPSEN2;


import IIIPSEN2.interfaces.KlantHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * @author Roger Bosman, Anton Steenvoorden
 *         <p>
 *         klant is het datamodel waarin alle informatie over een klant opgeslagen en opgevraagd kan worden.
 */

public class Klant {

    /**
     * Klant bevat Simple<datatye>Poperty's aangezien de tableviews in de IIIPSEN2.view hier goed mee om kunnen gaan.
     * de Properties omdvatten alle velden behalve de creationdate en de arraylist van orders die een klant kan bevatten
     */
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty voornaam = new SimpleStringProperty();
    private SimpleStringProperty tussenvoegsel = new SimpleStringProperty();
    private SimpleStringProperty achternaam = new SimpleStringProperty();
    private SimpleStringProperty straatnaam = new SimpleStringProperty();
    private SimpleIntegerProperty huisNummer = new SimpleIntegerProperty();
    private SimpleStringProperty huisNummerToevoeging = new SimpleStringProperty();
    private SimpleIntegerProperty postcode = new SimpleIntegerProperty();
    private SimpleStringProperty postcodeToevoeging = new SimpleStringProperty();
    private SimpleStringProperty plaatsNaam = new SimpleStringProperty();
    private SimpleStringProperty telefoon = new SimpleStringProperty();
    private SimpleStringProperty gastLid = new SimpleStringProperty();
    private SimpleStringProperty notitie = new SimpleStringProperty();
    private SimpleIntegerProperty isActief = new SimpleIntegerProperty();
    private SimpleStringProperty dateString = new SimpleStringProperty();

    //specific IIIPSEN2.view properties
    private SimpleStringProperty volledigAdres = new SimpleStringProperty(); //straat, huisnummer,
    // huisnummertoevoeging, postcode

    private Timestamp creationDate;

    private ArrayList<Order> orders;

    /**
     * Default constructor
     *
     * @param email
     * @param voornaam
     * @param tussenvoegsel
     * @param achternaam
     * @param straatnaam
     * @param huisNummer
     * @param huisNummerToevoeging
     * @param postcode
     * @param postcodeToevoeging
     * @param plaatsNaam
     * @param telefoon
     * @param gastLid
     * @param notitie
     * @param isActief
     * @param creationDate
     * @author Roger Bosman
     */
    public Klant(String email, String voornaam, String tussenvoegsel, String achternaam, String straatnaam, int
            huisNummer, String huisNummerToevoeging, int postcode, String postcodeToevoeging, String plaatsNaam,
                 String telefoon, String gastLid, String notitie, int isActief, Timestamp creationDate) {
        setEmail(email);
        setVoornaam(voornaam);
        setTussenvoegsel(tussenvoegsel);
        setAchternaam(achternaam);
        setStraatnaam(straatnaam);
        setHuisNummer(huisNummer);
        setHuisNummerToevoeging(huisNummerToevoeging);
        setPostcode(postcode);
        setPostcodeToevoeging(postcodeToevoeging);
        setPlaatsNaam(plaatsNaam);
        setTelefoon(telefoon);
        setGastLid(gastLid);
        setNotitie(notitie);
        setIsActief(isActief);
        setCreationDate(creationDate);
    }

    /**
     * Constructor met Optional<integer> datatypen voor huisnummer en postcode waardoor deze niet hoeven te bestaan.
     *
     * @param email
     * @param voornaam
     * @param tussenvoegsel
     * @param achternaam
     * @param straatnaam
     * @param huisNummer
     * @param huisNummerToevoeging
     * @param postcode
     * @param postcodeToevoeging
     * @param plaatsNaam
     * @param telefoon
     * @param gastLid
     * @param notitie
     * @param isActief
     * @param creationDate
     * @author Roger Bosman
     */
    public Klant(String email, String voornaam, String tussenvoegsel, String achternaam, String straatnaam,
                 Optional<Integer>
                         huisNummer, String huisNummerToevoeging, Optional<Integer> postcode, String
                         postcodeToevoeging, String
                         plaatsNaam,
                 String telefoon, String gastLid, String notitie, int isActief, Timestamp creationDate) {
        setEmail(email);
        setVoornaam(voornaam);
        setTussenvoegsel(tussenvoegsel);
        setAchternaam(achternaam);
        setStraatnaam(straatnaam);
        if (huisNummer != null) {
            setHuisNummer(huisNummer.get());
        }
        setHuisNummerToevoeging(huisNummerToevoeging);
        if (postcode != null) {
            setPostcode(postcode.get());
        }
        setPostcodeToevoeging(postcodeToevoeging);
        setPlaatsNaam(plaatsNaam);
        setTelefoon(telefoon);
        setGastLid(gastLid);
        setNotitie(notitie);
        setIsActief(isActief);
        setCreationDate(creationDate);
    }

    public String getGastLid() {
        return gastLid.get();
    }

    public void setGastLid(String gastLid) {
        if (gastLid != null) {
            if (!gastLid.equals("")) {
                this.gastLid.set(gastLid);
            }
        }
    }

    public SimpleStringProperty gastLidProperty() {
        return gastLid;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        if (email != null) {
            if (!email.equals("")) {
                this.email.set(email);
            }
        }
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getVoornaam() {
        return voornaam.get();
    }

    public void setVoornaam(String voornaam) {
        if (voornaam != null) {
            if (!voornaam.equals("")) {
                this.voornaam.set(voornaam);
            }
        }
    }

    public SimpleStringProperty voornaamProperty() {
        return voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel.get();
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        if (tussenvoegsel != null) {
            if (!tussenvoegsel.equals("")) {
                this.tussenvoegsel.set(tussenvoegsel);
            }
        }
    }

    public SimpleStringProperty tussenvoegselProperty() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam.get();
    }

    public void setAchternaam(String achternaam) {
        if (achternaam != null) {
            if (!achternaam.equals("")) {
                this.achternaam.set(achternaam);
            }
        }
    }

    public SimpleStringProperty achternaamProperty() {
        return achternaam;
    }

    public String getStraatnaam() {
        return straatnaam.get();
    }

    public void setStraatnaam(String straatnaam) {
        if (straatnaam != null) {
            if (!straatnaam.equals("")) {
                this.straatnaam.set(straatnaam);
            }
        }
    }

    public SimpleStringProperty straatnaamProperty() {
        return straatnaam;
    }

    public int getHuisNummer() {
        return huisNummer.get();
    }

    public void setHuisNummer(int huisNummer) {
        this.huisNummer.set(huisNummer);
    }

    public SimpleIntegerProperty huisNummerProperty() {
        return huisNummer;
    }

    public String getHuisNummerToevoeging() {
        return huisNummerToevoeging.get();
    }

    public void setHuisNummerToevoeging(String huisNummerToevoeging) {
        if (huisNummerToevoeging != null) {
            if (!huisNummerToevoeging.equals("")) {
                this.huisNummerToevoeging.set(huisNummerToevoeging);
            }
        }
    }

    public SimpleStringProperty huisNummerToevoegingProperty() {
        return huisNummerToevoeging;
    }

    public int getPostcode() {
        return postcode.get();
    }

    public void setPostcode(int postcode) {
        this.postcode.set(postcode);
    }

    public SimpleIntegerProperty postcodeProperty() {
        return postcode;
    }

    public String getPostcodeToevoeging() {
        return postcodeToevoeging.get();
    }

    public void setPostcodeToevoeging(String postcodeToevoeging) {
        if (postcodeToevoeging != null) {
            if (!postcodeToevoeging.equals("")) {
                this.postcodeToevoeging.set(postcodeToevoeging);
            }
        }
    }

    public SimpleStringProperty postcodeToevoegingProperty() {
        return postcodeToevoeging;
    }

    public String getPlaatsNaam() {
        return plaatsNaam.get();
    }

    public void setPlaatsNaam(String plaatsNaam) {
        if (plaatsNaam != null) {
            if (!plaatsNaam.equals("")) {
                this.plaatsNaam.set(plaatsNaam);
            }
        }
    }

    public SimpleStringProperty plaatsNaamProperty() {
        return plaatsNaam;
    }

    public String getTelefoon() {
        return telefoon.get();
    }

    public void setTelefoon(String telefoon) {
        if (telefoon != null) {
            if (!telefoon.equals("")) {
                this.telefoon.set(telefoon);
            }
        }
    }

    public SimpleStringProperty telefoonProperty() {
        return telefoon;
    }

    public String getNotitie() {
        return notitie.get();
    }

    public void setNotitie(String notitie) {
        if (notitie != null) {
            if (!notitie.equals("")) {
                this.notitie.set(notitie);
            }
        }
    }

    public SimpleStringProperty notitieProperty() {
        return notitie;
    }

    public int getIsActief() {
        return isActief.get();
    }

    public void setIsActief(int isActief) {
        this.isActief.set(isActief);
    }

    public SimpleIntegerProperty isActiefProperty() {
        return isActief;
    }

    public ArrayList<Order> getOrders(KlantHandler klantHandler) {
        if (orders == null) {
            this.orders = klantHandler.getOrderByKlant(this);
        }
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public String getDateString() {
        return dateString.get();
    }

    public SimpleStringProperty dateStringProperty() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString.set(dateString);
    }

    /**
     * de toDate functie convert de Timestamp in een String met formaat yyyy-MM-dd
     *
     * @param timestamp te converten timestamp
     * @return de gegenereerde string volgens format yyyy-MM-dd
     * @author Roger Bosman
     */
    private String toDate(long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public void setCreationDate(Timestamp creationDate) {
        if (creationDate != null) {
            this.creationDate = creationDate;
            this.setDateString(toDate(creationDate.getTime()));
        }
    }
}