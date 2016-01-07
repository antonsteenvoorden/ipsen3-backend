package IIIPSEN2.control;

import IIIPSEN2.DAO.FactuurDAO;
import IIIPSEN2.model.Factuur;
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;

import java.util.ArrayList;

/**
 * Created by Roger Bosman on 9-10-2015.
 */
public class FactuurController {
    private FactuurDAO factuurDAO;
    private OrderController orderController;
    private KlantController klantController;

    public FactuurController() {
        this.factuurDAO = new FactuurDAO();
        this.orderController = new OrderController();
        this.klantController = new KlantController();
    }

    public ArrayList<Factuur> getFacturenByIsVerzonden(boolean isVerzonden) {
        return factuurDAO.getFacturenByIsVerzonden(isVerzonden);
    }

    public void insertFactuur(Factuur factuur) {
        factuurDAO.insertFactuur(factuur);
    }

    public ArrayList<Factuur> getFactuurByOrder(Order order) {
        return factuurDAO.getFactuurByOrder(order);
    }

    public ArrayList<Klant> getKlantenByVerzendStatus(boolean isVerzonden) {
        return getKlantController().getKlantenByOrders(getOrderController().getOrdersByFacturen(factuurDAO
                .getFacturenByIsVerzonden(isVerzonden)));
    }

    public OrderController getOrderController() {
        if (orderController == null) {
            orderController = new OrderController();
        }
        return orderController;
    }

    public KlantController getKlantController() {
        if (klantController == null) {
            klantController = new KlantController();
        }
        return klantController;
    }

    public ArrayList<Factuur> getActieveFaceturenByIsVerzonden(boolean isVerzonden) {
        return factuurDAO.getActieveFaceturenByIsVerzonden(isVerzonden);
    }

    public void updateIsVerzondenByOrder(Order order, boolean isVerzonden) {
        factuurDAO.updateIsVerzondenByOrderID(isVerzonden, order.getOrderID());
    }
}
