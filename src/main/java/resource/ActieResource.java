package resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.ApiOperation;
import io.dropwizard.auth.Auth;
import com.wordnik.swagger.annotations.Api;
import model.Actie;
import model.Inschrijving;
import model.Klant;
import service.ActieService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Edited by:
 * - Anton
 * <p/>
 * Vangt alle requests af op het basis pad + /actie
 * Ontvangt en produceert JSON strings
 * Comments en code geschreven door Anton
 */
@Api("Actie")
@Path("/actie")
@Produces(MediaType.APPLICATION_JSON)
public class ActieResource {
  private final ActieService service;

  public ActieResource(ActieService service) {
    this.service = service;
  }

  /**
   * GET request op /actie/actief
   * Toegestaan voor GASTEN of hoger
   * Haalt alle actieve acties op
   * Produceert een collectie van Actie's
   *
   * @return Collectie<Actie>
   */
  @GET
  @ApiOperation("Get all active acties")
  @Path("/actief")
  @JsonView(_View.View.Public.class)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  public Collection<Actie> retreiveActive() {
    return service.getActive();
  }

  /**
   * GET Request op /actie/{id}
   * Haalt alleen een enkele opgevraagde actie op aan de hand van het actie nummer
   * Toegestaan voor gasten of hoger
   * Produceert een enkele Actie
   *
   * @param id
   * @return Actie
   */
  @GET
  @Path("/{id}")
  @ApiOperation("Get actie by id")
  @JsonView(_View.View.Public.class)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  public Actie retreive(@PathParam("id") int id) {
    return service.get(id);
  }

  /**
   * GET request op /actie
   * haalt ALLE acties op uit de database
   * Toegestaan voor gasten of hoger
   * Produceert een collectie van Actie's
   *
   * @return Collection<Actie>
   */
  @GET
  @JsonView(_View.View.Public.class)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  @ApiOperation("Get all acties")
  public Collection<Actie> retrieveAll() {
    return service.getAll();
  }

  /**
   * POST request op /actie
   * Ontvangt de nieuwe actie
   * maakt een nieuwe actie aan.
   * Toegestaan voor LID of hoger
   *
   * @param actie
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @JsonView(_View.View.Public.class)
  @RolesAllowed({"LID", "MS", "ADMIN"})
  @ApiOperation("Create actie")
  public void create(Actie actie) {
    service.add(actie);
  }

  /**
   * PUT request op /actie
   * Ontvangt een actie met nieuwe waarden
   * wijzigt de actie
   * Toegestaan voor LID of hoger
   *
   * @param actie
   */
  @PUT
  @ApiOperation("Update actie")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed({"LID", "MS", "ADMIN"})
  @JsonView(_View.View.Public.class)
  public void update(@PathParam("id") int id, Actie actie) {
    service.update(actie);
  }

  /**
   * POST op /actie/{id}/aanmeldingen
   * Meld een nieuwe klant / schrijft een klant in voor de gespecificeerde actie
   * Ontvangt een actie nummer (id) de authenticatie credentials en de desbetreffende klant
   * Toegestaan voor GASTEN of hoger
   *
   * @param id
   * @param authenticator
   * @param klant
   */
  @POST
  @Path("/{id}/aanmeldingen")
  @ApiOperation("Klanten aanmelden")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed({"GUEST", "LID", "MS", "ADMIN"})
  @JsonView(_View.View.Public.class)
  public void aanmelden(@PathParam("id") int id, @Auth Klant authenticator, Klant klant) {
    service.aanmelden(id, authenticator, klant);
  }

  /**
   * GET request op /actie/{id}/aanmeldingen
   * Haalt alle aanmeldingen op voor de gespecificeerde actie
   * Ontvangt een actie nummer(id)
   *
   * @param id
   * @return
   */
  @GET
  @Path("/{id}/aanmeldingen")
  @ApiOperation("Get all inschrijvingen")
  @RolesAllowed({"LID", "MS", "ADMIN"})
  @JsonView(_View.View.Public.class)
  public Collection<Inschrijving> getAll(@PathParam("id") int id) {
    return service.getInschrijvingen(id);
  }
}
