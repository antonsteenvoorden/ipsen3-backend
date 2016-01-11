package service;

import dao.OrderDao;
import model.Order;

import java.util.Set;

/**
 * Edited by:
 * - Anton
 * - Roger
 */
public class OrderService extends BaseService<Order> {
  private final OrderDao orderDao;
  private final OrderRegelService orderRegelService;

  public OrderService(OrderDao orderDao, OrderRegelService orderRegelService) {
    this.orderDao = orderDao;
    this.orderRegelService = orderRegelService;
  }

  public Set<Order> retrieveEmptyOrders() {
    return orderDao.retrieveAll();
  }

  public Set<Order> retrieveOrdersWithOrderRegels() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      orderRegelService.retrieveEmptyOrderRegelsForOrderID(order.getOrderID());
    }
    return orders;
  }

  public Set<Order> retrieveOrdersWithOrderRegelsWithWijn() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      orderRegelService.retrieveOrderRegelsWithWijn(order);
    }
    return orders;
  }

  public Order retrieveEmptyOrder(int id) {
    return requireResult(orderDao.retrieve(id));
  }

  public Order retrieveOrderWithOrderRegels(int id) {
    Order order = this.retrieveEmptyOrder(id);
    order.setOrderRegelSet(orderRegelService.retrieveEmptyOrderRegelsForOrderID(order.getOrderID()));
    return order;
  }

  public Order retrieveOrderWithOrderRegelsWithWijn(int id) {
    Order order = this.retrieveEmptyOrder(id);
    order.setOrderRegelSet(orderRegelService.retrieveEmptyOrderRegelsWithWijnForOrderID(order));
    return order;
  }

  public Order add(Order order) {
    int newOrderID = orderDao.add(order);
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
    orderDao.update(existingOrder);
  }
}
