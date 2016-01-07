/**
 *
 */
package IIIPSEN2.control;

import IIIPSEN2.knopHandlers.KlantAanmakenHandler;
import IIIPSEN2.knopHandlers.KlantOverzichtHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.view.HomeView;
import IIIPSEN2.view.KlantBeherenView;
import IIIPSEN2.view.KlantOverzichtView;

/**
 * @author Anton
 *         Keuzemenu voor gasten knop in homeview.
 *         Handelt de 3 keuzes af.
 */
public class KlantKeuzeController implements KlantAanmakenHandler, KlantOverzichtHandler, TerugKnopHandler {

    @Override
    public void klantAanmakenClicked() {
        // TODO Auto-generated method stub
        new KlantBeherenView(new KlantController());
    }

    @Override
    public void klantOverzichtClicked() {
        new KlantOverzichtView(new KlantController());
    }

    @Override
    public void terugKnopClicked() {
        // TODO Auto-generated method stub
        new HomeView();
    }
}
