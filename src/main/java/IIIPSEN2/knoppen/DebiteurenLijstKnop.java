package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.Knop;
import IIIPSEN2.knopHandlers.DebiteurenKnopHandler;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class DebiteurenLijstKnop implements Knop {
    private String knopTekst = "Debiteuren lijst";
    private DebiteurenKnopHandler k;

    public DebiteurenLijstKnop(DebiteurenKnopHandler k) {
        this.k = k;
    }

    public String getKnopTekst() {
        return this.knopTekst;
    }

    @Override
    public void onClick() {
        k.debiteurenClicked();
    }
}
