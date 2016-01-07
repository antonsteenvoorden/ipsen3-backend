package IIIPSEN2.control;

import IIIPSEN2.knopHandlers.BestellingOverzichtHandler;
import IIIPSEN2.knopHandlers.BestellingPlaatsenHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.view.BestellingOverzichtView;
import IIIPSEN2.view.BestellingPersoonView;
import IIIPSEN2.view.HomeView;

/**
 * @author Anton
 *         Keuzemenu voor bestelling knop in homeview.
 *         Handelt de 3 keuzes af.
 */
public class BestellingKeuzeController implements BestellingPlaatsenHandler, BestellingOverzichtHandler,
        TerugKnopHandler {

    @Override
    public void bestellingPlaatsenClicked() {
        new BestellingPersoonView(new KlantController());

    }

    @Override
    public void bestellingOverzichtClicked() {
        // TODO Auto-generated method stub
        new BestellingOverzichtView(new OrderController(), new KlantController());
    }

    @Override
    public void terugKnopClicked() {
        // TODO Auto-generated method stub
        new HomeView();
    }


}
