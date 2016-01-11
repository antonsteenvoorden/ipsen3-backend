package resource;

import exception.ResponseException;
import model.Order;
import service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Created by Anton on 10/01/2016.
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    public Set<Order> retrieveAll() {
        Set<Order> orderSet = orderService.retrieveAll();
        return orderSet;
    }

    @GET
    @Path("/{id}")
    public Order retrieve(@PathParam("id") int id) {
        Order bestaandeOrder = orderService.retrieve(id);
        if (bestaandeOrder == null) {
            ResponseException.formatAndThrow(Response.Status.NOT_FOUND, "Content with id " + id + " does not exist");
        }
        return bestaandeOrder;
    }

    @POST
    @Path("/single")
    @Consumes(MediaType.APPLICATION_JSON)
    public Order create(Order order)
    {
        return orderService.add(order);
    }
}
