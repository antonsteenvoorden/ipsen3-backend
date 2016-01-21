package resource;

import io.swagger.annotations.Api;
import model.Nieuwsbrief;
import model.Order;
import service.LionsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Anton on 13/01/2016.
 */
@Api("/Lions")
@Path("/lions")
@Produces(MediaType.APPLICATION_JSON)
public class LionsResource {
  private final LionsService lionsService;

  public LionsResource(LionsService lionsService) {
    this.lionsService = lionsService;
  }

  @GET
  @Path("/verstuurnieuwsbrief")
  @Consumes(MediaType.APPLICATION_JSON)
  public void create(Nieuwsbrief nieuwsbrief) {
    lionsService.send(nieuwsbrief);
  }

}
