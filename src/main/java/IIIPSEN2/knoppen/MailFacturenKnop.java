package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.Knop;
import IIIPSEN2.knopHandlers.FacturenKnopHandler;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class MailFacturenKnop implements Knop {
    private String knopTekst = "Facturen";
    private FacturenKnopHandler k;

    public MailFacturenKnop(FacturenKnopHandler k) {
        this.k = k;
    }

    public String getKnopTekst() {
        return this.knopTekst;
    }

    @Override
    public void onClick() {
        k.facturenClicked();
    }

}
