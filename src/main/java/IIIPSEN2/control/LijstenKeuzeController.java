package IIIPSEN2.control;

import IIIPSEN2.interfaces.BestelkaartenHandler;
import IIIPSEN2.knopHandlers.DebiteurenKnopHandler;
import IIIPSEN2.knopHandlers.FacturenKnopHandler;
import IIIPSEN2.knopHandlers.GroothandelBestellingHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.view.BestelkaartView;
import IIIPSEN2.view.HomeView;

/**
 * @author Jordan Munk,
 *         Anton Steenvoorden,
 *         Sidney de Geus
 *         Handelt de keuzes af van de knop Lijsten in homeview.
 */

public class LijstenKeuzeController implements FacturenKnopHandler, BestelkaartenHandler,
        GroothandelBestellingHandler, DebiteurenKnopHandler, TerugKnopHandler {

    @Override
    public void groothandelBestellingClicked() {
        new LijstController().groothandelBestelling();
    }

    @Override
    public void bestelKaartenClicked() {
        new BestelkaartView(new KlantController());

    }

    @Override
    public void facturenClicked() {
        // TODO Auto-generated method stub
        System.out.println("Facturen");
    }

    @Override
    public void debiteurenClicked() {
        new LijstController().debiteurenExporteren();
    }

    @Override
    public void terugKnopClicked() {
        new HomeView();
    }

}
