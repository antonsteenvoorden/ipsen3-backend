/**
 *
 */
package IIIPSEN2.control;

import IIIPSEN2.DAO.FactuurDAO;
import IIIPSEN2.DAO.KlantDAO;
import IIIPSEN2.DAO.OrderRegelDAO;
import javafx.collections.ObservableList;
import IIIPSEN2.model.FactuurGenerator;
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;
import IIIPSEN2.model.OrderRegel;
import IIIPSEN2.view.BestellingBeherenView;

import java.util.ArrayList;

/**
 * @author Anton S
 *         Handelt de acties uit de bestellingbeherenview af
 */
public class BestellingController {
    OrderController orderController;
    KlantDAO klantDAO;
    OrderRegelDAO orderRegelDAO;
    ObservableList<OrderRegel> orderRegels;

    /**
     * set dao's en een orderController
     */
    public BestellingController() {
        orderController = new OrderController();
        klantDAO = new KlantDAO();
        orderRegelDAO = new OrderRegelDAO();
    }

    /**
     * Roept de orderController aan
     */
    public void addOrderRegel(OrderRegel orderRegel) {
        orderController.addOrderRegel(orderRegel);
    }

    /**
     * Roept de orderController aan
     */
    public void updateOrder(ObservableList<OrderRegel> orderRegels) {
        int orderID = orderRegels.get(0).getOrderID();
        for (OrderRegel o : orderRegels) {
            orderController.updateOrderRegel(o);
        }
        FactuurDAO f = new FactuurDAO();
        f.updateIsVerzondenByOrderID(false, orderID);
    }

    /**
     * Roept de orderController aan
     */
    public void removeOrder(ArrayList<OrderRegel> orderRegels) {
        for (OrderRegel o : orderRegels) {
            orderController.removeOrderRegel(o.getOrderRegelID());
        }
    }


    /**
     * Roept de orderController aan
     *
     * @param order
     * @param orderRegels
     */
    public void addOrder(Order order, ObservableList<OrderRegel> orderRegels) {
        Order tempOrder = orderController.addOrder(order);

        for (OrderRegel o : orderRegels) {
            o.setOrderID(tempOrder.getOrderID());
            orderController.addOrderRegel(o);
        }
        Klant klant = klantDAO.getKlantByEmail(tempOrder.getKlantEmail());
        @SuppressWarnings("deprecation")
        FactuurGenerator factuurGenerator = new FactuurGenerator(
                klant, tempOrder, orderRegels);
        factuurGenerator.factuurDB();
    }

    /**
     * maakt nieuwe bestellingbeherenview aan met de geselecteerde bestelling
     *
     * @param order
     */
    public void editView(Order order) {
        new BestellingBeherenView(
                this,
                order,
                new KlantDAO().getKlantByEmail(order.getKlantEmail())
        );
    }

    /**
     * @param klant
     * @param orderRegels
     */
    public void createOrder(Klant klant, ObservableList<OrderRegel> orderRegels) {
        String factuurAdres =
                klant.getStraatnaam() + " " + klant.getHuisNummer()
                        + " " + klant.getPostcode()
                        + klant.getPostcodeToevoeging();

        Order order = new Order(
                klant.getEmail(),
                factuurAdres,
                1);
        addOrder(order, orderRegels);
    }

    public void opslaan(ObservableList<OrderRegel> orderRegels, ArrayList<OrderRegel> tmpRemoveOrderRegels) {
        this.orderRegels = orderRegels;
        removeOldOrders(tmpRemoveOrderRegels);
        addNewOrders();
        updateOrder();
    }

    /**
     * Voegt de orders uit de temp lijst die nog niet een orderID hebben toe aan de database.
     */
    private void addNewOrders() {
        for (OrderRegel o : orderRegels) {
            if (o.getOrderRegelID() == 0) {
                addOrderRegel(o);
            }
        }
    }

    /**
     * Verwijdert bestellingen die in de templist staan die al een ID hebben uit de database.
     */
    private void removeOldOrders(ArrayList<OrderRegel> tmpRemoveOrderRegels) {
        for (OrderRegel o : tmpRemoveOrderRegels) {
            if (o.getOrderRegelID() == 0) {
                tmpRemoveOrderRegels.remove(o);
            }
        }
        removeOrder(tmpRemoveOrderRegels);
    }

    /**
     * Verwijdert de orderRegels die nog niet zijn toegevoegd uit de array van orderregels
     * die moeten worden geupdate.
     */
    private void updateOrder() {
        for (OrderRegel o : orderRegels) {
            if (o.getOrderRegelID() == 0) {
                orderRegels.remove(o);
            }
        }
        updateOrder(orderRegels);
    }
}

