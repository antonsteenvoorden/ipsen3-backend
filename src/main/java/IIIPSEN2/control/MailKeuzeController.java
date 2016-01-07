package IIIPSEN2.control;

import IIIPSEN2.knopHandlers.FacturenKnopHandler;
import IIIPSEN2.knopHandlers.HerinneringenKnopHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.knopHandlers.UitnodigingenHandler;
import IIIPSEN2.view.HomeView;

/**
 * @author Anton
 *         Keuzemenu voor Mailen knop in homeview.
 *         Handelt de 3 keuzes af.
 */
public class MailKeuzeController implements FacturenKnopHandler, UitnodigingenHandler, HerinneringenKnopHandler,
        TerugKnopHandler {

    OrderController oc;
    KlantController kc;
    FactuurController fc;

    public MailKeuzeController(OrderController oc, KlantController kc, FactuurController fc) {
        this.oc = oc;
        this.kc = kc;
        this.fc = fc;
    }

    @Override
    public void uitnodigingenClicked() {
        // TODO Auto-generated method stub
        new MailController(oc, kc, fc).uitnodigingenClicked();
    }

    @Override
    public void herineringenClicked() {
        // TODO Auto-generated method stub
        new MailController(oc, kc, fc).herineringenClicked();
    }

    @Override
    public void terugKnopClicked() {
        // TODO Auto-generated method stub
        new HomeView();
    }

    @Override
    public void facturenClicked() {
        new MailController(oc, kc, fc).facturenClicked();
    }

}
