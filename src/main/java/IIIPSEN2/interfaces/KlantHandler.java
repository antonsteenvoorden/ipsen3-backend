package IIIPSEN2.interfaces;

import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;

import java.util.ArrayList;

/**
 * @author Roger Bosman
 */

public interface KlantHandler {
    ArrayList<Order> getOrderByKlant(Klant klant);
}
