package IIIPSEN2.control;

import IIIPSEN2.DAO.OrderDAO;
import IIIPSEN2.DAO.OrderRegelDAO;
import IIIPSEN2.interfaces.OrderHandler;
import javafx.collections.ObservableList;
import IIIPSEN2.model.Factuur;
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;
import IIIPSEN2.model.OrderRegel;

import java.util.ArrayList;

/**
 * @author Roger Bosman, Jordan Munk
 *         <p>
 *         OrderController is onderdeel van de laag tussen de klassen die informatie uit de database willen opvragen
 *         en de IIIPSEN2.DAO's.
 *         OrderController richt zich op de orders en orderegels (deze gaan altijd samen dus zijn gecombineerd in een
 *         controll classe).
 */


public class OrderController implements OrderHandler {
    /**
     * OrderController bevat referenties naar OrderDAO (voor het doorspelen van de orderfuncties),
     * OrderRegelDAO (voor het doorspelen van de orderregelfuncties)
     * en WijnController voor het doorspelen van de wijn-specifieke functies
     */
    private OrderDAO orderDAO;
    private OrderRegelDAO orderRegelDAO;
    private WijnController wijnController;

    public OrderController() {
        this.orderDAO = new OrderDAO();
        this.orderRegelDAO = new OrderRegelDAO();
        this.wijnController = new WijnController();
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public OrderRegelDAO getOrderRegelDAO() {
        return orderRegelDAO;
    }

    public void setOrderRegelDAO(OrderRegelDAO orderRegelDAO) {
        this.orderRegelDAO = orderRegelDAO;
    }

    public WijnController getWijnController() {
        return wijnController;
    }

    public void setWijnController(WijnController wijnController) {
        this.wijnController = wijnController;
    }

    public ArrayList<Order> getOrderByKlant(Klant klant) {
        return orderDAO.getOrderByKlant(klant);
    }

    public ArrayList<Order> getAlleActieveOrders() {
        return orderDAO.getActieveOrders();
    }

    public ArrayList<Order> getInactieveOrders() {
        return orderDAO.getInactieveOrders();
    }

    public ArrayList<Order> getAlleOrders() {
        return orderDAO.getAlleOrders();
    }

    public Order addOrder(Order order) {
        return orderDAO.addOrder(order);
    }

    /**
     * updateMultipleOrders loopt door de gegeven arraylist heen aangezien orderDAO.updateOrder telkens een enkele
     * orders verwacht
     *
     * @param orders
     * @author Roger Bosman
     */
    public void updateMultipleOrders(ArrayList<Order> orders) {
        for (Order updatedOrder : orders) {
            orderDAO.updateOrder(updatedOrder);
        }
    }

    /**
     * deactivateOrders loopt door de gegeven arraylist heen aangezien orderDAO.updateOrder telkens een enkele orders
     * verwacht
     * ook word in deze loop de order.setIsActief op 0 gezet.
     *
     * @param orders
     * @author Roger Bosman
     */
    public void deactivateOrders(ObservableList<Order> orders) {
        for (Order order : orders) {
            order.setIsActief(0);
            orderDAO.updateOrder(order);
        }
    }

    /**
     * deactivateOrders loopt door de gegeven arraylist heen aangezien orderDAO.updateOrder telkens een enkele orders
     * verwacht
     * ook word in deze loop de order.setIsActief op 1 gezet.
     *
     * @param orders
     * @author Roger Bosman
     */
    public void activateOrders(ObservableList<Order> orders) {
        for (Order order : orders) {
            order.setIsActief(1);
            orderDAO.updateOrder(order);
        }
    }

    /**
     * getOrdersByFacturen loopt door de gegeven arraylist van facturen heen.
     * binnen deze array word er door de eigen arraylist van orderIDs geloopt.
     * in deze inner loop word elk OrderID behorende bij een order welke nog niet in de arraylist zit toegevoegd aan
     * de array.
     * vervolgens word voor ieder OrderID in de array de bijbehorende Order opgehaald
     *
     * @param facturen waarop gezocht moet worden
     * @return Orders behorende bij de gegeven lijst facturen
     */
    public ArrayList<Order> getOrdersByFacturen(ArrayList<Factuur> facturen) {
        ArrayList<Integer> orderIDs = new ArrayList<>();

        boolean inArray;
        for (int i = 0; i < facturen.size(); i++) {
            inArray = true;
            for (int j = 0; j < orderIDs.size(); j++) {
                if (facturen.get(i).getOrderID() == orderIDs.get(j)) {
                    inArray = false;
                }
            }
            if (inArray) {
                orderIDs.add(facturen.get(i).getOrderID());
            }
        }

        ArrayList<Order> orders = new ArrayList<>();

        for (Integer orderID : orderIDs) {
            orders.add(orderDAO.getOrderByID(orderID).get(0));
        }
        return orders;
    }

    //Orderregel functies

    public ArrayList<OrderRegel> getActieveOrderRegels() {
        return orderRegelDAO.getActieveOrderRegels();
    }

    public ArrayList<OrderRegel> getInactieveOrderRegels() {
        return orderRegelDAO.getInactieveOrderRegels();
    }

    public ArrayList<OrderRegel> getAlleOrderRegels(int orderID) {
        return orderRegelDAO.getAlleOrderRegels(orderID);
    }

    public OrderRegel addOrderRegel(OrderRegel newOrderRegel) {
        return orderRegelDAO.addOrderRegel(newOrderRegel);
    }

    public void updateOrderRegel(OrderRegel updatedOrderRegel) {
        orderRegelDAO.updateOrderRegel(updatedOrderRegel);
    }

    /**
     * deActivateOrderRegels loopt door de gegeven arraylist heen aangezien orderRegelDAO.updateOrderRegel telkens
     * een enkele OrderRegel verwacht
     *
     * @param orderRegels
     * @author Roger Bosman
     */
    public void updateMultipleOrderRegels(ArrayList<OrderRegel> orderRegels) {
        for (OrderRegel orderRegel : orderRegels) {
            orderRegelDAO.updateOrderRegel(orderRegel);
        }
    }

    /**
     * deActivateOrderRegels loopt door de gegeven arraylist heen aangezien orderRegelDAO.updateOrderRegel telkens
     * een enkele OrderRegel verwacht
     * ook word in deze loop de orderRegel.setIsActief op 0 gezet.
     *
     * @param orderRegels
     * @author Roger Bosman
     */
    public void deActivateOrderRegels(ObservableList<OrderRegel> orderRegels) {
        for (OrderRegel orderRegel : orderRegels) {
            orderRegel.setIsActief(0);
            orderRegelDAO.updateOrderRegel(orderRegel);
        }
    }

    /**
     * deActivateOrderRegels loopt door de gegeven arraylist heen aangezien orderRegelDAO.updateOrderRegel telkens
     * een enkele OrderRegel verwacht
     * ook word in deze loop de orderRegel.setIsActief op 1 gezet.
     *
     * @param orderRegels
     * @author Roger Bosman
     */
    public void activateOrderRegels(ObservableList<OrderRegel> orderRegels) {
        for (OrderRegel orderRegel : orderRegels) {
            orderRegel.setIsActief(1);
            orderRegelDAO.updateOrderRegel(orderRegel);
        }
    }

    @Override
    public ArrayList<OrderRegel> getOrderRegelByOrder(Order order) {
        return orderRegelDAO.getOrderRegelByOrder(order);
    }

    /**
     * @param orderRegelID
     */
    public void removeOrderRegel(int orderRegelID) {
        orderRegelDAO.removeOrderRegel(orderRegelID);
    }
}


