package service;

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

  public OrderService(OrderDAO orderDAO, OrderRegelService orderRegelService) {
    this.orderDAO = orderDAO;
    this.orderRegelService = orderRegelService;
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

  public Order add(Order order) {
    int newOrderID = orderDAO.add(order);
    order.setOrderID(newOrderID);
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
