package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.Knop;
import IIIPSEN2.knopHandlers.WijnOverzichtHandler;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class WijnOverzichtKnop implements Knop {
    private String knopTekst = "Wijn overzicht";
    private WijnOverzichtHandler k;

    public WijnOverzichtKnop(WijnOverzichtHandler wijnOverzichtHandler) {
        this.k = wijnOverzichtHandler;
    }

    public String getKnopTekst() {
        return this.knopTekst;
    }

    @Override
    public void onClick() {
        k.wijnOverzichtClicked();
    }

}
