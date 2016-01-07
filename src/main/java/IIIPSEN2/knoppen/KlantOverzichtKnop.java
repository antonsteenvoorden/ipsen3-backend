package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.Knop;
import IIIPSEN2.knopHandlers.KlantOverzichtHandler;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class KlantOverzichtKnop implements Knop {
    private String knopTekst = "Gast Overzicht";
    private KlantOverzichtHandler k;

    public KlantOverzichtKnop(KlantOverzichtHandler k) {
        this.k = k;
    }

    public String getKnopTekst() {
        return this.knopTekst;
    }

    @Override
    public void onClick() {
        k.klantOverzichtClicked();
    }

}
