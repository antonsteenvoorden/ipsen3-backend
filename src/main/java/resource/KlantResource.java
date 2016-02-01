package resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import io.dropwizard.auth.Auth;
import model.Klant;
import model.Order;
import service.KlantService;
import service.LionsService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Edited by:
 * - Anton
 * - Roger
 * <p/>
 * Resource die alle requests afhandelt op /api/klanten
 * Heeft een get request voor alle klanten, voor een enkele klant
 * Heeft een post request om een nieuwe klant aan te maken
 * een put request om een klant te wijzigen
 * een put reqeust om het wachtwoord te wijzigen
 * een post update om een wachtwoord te resetten
 * en get request om alle orders voor een klant te laten zien
 */
@Api("Klanten")
@Path("/klanten")
@Produces(MediaType.APPLICATION_JSON)
public class KlantResource {
  private final KlantService service;
  private final LionsService lionsService;

  public KlantResource(KlantService service, LionsService lionsService) {
    this.service = service;
    this.lionsService = lionsService;

  }

  /**
   * GET request op /klanten
   * haalt alle klanten op
   *
   * @return Collection<Klant>
   */
  @GET
  @ApiOperation("Get all klanten")
  @JsonView(_View.View.Public.class)
  @RolesAllowed({"LID", "MS", "ADMIN"})
  public Collection<Klant> retrieveAll() {
    return service.getAll();
  }

  /**
   * GET request op /klanten/{email}
   * Haalt een enkele klant op adhv de opgegeven email
   * Stuurt ook een authenticator mee om te controleren dat de gebruiker zichzelf opvraagt,
   * of dat het een admin is
   *
   * @param email
   * @param authenticator
   * @return Klant
   */
  @GET
  @Path("/{email}")
  @ApiOperation("Get klant by email")
  @JsonView(_View.View.Public.class)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  public Klant retrieve(@PathParam("email") String email, @Auth Klant authenticator) {
    return service.get(email, authenticator);
  }

  /**
   * POST request op /klanten
   * Maakt een nieuwe klant aan met de opgeleverde gegevens
   *
   * @param klant
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @JsonView(_View.View.Protected.class)
  @ApiOperation("Create klant")
  public void create(Klant klant) {
    System.out.println("KlantResource.create" + klant.toString());
    service.add(klant);
  }

  /**
   * PUT Request, ontvangt de authenticator,
   * de gegevens van de nieuwe klant en update deze in de klant service
   *
   * @param authenticator
   * @param klant
   */
  @PUT
  @ApiOperation("Update klant")
  @Consumes(MediaType.APPLICATION_JSON)
  @JsonView(_View.View.Protected.class)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  public void update(@Auth Klant authenticator, Klant klant) {
    service.update(authenticator, klant);
  }

  /**
   * PUT request op /klanten/wachtwoord om het wachtwoord te veranderen, ontvangt de authenticator,
   * email en het wachtwoord en roept de update methode aan in de klantservice
   *
   * @param klant
   */
  @PUT
  @Path("/wachtwoord")
  @ApiOperation("Update wachtwoord")
  @Consumes(MediaType.APPLICATION_JSON)
  @JsonView(_View.View.Protected.class)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  public void updateWachtwoord(@Auth Klant authenticator, Klant klant) {
    service.updateWachtwoord(authenticator, klant);
  }

  /**
   * GET request op /klanten/{email}/orders
   * haalt alle orders op voor de meegegeven email/klant
   * Ontvangt de authenticator, email adres, of de orders moeten worden weergeven
   * en of de wijnen in de orders moeten worden weergeven
   *
   * @param email
   * @param orderFill
   * @param wijnFill
   * @return
   */
  @GET
  @Path("/{email}/orders")
  @ApiOperation("Get all orders of klant")
  @JsonView(_View.View.Protected.class)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  public ArrayList<Order> getOrders(@PathParam("email") String email,
                                    @QueryParam("orderFill") boolean orderFill, @QueryParam("wijnFill") boolean wijnFill, @Auth Klant authenticator) {
    return service.getOrdersByKlant(email, orderFill, wijnFill, authenticator);
  }

  /**
   * POST reqeust op /klanten/wachtwoordvergeten
   * Ontvangt een email adres en stuurt deze door naar lionservice, daar wordt een mail verzonden
   *
   * @param email
   */
  @POST
  @ApiOperation("Nieuw wachtwoord versturen naar gebruiker")
  @Path("/wachtwoordvergeten")
  @JsonView(_View.View.Protected.class)
  public void wachtwoordVergeten(String email) {
    System.out.println("KlantResource.wachtwoordVergeten : " + email);
    lionsService.wachtwoordVergeten(email);
  }


  //    @DELETE
  //    @Path("/{email}")
  //    @RolesAllowed("ADMIN")
  //    public void delete(@PathParam("email") String email) {
  //        service.delete(email);
  //    }

}
