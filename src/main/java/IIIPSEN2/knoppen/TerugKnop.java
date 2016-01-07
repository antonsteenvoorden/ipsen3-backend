package IIIPSEN2.knoppen;

import IIIPSEN2.interfaces.Knop;
import IIIPSEN2.knopHandlers.TerugKnopHandler;

/**
 * Zelf uitleggende klasse. Is een knop met een string knopTekst
 * die de tekst bevat dat als label gebruikt wordt.
 * Roept de methode aan op de bijhorende handler
 *
 * @author Anton Steenvoorden, Roger Bosman
 */
public class TerugKnop implements Knop {
    private String knopTekst = "Terug";
    private TerugKnopHandler k;

    public TerugKnop(TerugKnopHandler k) {
        this.k = k;
    }

    @Override
    public void onClick() {
        k.terugKnopClicked();
    }

    @Override
    public String getKnopTekst() {
        return knopTekst;
    }

}
