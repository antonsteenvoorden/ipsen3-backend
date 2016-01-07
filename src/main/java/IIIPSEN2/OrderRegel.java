package IIIPSEN2;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Roger Bosman, Jordan Munk
 *         <p>
 *         OrderRegel is het datamodel waarin alle informatie over een OrderRegel opgeslagen en opgevraagd kan worden.
 */

public class OrderRegel {
    /**
     * OrderRegel bevat Simple<datatye>Poperty's aangezien de tableviews in de IIIPSEN2.view hier goed mee om kunnen gaan.
     * de Properties omdvatten alle velden behalve een referentie van wijn waar een orderregel naar kan verwijzen.
     */
    private SimpleIntegerProperty orderRegelID = new SimpleIntegerProperty();
    private SimpleIntegerProperty wijnID = new SimpleIntegerProperty();
    private SimpleStringProperty wijnNaam = new SimpleStringProperty();
    private SimpleIntegerProperty wijnJaartal = new SimpleIntegerProperty();
    private SimpleIntegerProperty aantal = new SimpleIntegerProperty();
    private SimpleIntegerProperty orderID = new SimpleIntegerProperty();
    private SimpleIntegerProperty isActief = new SimpleIntegerProperty();
    private SimpleDoubleProperty totaalPrijs = new SimpleDoubleProperty();
    private SimpleDoubleProperty wijnPrijs = new SimpleDoubleProperty();

    private Wijn wijn;

    public OrderRegel(int wijnID, String wijnNaam, int wijnJaartal, int aantal, double wijnPrijs, int isActief) {
        this.wijnID.set(wijnID);
        this.wijnNaam.set(wijnNaam);
        this.wijnJaartal.set(wijnJaartal);
        this.aantal.set(aantal);
        this.isActief.set(isActief);
        this.wijnPrijs.set(wijnPrijs);

        int r = (int) Math.round(wijnPrijs * aantal * 6 * 100);
        double f = r / 100.0;
        this.totaalPrijs.set(f);
    }

    public int getWijnID() {
        return wijnID.get();
    }

    public void setWijnID(int wijnID) {
        this.wijnID.set(wijnID);
    }

    public SimpleIntegerProperty wijnIDProperty() {
        return wijnID;
    }

    public int getOrderRegelID() {
        return orderRegelID.get();
    }

    public void setOrderRegelID(int orderRegelID) {
        this.orderRegelID.set(orderRegelID);
    }

    public SimpleIntegerProperty orderRegelIDProperty() {
        return orderRegelID;
    }

    public String getWijnNaam() {
        return wijnNaam.get();
    }

    public void setWijnNaam(String wijnNaam) {
        this.wijnNaam.set(wijnNaam);
    }

    public SimpleStringProperty wijnNaamProperty() {
        return wijnNaam;
    }

    public int getAantal() {
        return aantal.get();
    }

    public void setAantal(int aantal) {
        this.aantal.set(aantal);
    }

    public SimpleIntegerProperty aantalProperty() {
        return aantal;
    }

    public int getWijnJaartal() {
        return wijnJaartal.get();
    }

    public void setWijnJaartal(int jaartal) {
        this.wijnJaartal.set(jaartal);
    }

    public SimpleIntegerProperty wijnJaartalProperty() {
        return wijnJaartal;
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

    public int getIsActief() {
        return isActief.get();
    }

    public void setIsActief(int isActief) {
        this.isActief.set(isActief);
    }

    public SimpleIntegerProperty isActiefProperty() {
        return isActief;
    }

    public double getTotaalPrijs() {
        return totaalPrijs.get();
    }

    public void setTotaalPrijs(double totaalPrijs) {
        this.totaalPrijs.set(totaalPrijs);
    }

    public SimpleDoubleProperty totaalPrijsProperty() {
        return totaalPrijs;
    }

    public void setWijn(Wijn wijn) {
        this.wijn = wijn;
    }

    public double getWijnPrijs() {
        return wijnPrijs.get();
    }

    public void setWijnPrijs(double wijnPrijs) {
        this.wijnPrijs.set(wijnPrijs);
    }

    public SimpleDoubleProperty wijnPrijsProperty() {
        return wijnPrijs;
    }
}
