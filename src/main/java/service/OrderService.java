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

  public Set<Order> retrieveEmptyOrders() {
    return orderDAO.retrieveAll();
  }

  public Set<Order> retrieveOrdersWithOrderRegels() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      order.setOrderRegelSet(orderRegelService.retrieveEmptyOrderRegels(order.getOrderID()));
    }
    return orders;
  }

  public Set<Order> retrieveOrdersWithOrderRegelsWithWijn() {
    Set<Order> orders = retrieveEmptyOrders();
    for (Order order : orders) {
      order.setOrderRegelSet(orderRegelService.retrieveOrderRegelsWithWijn(order.getOrderID()));
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
    if (order.getOrderRegelSet() != null) {
      for(OrderRegel orderRegel : order.getOrderRegelSet()) {
        orderRegel.setOrderRegelID(orderRegelService.addOrderRegel(orderRegel));
      }
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
