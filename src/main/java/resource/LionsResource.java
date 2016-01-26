package resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import model.Mail;
import service.LionsService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Anton on 13/01/2016.
 */
@Api("Lions")
@Path("/lions")
@Produces(MediaType.APPLICATION_JSON)
public class LionsResource {
  private final LionsService lionsService;

  public LionsResource(LionsService lionsService) {
    this.lionsService = lionsService;
  }

  @POST
  @ApiOperation("Verstuur de mail")
  @Path("/verstuurnieuwsbrief")
  @RolesAllowed("ADMIN")
  @Consumes(MediaType.APPLICATION_JSON)
  public Mail create(Mail mail) {
    return lionsService.send(mail);
  }

}
