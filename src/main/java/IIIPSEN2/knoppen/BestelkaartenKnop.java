package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.BestelkaartenHandler;
import IIIPSEN2.interfaces.Knop;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class BestelkaartenKnop implements Knop {
    private String knopTekst = "Bestellijsten";
    private BestelkaartenHandler k;

    public BestelkaartenKnop(BestelkaartenHandler k) {
        this.k = k;
    }

    public String getKnopTekst() {
        return this.knopTekst;
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        k.bestelKaartenClicked();
    }


}
