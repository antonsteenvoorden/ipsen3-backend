package resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
  @ApiOperation("KaasDude")
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
  @ApiOperation("KaasDude")
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
  @ApiOperation("KaasDude")
  @Path("/single")
  @Consumes(MediaType.APPLICATION_JSON)
  public Order create(Order order) {
    return orderService.add(order);
  }

  @POST
  @ApiOperation("KaasDude")
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Order> create(Set<Order> orders) {
    List<Order> updatedList = new ArrayList<>();
    for (Order order : orders) {
      updatedList.add(orderService.add(order));
    }
    return updatedList;
  }

  @PUT
  @ApiOperation("KaasDude")
  @Path("/single")
  @Consumes(MediaType.APPLICATION_JSON)
  public void update(Order order) {
    orderService.update(order);
  }

  @PUT
  @ApiOperation("KaasDude")
  @Consumes(MediaType.APPLICATION_JSON)
  public void update(Set<Order> orders) {
    for (Order order : orders) {
      orderService.update(order);
    }
  }
}
