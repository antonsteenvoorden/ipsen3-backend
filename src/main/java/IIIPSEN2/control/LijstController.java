package IIIPSEN2.control;

import IIIPSEN2.DAO.KlantDAO;
import IIIPSEN2.DAO.WijnDAO;
import IIIPSEN2.model.ExportDebiteuren;
import IIIPSEN2.model.GrootHandelBestelling;

/**
 * @author Sidney de Geus
 *         <p>
 *         LijstController hanteerd groothandelBestelling en debiteurenExporteren
 */

public class LijstController {

    public void groothandelBestelling() {
        WijnDAO wijnDAO = new WijnDAO();
        new GrootHandelBestelling(wijnDAO.getGroothandelBestellingWijnen());
    }

    public void debiteurenExporteren() {
        KlantDAO klantDAO = new KlantDAO();
        new ExportDebiteuren(klantDAO.getDebiteuren());
    }
}
