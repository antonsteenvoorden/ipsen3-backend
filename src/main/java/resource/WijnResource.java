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
import java.util.Collection;
import java.util.Set;

/**
 * Edited by:
 * - Roger
 * - Anton
 * Resource die alle api calls afhandelt voor de wijnen.
 * Ondersteuning voor alleen ophalen, aanpassen en toevoegen gaat via IPSEN2
 */
@Api("Wijnen")
@Path("/wijnen")
@Produces(MediaType.APPLICATION_JSON)
public class WijnResource {

  private final WijnService wijnService;

  public WijnResource(WijnService wijnService) {
    this.wijnService = wijnService;
  }

  /**
   * GET request op /wijnen
   * Haalt alle wijnen op
   * @return Collection<Wijn>
   */
  @GET
  @ApiOperation("Get all wijnen")
  @JsonView(_View.View.Public.class)
  public Collection<Wijn> retrieveAll() {
    Collection<Wijn> wijnSet = wijnService.retrieveAll();
    return wijnSet;
  }

  /**
   * GET request op /wijnen/actief
   * Haalt alleen de actieve wijnen op
   * @return Collection<Wijn>
   */
  @GET
  @Path("/actief")
  @ApiOperation("Get actieve wijnen")
  @JsonView(_View.View.Public.class)
  public Collection<Wijn> retreiveActive() {
    Collection<Wijn> wijnSet = wijnService.retreiveActive();
    return wijnSet;
  }

  /**
   * Get request op een /wijnen/{id}
   * waar id een wijn_serie_id is, haalt een enkele wijn op aan de hand van het meegegeven id
   * @param id
   * @return Wijn
   */
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
