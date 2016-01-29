package service;

import dao.OrderRegelDAO;
import exception.ResponseException;
import model.OrderRegel;

import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Created by roger on 11-1-2016.
 */
public class OrderRegelService extends BaseService<OrderRegel> {
  private final OrderRegelDAO orderRegelDAO;
  private final WijnService wijnService;

  public OrderRegelService(OrderRegelDAO orderRegelDAO, WijnService wijnService) {
    this.orderRegelDAO = orderRegelDAO;
    this.wijnService = wijnService;
  }

  public Set<OrderRegel> retrieveAllEmptyOrderRegels() {
    return orderRegelDAO.retrieveAll();
  }

  public Set<OrderRegel> retrieveAllOrderRegelsWithWijn() {
    Set<OrderRegel> orderRegels = this.retrieveAllEmptyOrderRegels();
    for (OrderRegel orderRegel : orderRegels) {
      orderRegel.setWijn(wijnService.retrieve(orderRegel.getWijnID()));
    }
    return orderRegels;
  }

  public Set<OrderRegel> retrieveEmptyOrderRegels(int orderID) {
    System.out.println("Calling retrieve with orderID: " + orderID);
    Set<OrderRegel> verwijderDit =  orderRegelDAO.retrieve(orderID);
    for (OrderRegel orderRegel : verwijderDit) {
      System.out.println("orderregel found with id: " + orderRegel.getOrderRegelID());
    }
    return verwijderDit;
  }

  public Set<OrderRegel> retrieveOrderRegelsWithWijn(int orderID) {
    Set<OrderRegel> orderRegels = this.retrieveEmptyOrderRegels(orderID);
    for (OrderRegel orderRegel : orderRegels) {
      orderRegel.setWijn(wijnService.retrieve(orderRegel.getWijnID()));
    }
    return orderRegels;
  }

  public OrderRegel retrieveEmptyOrderRegel(int orderRegelID) {
    Set<OrderRegel> orderRegels = orderRegelDAO.retrieve(orderRegelID);
    if (orderRegels.size() > 1) {
      ResponseException.formatAndThrow(Response.Status.NOT_FOUND, "Query for orderRegel with id " + orderRegelID + " returned more than one result");
    }
    return orderRegels.iterator().next();
  }

  public OrderRegel retrieveOrderRegelWithWijn(int orderRegelID) {
    OrderRegel orderRegel = retrieveEmptyOrderRegel(orderRegelID);
    orderRegel.setWijn(wijnService.retrieve(orderRegel.getWijnID()));
    return orderRegel;
  }

  public int addOrderRegel(OrderRegel orderRegel) {
    return orderRegelDAO.add(orderRegel);
  }
}
