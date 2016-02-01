package service;

import dao.OrderRegelDAO;
import exception.ResponseException;
import model.OrderRegel;

import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Edited by:
 * - Roger
 */
public class OrderRegelService extends BaseService<OrderRegel> {
  private final OrderRegelDAO orderRegelDAO;
  private final WijnService wijnService;

  public OrderRegelService(OrderRegelDAO orderRegelDAO, WijnService wijnService) {
    this.orderRegelDAO = orderRegelDAO;
    this.wijnService = wijnService;
  }

  /**
   * Haalt alle orderregels op. De opgehaalde orderregels zijn leeg (bevatten geen wijn)
   * @return
   */
  public Set<OrderRegel> retrieveAllEmptyOrderRegels() {
    return orderRegelDAO.retrieveAll();
  }

  /**
   * Haalt eerst alle lege orderregels op door retrieveAllEmptyOrderRegels aan te roepen.
   * Vervolgens word iedere order gevult met een wijn
   * @return
   */
  public Set<OrderRegel> retrieveAllOrderRegelsWithWijn() {
    Set<OrderRegel> orderRegels = this.retrieveAllEmptyOrderRegels();
    for (OrderRegel orderRegel : orderRegels) {
      orderRegel.setWijn(wijnService.retrieve(orderRegel.getWijnID()));
    }
    return orderRegels;
  }

  /**
   * Haalt een set orderregels op behorende bij een order op op basis van een orderID.
   * De opgehaalde orderregels zijn leeg (bevatten geen wijn)
   * @param orderID
   * @return
   */
  public Set<OrderRegel> retrieveEmptyOrderRegels(int orderID) {
    System.out.println("Calling retrieve with orderID: " + orderID);
    Set<OrderRegel> verwijderDit =  orderRegelDAO.retrieve(orderID);
    for (OrderRegel orderRegel : verwijderDit) {
      System.out.println("orderregel found with id: " + orderRegel.getOrderRegelID());
    }
    return verwijderDit;
  }

  /**
   * Haalt een set orderregels op behorende bij een order op op basis van een orderID.
   * De opgehaalde orderregels bevat een wijn.
   * @param orderID
   * @return
   */
  public Set<OrderRegel> retrieveOrderRegelsWithWijn(int orderID) {
    Set<OrderRegel> orderRegels = this.retrieveEmptyOrderRegels(orderID);
    for (OrderRegel orderRegel : orderRegels) {
      orderRegel.setWijn(wijnService.retrieve(orderRegel.getWijnID()));
    }
    return orderRegels;
  }

  /**
   * Haalt een orderregel op op op basis van een orderRegelID.
   * De opgehaalde orderregel is leeg (bevat geen wijn)
   * @param orderRegelID
   * @return
   */
  public OrderRegel retrieveEmptyOrderRegel(int orderRegelID) {
    Set<OrderRegel> orderRegels = orderRegelDAO.retrieve(orderRegelID);
    if (orderRegels.size() > 1) {
      ResponseException.formatAndThrow(Response.Status.NOT_FOUND, "Query for orderRegel with id " + orderRegelID + " returned more than one result");
    }
    return orderRegels.iterator().next();
  }

  /**
   * Haalt een orderregel op op op basis van een orderRegelID.
   * De opgehaalde orderregel is gevuld (bevat een wijn)
   * @param orderRegelID
   * @return
   */
  public OrderRegel retrieveOrderRegelWithWijn(int orderRegelID) {
    OrderRegel orderRegel = retrieveEmptyOrderRegel(orderRegelID);
    orderRegel.setWijn(wijnService.retrieve(orderRegel.getWijnID()));
    return orderRegel;
  }

  /**
   * voegt een orderregel toe en returned de gegenereerde orderRegelID key.
   * @param orderRegel
   * @return
   */
  public int addOrderRegel(OrderRegel orderRegel) {
    return orderRegelDAO.add(orderRegel);
  }
}
