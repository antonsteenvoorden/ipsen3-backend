package resource;

import exception.ResponseException;
import io.swagger.annotations.Api;
import model.Wijn;
import service.WijnService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Edited by:
 * - Roger
 * - Anton
 */
@Api("/wijnen")
@Path("/wijnen")
@Produces(MediaType.APPLICATION_JSON)
public class WijnResource {

  private final WijnService wijnService;

  public WijnResource(WijnService wijnService) {
    this.wijnService = wijnService;
  }

  @GET
  public Set<Wijn> retrieveAll() {
    Set<Wijn> wijnSet = wijnService.retrieveAll();
    return wijnSet;
  }

  @GET
  @Path("/{id}")
  public Wijn retrieve(@PathParam("id") int id) {
    Wijn bestaandeWijn = wijnService.retrieve(id);
    if (bestaandeWijn == null) {
      ResponseException.formatAndThrow(Response.Status.NOT_FOUND, "Content with id " + id + " does not exist");
    }
    return bestaandeWijn;
  }
}
