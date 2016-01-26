package resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import model.Order;
import service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    System.out.println("Oderfill = " + orderFill + ", wijnFill = " + wijnFill);
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
  public Order retrieve(@PathParam("id") int id, @QueryParam("orderFill") boolean orderFill, @QueryParam("wijnFill") boolean wijnFill) {
    if (wijnFill) {
      return orderService.retrieveOrderWithOrderRegelsWithWijn(id);
    } else if (orderFill) {
      return orderService.retrieveOrderWithOrderRegels(id);
    } else {
      return orderService.retrieveEmptyOrder(id);
    }
  }

  @POST
  @ApiOperation("Create order")
  @Consumes(MediaType.APPLICATION_JSON)
  public Order create(Order order) {
    return orderService.add(order);
  }

  @PUT
  @ApiOperation("Update order")
  @Consumes(MediaType.APPLICATION_JSON)
  public void update(Order order) {
    orderService.update(order);
  }
}
