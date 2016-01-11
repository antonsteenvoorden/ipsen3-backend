package service;

import dao.OrderRegelDao;
import model.Order;
import model.OrderRegel;

import java.util.Set;

/**
 * Created by roger on 11-1-2016.
 */
public class OrderRegelService {
  private final OrderRegelDao orderRegelDao;
  private final WijnService wijnService;

  public OrderRegelService(OrderRegelDao orderRegelDao, WijnService wijnService) {
    this.orderRegelDao = orderRegelDao;
    this.wijnService = wijnService;
  }

  public Set<OrderRegel> retrieveEmptyOrderRegelsForOrderID(int orderID) {
    return orderRegelDao.retrieve(orderID);
  }

  public Set<OrderRegel> retrieveEmptyOrderRegelsWithWijnForOrderID(int orderID) {
    Set<OrderRegel> orderRegels = this.retrieveEmptyOrderRegelsForOrderID(orderID);
    for (OrderRegel orderRegel : orderRegels) {
      
    }
  }

  public Order retrieveEmptyOrderRegel() {

  }

  public void retrieveOrderRegelsWithWijn(Order order) {
    //todo: filll
  }


}
