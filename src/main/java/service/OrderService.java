package service;

import dao.OrderDAO;
import model.Order;

import java.util.Set;

/**
 * Created by Anton on 10/01/2016.
 */
public class OrderService extends BaseService<Order> {
  private final OrderDAO dao;

  public OrderService(OrderDAO orderDAO) {
    this.dao = orderDAO;
  }

  public Set<Order> retrieveAll() {
    return dao.retrieveAll();
  }

  public Order retrieve(int id) {
    return requireResult(dao.retrieve(id));
  }

  public void add(Order order) {
    dao.add(order);
  }
}
