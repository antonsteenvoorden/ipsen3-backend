package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.Knop;
import IIIPSEN2.knopHandlers.WijnAanmakenHandler;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class WijnBeherenKnop implements Knop {
    private String knopTekst = "Wijnen toevoegen";
    private WijnAanmakenHandler k;

    public WijnBeherenKnop(WijnAanmakenHandler wijnBeherenHandler) {
        this.k = wijnBeherenHandler;
    }

    public String getKnopTekst() {
        return this.knopTekst;
    }

    @Override
    public void onClick() {
        k.wijnAanmakenClicked();
    }

}
