package service;

import dao.OrderDAO;
import model.Order;
import model.OrderRegel;

import java.util.ArrayList;
import java.util.Set;

/**
 * Edited by:
 * - Anton
 * - Roger
 */
public class OrderService extends BaseService<Order> {
  private final OrderDAO orderDAO;
  private final OrderRegelService orderRegelService;
  private final ActieService actieService;

  public OrderService(OrderDAO orderDAO, OrderRegelService orderRegelService, ActieService actieService) {
    this.orderDAO = orderDAO;
    this.orderRegelService = orderRegelService;
    this.actieService = actieService;
  }

  /**
   * Haalt alle orders op. De opgehaalde orders zijn leeg (bevatten geen orderregels)
   * @return
   */
  public Set<Order> retrieveEmptyOrders() {
    return orderDAO.retrieveAll();
  }

  /**
   * Haalt alle orders op. De opgehaalde orders zijn gevuld (bevatten orderregels)
   * De orderregels die in de orders worden gestopt zijn leeg (bevatten geen wijn)
   * @return
   */
  public Set<Order> retrieveOrdersWithOrderRegels() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      order.setOrderRegelSet(orderRegelService.retrieveEmptyOrderRegels(order.getOrderID()));
    }
    return orders;
  }

  /**
   * Haalt alle orders op. De opgehaalde orders zijn gevuld (bevatten orderregels)
   * De orderregels die in de orders worden gestopt zijn gevuld (bevatten een wijn)
   * @return
   */
  public Set<Order> retrieveOrdersWithOrderRegelsWithWijn() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      order.setOrderRegelSet(orderRegelService.retrieveOrderRegelsWithWijn(order.getOrderID()));
    }
    return orders;
  }

  /**
   * Haalt een specifieke order op op basis van een orderID.
   * De opgehaalde order is leeg (bevat geen orderregels)
   * @param id
   * @return
   */
  public Order retrieveEmptyOrder(int id) {
    return requireResult(orderDAO.retrieve(id));
  }

  /**
   * Haalt een specifieke order op op basis van een orderID.
   * De opgehaalde order is gevuld (bevat orderregels)
   * De orderregels die in de orders worden gestopt zijn leeg (bevatten geen wijn)
   * @param id
   * @return
   */
  public Order retrieveOrderWithOrderRegels(int id) {
    Order order = this.retrieveEmptyOrder(id);
    order.setOrderRegelSet(orderRegelService.retrieveEmptyOrderRegels(order.getOrderID()));
    return order;
  }

  /**
   * Haalt een specifieke order op op basis van een orderID.
   * De opgehaalde order is gevuld (bevat orderregels)
   * De orderregels die in de orders worden gestopt zijn gevuld (bevatten een wijn)
   * @param id
   * @return
   */
  public Order retrieveOrderWithOrderRegelsWithWijn(int id) {
    Order order = this.retrieveEmptyOrder(id);
    order.setOrderRegelSet(orderRegelService.retrieveOrderRegelsWithWijn(order.getOrderID()));
    return order;
  }

  /**
   * voegt een order toe en returned de gegenereerde OrderID key.
   * Als een order gevuld is met orderregels worden deze ook aangemaakt door de desbetreffende functie in orderRegelService aan te roepen
   * @see OrderRegelService
   * @param order
   * @return
   */
  public Order add(Order order) {
    int newOrderID = orderDAO.add(order);
    order.setOrderID(newOrderID);
    if (order.getOrderRegelSet() != null) {
      for(OrderRegel orderRegel : order.getOrderRegelSet()) {
        orderRegel.setOrderID(newOrderID);
        orderRegel.setOrderRegelID(orderRegelService.addOrderRegel(orderRegel));
      }
    }
    return order;
  }

  /**
   * updated een order.
   * @param newOrder
   */
  public void update(Order newOrder) {
    Order existingOrder = retrieveOrderWithOrderRegels(newOrder.getOrderID());
    if (newOrder.getKlantEmail() != null) {
      existingOrder.setKlantEmail(newOrder.getKlantEmail());
    }
    if (newOrder.getFactuurAdres() != null) {
      existingOrder.setFactuurAdres(newOrder.getFactuurAdres());
    }
    if (newOrder.getIsActief() != -1) {
      existingOrder.setIsActief(newOrder.getIsActief());
    }
    orderDAO.update(existingOrder);
  }

  /**
   * Haalt een order op op basis van een klantenemail.
   * Als wijnFill true is worden de orderregels gevuld met orderregels welke met wijn gevuld worden.
   * Als wijnFill false is maar orderFill true worden de orders gevuld met orderRegels welke leeg zijn (bevatten geen wijn)
   * Als zowel wijnFill als orderFill false zijn worden lege orders (bevatten geen orderregels) gereturned.
   * @param email
   * @param orderFill
   * @param wijnFill
   * @return
   */
  public ArrayList<Order> getOrdersByKlantEmail(String email, boolean orderFill, boolean wijnFill) {
    Set<Integer> orderIDs = orderDAO.retrieveOrderIDs(email);
    ArrayList<Order> orders = new ArrayList<>();
    for (Integer id : orderIDs) {
      if (wijnFill) {
        orders.add(this.retrieveOrderWithOrderRegelsWithWijn(id));
      } else if (orderFill) {
        orders.add(this.retrieveOrderWithOrderRegels(id));
      } else {
        orders.add(this.retrieveEmptyOrder(id));
      }
    }
    return orders;
  }
}
