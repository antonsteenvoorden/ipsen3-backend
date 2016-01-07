package IIIPSEN2;

import IIIPSEN2.interfaces.OrderHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Roger Bosman, Jordan Munk
 *         <p>
 *         Order is het datamodel waarin alle informatie over een Order opgeslagen en opgevraagd kan worden.
 */

public class Order {
    /**
     * Order bevat Simple<datatye>Poperty's aangezien de tableviews in de IIIPSEN2.view hier goed mee om kunnen gaan.
     * de Properties omdvatten alle velden behalve de creationdate en de arraylist van orderregels die een order kan
     * bevatten
     */
    private SimpleIntegerProperty orderID = new SimpleIntegerProperty();
    private SimpleStringProperty klantEmail = new SimpleStringProperty();
    private SimpleStringProperty factuurAdres = new SimpleStringProperty();
    private Timestamp orderDatum;
    private SimpleIntegerProperty isActief = new SimpleIntegerProperty();

    private ArrayList<OrderRegel> orderRegels;

    public Order(String klantEmail, String factuurAdres, int isActief) {
        setKlantEmail(klantEmail);
        setFactuurAdres(factuurAdres);
        setIsActief(isActief);
    }

    public int getOrderID() {
        return orderID.get();
    }

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }

    public SimpleIntegerProperty orderIDProperty() {
        return orderID;
    }

    public String getKlantEmail() {
        return klantEmail.get();
    }

    public void setKlantEmail(String klantEmail) {
        this.klantEmail.set(klantEmail);
    }

    public SimpleStringProperty klantEmailProperty() {
        return klantEmail;
    }

    public String getFactuurAdres() {
        return factuurAdres.get();
    }

    public void setFactuurAdres(String factuurAdres) {
        this.factuurAdres.set(factuurAdres);
    }

    public SimpleStringProperty factuurAdresProperty() {
        return factuurAdres;
    }

    public Timestamp getOrderDatum() {
        return orderDatum;
    }

    public void setOrderDatum(Timestamp orderDatum) {
        this.orderDatum = orderDatum;
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

    public ArrayList<OrderRegel> getOrderRegels(OrderHandler orderHandler) {
        if (orderRegels == null) {
            this.orderRegels = orderHandler.getOrderRegelByOrder(this);
        }
        return orderRegels;
    }

    public void setOrderRegels(ArrayList<OrderRegel> orderRegels) {
        this.orderRegels = orderRegels;
    }
}
