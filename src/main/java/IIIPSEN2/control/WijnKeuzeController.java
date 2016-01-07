package IIIPSEN2.control;

import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.knopHandlers.WijnAanmakenHandler;
import IIIPSEN2.knopHandlers.WijnOverzichtHandler;
import IIIPSEN2.view.HomeView;
import IIIPSEN2.view.WijnBeherenView;
import IIIPSEN2.view.WijnOverzichtView;

/**
 * @author Anton, Sidney
 *         Keuzemenu voor wijn knop in homeview.
 *         Handelt de 3 keuzes af.
 */
public class WijnKeuzeController implements WijnAanmakenHandler, WijnOverzichtHandler, TerugKnopHandler {
    @Override
    public void wijnAanmakenClicked() {
        new WijnBeherenView(new WijnController());
    }

    @Override
    public void wijnOverzichtClicked() {
        new WijnOverzichtView(new WijnController());
    }

    @Override
    public void terugKnopClicked() {
        // TODO Auto-generated method stub
        new HomeView();
    }

}
