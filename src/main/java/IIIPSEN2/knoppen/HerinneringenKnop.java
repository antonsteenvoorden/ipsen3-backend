package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.Knop;
import IIIPSEN2.knopHandlers.HerinneringenKnopHandler;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class HerinneringenKnop implements Knop {
    private String knopTekst = "Herinneringen";
    private HerinneringenKnopHandler k;

    public HerinneringenKnop(HerinneringenKnopHandler k) {
        this.k = k;
    }

    public String getKnopTekst() {
        return this.knopTekst;
    }

    @Override
    public void onClick() {
        // TODO Auto-generated method stub
        k.herineringenClicked();
    }

}
