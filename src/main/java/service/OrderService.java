package service;

import dao.OrderDAO;
import model.Actie;
import model.Klant;
import model.Order;

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

  public Set<Order> retrieveEmptyOrders() {
    return orderDAO.retrieveAll();
  }

  public Set<Order> retrieveOrdersWithOrderRegels() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      orderRegelService.retrieveEmptyOrderRegels(order.getOrderID());
    }
    return orders;
  }

  public Set<Order> retrieveOrdersWithOrderRegelsWithWijn() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      orderRegelService.retrieveOrderRegelsWithWijn(order.getOrderID());
    }
    return orders;
  }

  public Order retrieveEmptyOrder(int id) {
    return requireResult(orderDAO.retrieve(id));
  }

  public Order retrieveOrderWithOrderRegels(int id) {
    Order order = this.retrieveEmptyOrder(id);
    order.setOrderRegelSet(orderRegelService.retrieveEmptyOrderRegels(order.getOrderID()));
    return order;
  }

  public Order retrieveOrderWithOrderRegelsWithWijn(int id) {
    Order order = this.retrieveEmptyOrder(id);
    order.setOrderRegelSet(orderRegelService.retrieveOrderRegelsWithWijn(order.getOrderID()));
    return order;
  }

  public Order add(int id, Order order, Klant authenticator, Klant klant) {
    if(!actieService.checkIngeschreven(id, authenticator)) {
      if (!authenticator.hasRole("ADMIN")) {
        assertSelf(authenticator, klant);
      }
      int newOrderID = orderDAO.add(order);
      order.setOrderID(newOrderID);
    } else {
      //niet ingeschreven error
    }
    return order;
  }

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
}
