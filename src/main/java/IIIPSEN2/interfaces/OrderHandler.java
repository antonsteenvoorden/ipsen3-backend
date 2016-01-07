package IIIPSEN2.interfaces;

import IIIPSEN2.model.Order;
import IIIPSEN2.model.OrderRegel;

import java.util.ArrayList;

/**
 * @author Roger Bosman
 */

public interface OrderHandler {

    ArrayList<OrderRegel> getOrderRegelByOrder(Order order);
}
