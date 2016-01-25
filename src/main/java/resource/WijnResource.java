package resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.ApiOperation;
import exception.ResponseException;
import com.wordnik.swagger.annotations.Api;
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
  @ApiOperation("Get all wijnen")
  @JsonView(_View.View.Public.class)
  public Set<Wijn> retrieveAll() {
    Set<Wijn> wijnSet = wijnService.retrieveAll();
    return wijnSet;
  }

  @GET
  @Path("/{id}")
  @ApiOperation("Get wijn by id")
  @JsonView(_View.View.Public.class)
  public Wijn retrieve(@PathParam("id") int id) {
    Wijn bestaandeWijn = wijnService.retrieve(id);
    if (bestaandeWijn == null) {
      ResponseException.formatAndThrow(Response.Status.NOT_FOUND, "Content with id " + id + " does not exist");
    }
    return bestaandeWijn;
  }
}
