package resource;

import com.fasterxml.jackson.annotation.JsonView;
import exception.ResponseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Wijn;
import service.WijnService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Edited by:
 * - Roger
 * - Anton
 */
@Api("Wijnen")
@Path("/wijnen")
@Produces(MediaType.APPLICATION_JSON)
public class WijnResource {

  private final WijnService wijnService;

  public WijnResource(WijnService wijnService) {
    this.wijnService = wijnService;
  }

  @GET
  @RolesAllowed("GUEST")
  public Set<Wijn> retrieveAll() {
    Set<Wijn> wijnSet = wijnService.retrieveAll();
    return wijnSet;
  }

  @GET
  @Path("/{id}")
  @RolesAllowed("GUEST")
  @JsonView(_View.View.Public.class)
  public Wijn retrieve(@PathParam("id") int id) {
    Wijn bestaandeWijn = wijnService.retrieve(id);
    if (bestaandeWijn == null) {
      ResponseException.formatAndThrow(Response.Status.NOT_FOUND, "Content with id " + id + " does not exist");
    }
    return bestaandeWijn;
  }
}
