/**
 *
 */
package IIIPSEN2.control;

import IIIPSEN2.DAO.WijnDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import IIIPSEN2.model.ImportWijnen;
import IIIPSEN2.model.OrderRegel;
import IIIPSEN2.model.Wijn;
import IIIPSEN2.view.WijnBeherenView;
import IIIPSEN2.view.WijnOverzichtView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Roger, Anton Steenvoorden, Sidney de Geus
 *         <p>
 *         De WijnController class stuurt alle verzoeken met de WijnDAO aan.
 *         Zodra een IIIPSEN2.view gegevens uit de database nodig heeft, wilt toevoegen
 *         of wilt wijzigen, dan wordt dit via de WijnController aangeroepen.
 */
public class WijnController {


    private WijnDAO wijnDAO;


    // CONSTRUCTOR

    /**
     * WijnController constructor maakt automatisch een WijnDAO object. 
     */
    public WijnController() {
        wijnDAO = new WijnDAO();
    }


    // SELECTS

    public ArrayList<Wijn> getAlleWijnen() {
        return wijnDAO.getAllWijnen();
    }

    public ArrayList<Wijn> getActieveWijnen() {
        return wijnDAO.getActiveWijnen();
    }

    public ArrayList<Wijn> getInactieveWijnen() {
        return wijnDAO.getInactiveWijnen();
    }

    public ArrayList<Wijn> getWijnBestellijst() {
        return wijnDAO.getWijnBestellijst();
    }

    public ArrayList<Wijn> getAlleOrders() {
        return wijnDAO.getAllWijnen();
    }

    public ArrayList<String> getCategories() {
        return wijnDAO.getCategories();
    }

    public String[] getTypes() {
        return wijnDAO.getTypes();
    }

    public int getLastSerieID() {
        return wijnDAO.getLastWijnSerieID();
    }

    public Wijn getWijnByOrderRegel(OrderRegel orderRegel) {
        return wijnDAO.getWijnByOrderRegel(orderRegel);
    }


    // INSERTS

    /**
     * addWijn() stuurt data dat uit de IIIPSEN2.view komt door naar de WijnDAO
     * om een wijn toe te voegen..
     */
    public void addWijn(Wijn wijn) {
        try {
            new WijnBeherenView(this);
            wijnDAO.addWijn(wijn);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is iets misgegaan");
            alert.setContentText("Let goed op dat de prijs en het jaartal getallen zijn !");
            alert.showAndWait();
        }
    }


    // UPDATES

    /**
     * updateWijn() stuurt data dat uit de IIIPSEN2.view komt door naar de WijnDAO
     * om een wijn te updaten.
     */
    public void updateWijn(Wijn wijn) {
        try {
            wijnDAO.updateWijn(wijn);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is iets misgegaan");
            alert.setContentText("Let goed op dat de prijs en het  jaartal getallen zijn !");
            alert.showAndWait();
        }
    }

    public void updateMultipleWijnen(ArrayList<Wijn> wijnen) {
        for (Wijn wijn : wijnen) {
            wijnDAO.updateWijn(wijn);
        }
    }

    /**
     * deActivateWijnen(...) zorgt voor het inactief stellen van wijnen
     */
    public void deActivateWijnen(ObservableList<Wijn> wijnen) {
        for (Wijn wijn : wijnen) {
            wijn.setIsActief(0);
            wijnDAO.setStatus(wijn);
        }
    }

    /**
     * activateWijnen(...) zorgt voor het actief stellen van wijnen
     */
    public void activateWijnen(ObservableList<Wijn> wijnen) {
        for (Wijn wijn : wijnen) {
            wijn.setIsActief(1);
            wijnDAO.setStatus(wijn);
        }
    }


    // OTHER

    /**
     * importWijnen() roept ImportWijnen() op en krijgt data er uit terug.
     * Vervolgens wordt deze data gebruikt om ze door te sturen naar de database
     * om toe te voegen, updaten en/of niks mee te doen.
     * Uiteindelijk wordt het proces in een alert box weergeven.
     */
    public void importWijnen() {
        ImportWijnen importWijnen = new ImportWijnen();
        ArrayList<Wijn> wijnen = importWijnen.openDialog();
        int[] results = new int[3];
        results[0] = 0; // adds
        results[1] = 0; // updates
        results[2] = 0; // ignores

        for (Wijn w : wijnen) {
            int[] result = new int[3];
            try {
                result = wijnDAO.addWijn(w);
                results[0] += result[0];
                results[1] += result[1];
                results[2] += result[2];
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (wijnen.size() != 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Resultaat");
            alert.setHeaderText("Resultaat van het importeren van de wijn lijst");
            alert.setContentText(
                    "Totaal aantal wijnen ingelezen: " + wijnen.size() + "\n\n"
                            + "Aantal toegevoegde wijnen: " + results[0] + "\n"
                            + "Aantal inactief naar actief gestelde wijnen: " + results[1] + "\n"
                            + "Aantal wijnen dat al in de lijst stonden: " + results[2]);
            alert.showAndWait();
            new WijnOverzichtView(this);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Importeren mislukt");
            alert.setHeaderText("Er zijn geen wijnen geïmporteerd");
            alert.setContentText(
                    "Het kan zijn dat het formaat van het bestand fout is \n"
                            + "of dat er geen wijnen in het bestand staan."
            );
        }
    }

    /**
     * wijnTypeToInt() zet de wijnType string om naar een int voor index reference.
     */
    public int wijnTypeToInt(String typeName) {
        int typeID = 0;
        String[] types = getTypes();
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(typeName)) {
                typeID = i;
                break;
            }
        }
        return typeID;
    }
}
