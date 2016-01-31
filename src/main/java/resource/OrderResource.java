package resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import io.dropwizard.auth.Auth;
import model.Klant;
import model.Order;
import service.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * Edited by:
 * - Roger
 * - Anton
 * Resource die alle requests afhandelt op /api/orders
 */
@Api("Orders")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
  private final OrderService orderService;

  public OrderResource(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Get functie die alle orders returned.
   * Als wijnFill word meegegeven en op true staat dan worden de orders gevuld met orderregels en die orderregels worden gevuld met wijnen.
   * Als wijnFill niet meegegeven word of false is word gekeken naar orderFill.
   * Als orderFill megegeven en true is worden de orders gevuld met orderregels, maar worden die orderregels niet met wijnen gevuld.
   * Mochten zowel orderFill als wijnFill niet meegegeven worden of false zijn worden alleen de lege orders.
   * @param orderFill
   * @param wijnFill
   * @return
   */
  @GET
  @RolesAllowed({"MS","ADMIN"})
  @ApiOperation("Get all orders")
  public Set<Order> retrieveAll(@QueryParam("orderFill") boolean orderFill, @QueryParam("wijnFill") boolean wijnFill) {
    System.out.println("Oderfill = " + orderFill + ", wijnFill = " + wijnFill);
    if (wijnFill) {
      return orderService.retrieveOrdersWithOrderRegelsWithWijn();
    } else if (orderFill) {
      return orderService.retrieveOrdersWithOrderRegels();
    } else {
      return orderService.retrieveEmptyOrders();
    }
  }

  /**
   * Get functie die alle een order returned op basis van een speciefiek id (orderID)
   * Als wijnFill word meegegeven en op true staat dan worden de orders gevuld met orderregels en die orderregels worden gevuld met wijnen.
   * Als wijnFill niet meegegeven word of false is word gekeken naar orderFill.
   * Als orderFill megegeven en true is worden de orders gevuld met orderregels, maar worden die orderregels niet met wijnen gevuld.
   * Mochten zowel orderFill als wijnFill niet meegegeven worden of false zijn worden alleen de lege orders.
   * @param orderFill
   * @param wijnFill
   * @return
   */
  @GET
  @ApiOperation("Get specific order")
  @Path("/{id}")
  public Order retrieve(@PathParam("id") int id, @QueryParam("orderFill") boolean orderFill, @QueryParam("wijnFill") boolean wijnFill, @Auth Klant authenticator) {
    if (wijnFill) {
      return orderService.retrieveOrderWithOrderRegelsWithWijn(id, authenticator);
    } else if (orderFill) {
      return orderService.retrieveOrderWithOrderRegels(id, authenticator);
    } else {
      return orderService.retrieveEmptyOrder(id, authenticator);
    }
  }

  /**
   * Maakt een order aan
   * @param order
   * @return
   */
  @POST
  @ApiOperation("Create order")
  @Consumes(MediaType.APPLICATION_JSON)
  public Order create(Order order, @Auth Klant authenticator) {
    return orderService.add(order, authenticator);
  }

  /**
   * updated een order
   * @param order
   */
  @PUT
  @ApiOperation("Update order")
  @Consumes(MediaType.APPLICATION_JSON)
  public void update(Order order, @Auth Klant authenticator) {
    orderService.update(order, authenticator);
  }
}
