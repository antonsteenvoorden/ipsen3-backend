package resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import model.Mail;
import service.LionsService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Edited by:
 * - Anton
 * <p/>
 * Hierin staan een aantal functies van de lions
 * Tot nu toe hebben we alleen het verzenden van de nieuwsbrief
 */
@Api("Lions")
@Path("/lions")
@Produces(MediaType.APPLICATION_JSON)
public class LionsResource {
  private final LionsService lionsService;

  public LionsResource(LionsService lionsService) {
    this.lionsService = lionsService;
  }

  /**
   * Maakt en verstuurd een nieuwsbrief naar alle klanten die wantsmail op true hebben
   * Ontvangt een mail met een onderwerp en tekst
   *
   * @param mail
   * @return
   */
  @POST
  @ApiOperation("Verstuur de mail")
  @Path("/nieuwsbrief")
  @RolesAllowed({"LID", "MS", "ADMIN"})
  @Consumes(MediaType.APPLICATION_JSON)
  public Mail create(Mail mail) {
    return lionsService.send(mail);
  }

  @POST
  @ApiOperation("Verstuur het contactformulier")
  @Path("/contact")
  @RolesAllowed({"GUEST","LID","MS","ADMIN"})
  @Consumes(MediaType.APPLICATION_JSON)
  public Mail createContactFormulier(Mail mail) {
    return lionsService.sendContactFormulier(mail);
  }


}
