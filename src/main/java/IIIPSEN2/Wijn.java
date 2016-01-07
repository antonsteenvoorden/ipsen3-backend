package IIIPSEN2;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Roger Bosman, Sidney de Geus
 *         <p>
 *         Wijn class is een data IIIPSEN2.model voor een wijn object.
 */

public class Wijn {

    private SimpleIntegerProperty wijnID = new SimpleIntegerProperty();
    private SimpleIntegerProperty wijnSerieID = new SimpleIntegerProperty();
    private SimpleStringProperty wijnNaam = new SimpleStringProperty();
    private SimpleDoubleProperty inkoopPrijs = new SimpleDoubleProperty();
    private SimpleDoubleProperty prijs = new SimpleDoubleProperty();
    private SimpleIntegerProperty wijnType = new SimpleIntegerProperty();
    private SimpleStringProperty wijnCategory = new SimpleStringProperty();
    private SimpleStringProperty wijnAfkomst = new SimpleStringProperty();
    private SimpleIntegerProperty wijnJaartal = new SimpleIntegerProperty();
    private SimpleIntegerProperty isActief = new SimpleIntegerProperty();

    private SimpleIntegerProperty orderRegelID = new SimpleIntegerProperty();

    private String stringWijnType;
    private int totaalAantalDozen;


    // CONSTRUCTOR

    /** 
     * Bij het maken van een wijn object zijn er benodigde waardes nodig in de constructor. 
     */
    public Wijn(int wijnSerieID, String wijnNaam, double inkoopPrijs, double prijs, int wijnType,
                int wijnJaartal, int isActief, String wijnAfkomst, String wijnCategory) {
        this.wijnSerieID.set(wijnSerieID);
        this.wijnNaam.set(wijnNaam);
        this.inkoopPrijs.set(inkoopPrijs);
        this.prijs.set(prijs);
        this.wijnType.set(wijnType);
        this.wijnCategory.set(wijnCategory);
        this.wijnAfkomst.set(wijnAfkomst);
        this.wijnJaartal.set(wijnJaartal);
        this.isActief.set(isActief);
    }


    // wijnID

    public int getWijnID() {
        return wijnID.get();
    }

    public SimpleIntegerProperty wijnIDProperty() {
        return wijnID;
    }

    public void setWijnID(int wijnID) {
        this.wijnID.set(wijnID);
    }


    //wijnSerieID

    public int getWijnSerieID() {
        return wijnSerieID.get();
    }

    public SimpleIntegerProperty wijnSerieIDProperty() {
        return wijnSerieID;
    }

    public void setWijnSerieID(int wijnSerieID) {
        this.wijnSerieID.set(wijnSerieID);
    }


    // wijnNaam

    public String getWijnNaam() {
        return wijnNaam.get();
    }

    public SimpleStringProperty wijnNaamProperty() {
        return wijnNaam;
    }

    public void setWijnNaam(String wijnNaam) {
        this.wijnNaam.set(wijnNaam);
    }


    // inkoopprijs

    public double getInkoopPrijs() {
        return inkoopPrijs.get();
    }

    public SimpleDoubleProperty inkoopPrijsProperty() {
        return inkoopPrijs;
    }

    public void setInkoopPrijs(double inkoopPrijs) {
        this.inkoopPrijs.set(inkoopPrijs);
    }


    // prijs

    public double getPrijs() {
        return prijs.get();
    }

    public SimpleDoubleProperty prijsProperty() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs.set(prijs);
    }


    // wijnCategory

    public String getWijnCategory() {
        return wijnCategory.get();
    }

    public SimpleStringProperty wijnCategoryProperty() {
        return wijnCategory;
    }

    public void setWijnCategory(String wijnLand) {
        this.wijnCategory.set(wijnLand);
    }


    // wijnType

    public int getWijnType() {
        return wijnType.get();
    }

    public SimpleIntegerProperty wijnTypeProperty() {
        return wijnType;
    }

    public void setWijnType(int wijnType) {
        this.wijnType.set(wijnType);
    }


    // wijnAfkomst

    public String getWijnAfkomst() {
        return wijnAfkomst.get();
    }

    public SimpleStringProperty wijnAfkomstProperty() {
        return wijnAfkomst;
    }

    public void setWijnAfkomst(String wijnAfkomst) {
        this.wijnAfkomst.set(wijnAfkomst);
    }


    // wijnJaartal

    public int getWijnJaartal() {
        return wijnJaartal.get();
    }

    public SimpleIntegerProperty wijnJaartalProperty() {
        return wijnJaartal;
    }

    public void setWijnJaartal(int wijnJaartal) {
        this.wijnJaartal.set(wijnJaartal);
    }


    // isActief

    public int getIsActief() {
        return isActief.get();
    }

    public SimpleIntegerProperty isActiefProperty() {
        return isActief;
    }

    public void setIsActief(int isActief) {
        this.isActief.set(isActief);
    }


    // other

    public int getOrderRegelID() {
        return orderRegelID.get();
    }

    public SimpleIntegerProperty orderRegelIDProperty() {
        return orderRegelID;
    }

    public void setOrderRegelID(int orderRegelID) {
        this.orderRegelID.set(orderRegelID);
    }


    // Must be a better way to solve this

    public String getStringWijnType() {
        return this.stringWijnType;
    }

    public void setStringWijnType(String type) {
        this.stringWijnType = type;
    }


    public int getTotaalAantalDozen() {
        return totaalAantalDozen;
    }

    public void setTotaalAantalDozen(int totaalAantalDozen) {
        this.totaalAantalDozen = totaalAantalDozen;
    }
}
