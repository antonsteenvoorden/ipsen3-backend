package resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import io.dropwizard.auth.Auth;
import model.Klant;
import model.Order;
import service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Edited by:
 * - Roger
 * - Anton
 */
@Api("Orders")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
  private final OrderService orderService;

  public OrderResource(OrderService orderService) {
    this.orderService = orderService;
  }

  @GET
  @ApiOperation("Get all orders")
  public Set<Order> retrieveAll(@QueryParam("orderFill") boolean orderFill, @QueryParam("wijnFill") boolean wijnFill) {
    if (wijnFill) {
      return orderService.retrieveOrdersWithOrderRegelsWithWijn();
    } else if (orderFill) {
      return orderService.retrieveOrdersWithOrderRegels();
    } else {
      return orderService.retrieveEmptyOrders();
    }
  }

  @GET
  @ApiOperation("Get specific order")
  @Path("/{id}")
  public Order retrieve(@PathParam("id") int id, boolean orderFill, @QueryParam("wijnFill") boolean wijnFill) {
    if (wijnFill) {
      return orderService.retrieveOrderWithOrderRegelsWithWijn(id);
    } else if (orderFill) {
      return orderService.retrieveOrderWithOrderRegels(id);
    } else {
      return orderService.retrieveEmptyOrder(id);
    }
  }


  @POST
  @Path("/{id}")
  @ApiOperation("Create order")
  @Consumes(MediaType.APPLICATION_JSON)
  public Order create(@PathParam("id") int id, Order order, @Auth Klant authenticator, Klant klant) {
    return orderService.add(id, order, authenticator, klant);
  }

//  //je kan maar 1 order tegelijk denk ik.
//  @POST
//  @ApiOperation("Create multiple orders")
//  @Consumes(MediaType.APPLICATION_JSON)
//  public List<Order> create(Set<Order> orders) {
//    List<Order> updatedList = new ArrayList<>();
//    for (Order order : orders) {
//      updatedList.add(orderService.add(order));
//    }
//    return updatedList;
//  }

  @PUT
  @ApiOperation("Update order")
  @Path("/single")
  @Consumes(MediaType.APPLICATION_JSON)
  public void update(Order order) {
    orderService.update(order);
  }

  @PUT
  @ApiOperation("Update multiple orders")
  @Consumes(MediaType.APPLICATION_JSON)
  public void update(Set<Order> orders) {
    for (Order order : orders) {
      orderService.update(order);
    }
  }
}
