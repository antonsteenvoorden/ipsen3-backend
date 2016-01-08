package IIIPSEN2.control;

import IIIPSEN2.DAO.KlantDAO;
import IIIPSEN2.interfaces.KlantHandler;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import IIIPSEN2.model.ImportKlanten;
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;
import IIIPSEN2.view.KlantOverzichtView;

import java.util.ArrayList;

/**
 * @author Anton Steenvoorden, Roger Bosman
 *         <p>
 *         Klantcontroller is onderdeel van de laag tussen de klassen die informatie uit de database willen opvragen
 *         en de IIIPSEN2.DAO's.
 *         Klantcontroller richt zich op de klanten.
 */

public class KlantController implements KlantHandler {
    /**
     * KlantController bevat referenties naar KlantDAO (voor het doorspelen van de functies)
     * en ordercontroller voor het doorspelen van de order-specifieke functies
     */
    private KlantDAO klantDAO;
    private OrderController orderController;

    public KlantController() {
        this.klantDAO = new KlantDAO();
        this.orderController = new OrderController();
    }

    public KlantDAO getKlantDAO() {
        if (klantDAO == null) {
            klantDAO = new KlantDAO();
        }
        return klantDAO;
    }

    public ArrayList<Klant> getActieveKlanten() {
        return klantDAO.getActieveKlanten();
    }

    public ArrayList<Klant> getInactieveKlanten() {
        return klantDAO.getInactieveKlanten();
    }

    public ArrayList<Klant> getAlleKlanten() {
        return klantDAO.getAlleKlanten();
    }

    public Klant getKlantByEmail(String email) {
        return klantDAO.getKlantByEmail(email);
    }

    public Klant getKlantByOrder(Order order) {
        return klantDAO.getKlantByOrder(order);
    }

    public void addKlant(Klant newKlant) {
        klantDAO.addKlant(newKlant);
    }

    public void updateKlant(Klant updatedKlant) {
        klantDAO.updateKlant(updatedKlant);
    }

    /**
     * updateMultipleKlanten loopt door de gegeven arraylist heen aangezien KlantDAO.updateKlant telkens een enkele
     * klant verwacht
     *
     * @param klanten
     * @author Roger Bosman
     */
    public void updateMultipleKlanten(ArrayList<Klant> klanten) {
        for (Klant klant : klanten) {
            klantDAO.updateKlant(klant);
        }
    }

    /**
     * updateMultipleKlanten loopt door de gegeven arraylist heen aangezien KlantDAO.updateKlant telkens een enkele
     * klant verwacht
     *
     * @param klanten
     * @author Roger Bosman
     */
    public void deActivateKlanten(ObservableList<Klant> klanten) {
        for (Klant klant : klanten) {
            klant.setIsActief(0);
            klantDAO.updateKlant(klant);
        }
    }

    /**
     * updateMultipleKlanten loopt door de gegeven arraylist heen aangezien KlantDAO.updateKlant telkens een enkele
     * klant verwacht
     * ook word in deze loop de klant.setIsActief op 1 gezet.
     *
     * @param klanten
     * @author Roger Bosman
     */
    public void activateKlanten(ObservableList<Klant> klanten) {
        for (Klant klant : klanten) {
            klant.setIsActief(1);
            klantDAO.updateKlant(klant);
        }
    }

    @Override
    public ArrayList<Order> getOrderByKlant(Klant klant) {
        return orderController.getOrderByKlant(klant);
    }

    /**
     * Geeft klanten terug die overeenkomen met de string.
     * Zoekfunctie bij het bestellen.
     *
     * @param s
     * @return
     */
    public ArrayList<Klant> getKlantByString(String s) {
        return klantDAO.getKlantByString(s);
    }

    /**
     * getKlantenbyorders loopt door de gegeven arraylist van orders heen.
     * binnen deze array word er door de eigen arraylist van emails geloopt.
     * in deze inner loop word elk email adres behorende bij een order welke nog niet in de arraylist zit toegevoegd
     * aan de array.
     * vervolgens word voor ieder email adres in de array de bijbehorende klant opgehaald
     *
     * @param orders waarop gezocht moet worden
     * @return klanten behorende bij de gegeven lijst orders
     */
    public ArrayList<Klant> getKlantenByOrders(ArrayList<Order> orders) {
        ArrayList<String> emails = new ArrayList<>();

        boolean inArray;
        for (int i = 0; i < orders.size(); i++) {
            inArray = false;
            for (int j = 0; j < emails.size(); j++) {
                if (orders.get(i).getKlantEmail().equals(emails.get(j))) {
                    inArray = true;
                }
            }
            if (!inArray) {
                emails.add(orders.get(i).getKlantEmail());
            }
        }

        ArrayList<Klant> klanten = new ArrayList<>();

        for (String email : emails) {
            klanten.add(klantDAO.getKlantByEmail(email));
        }

        return klanten;
    }


    /**
     * @author Sidney de Geus
     */
    public void importKlanten() {
        ImportKlanten importKlanten = new ImportKlanten();
        ArrayList<Klant> klanten = importKlanten.openDialog();
        int[] results = new int[3];
        results[0] = 0; // adds
        results[1] = 0; // updates
        results[2] = 0; // ignores

        for (Klant k : klanten) {
            int[] result = new int[3];
            try {
                result = klantDAO.importNotAddKlant(k);
                results[0] += result[0];
                results[1] += result[1];
                results[2] += result[2];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (klanten.size() != 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Resultaat");
            alert.setHeaderText("Resultaat van het importeren van de gasten lijst");
            alert.setContentText(
                    "Totaal aantal gasten ingelezen: " + klanten.size() + "\n\n"
                            + "Aantal toegevoegde gasten: " + results[0] + "\n"
                            + "Aantal inactief naar actief gestelde gasten: " + results[1] + "\n"
                            + "Aantal gasten dat al in de lijst stonden: " + results[2]);
            alert.showAndWait();
            new KlantOverzichtView(this);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Importeren mislukt");
            alert.setHeaderText("Er zijn geen gasten geïmporteerd");
            alert.setContentText(
                    "Het kan zijn dat het formaat van het bestand fout is \n"
                            + "of dat er geen wijnen in het bestand staan."
            );
        }
    }

    public void enableLogging() {
        klantDAO.enableLogging();
    }

}
