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
 * Created by Anton on 20/01/2016.
 */
@Api("Actie")
@Path("/actie")
@Produces(MediaType.APPLICATION_JSON)
public class ActieResource {
  private final ActieService service;

  public ActieResource(ActieService service)
  {
    this.service = service;
  }

  @GET
  @ApiOperation("Get all active acties")
  @Path("/actief")
  @JsonView(_View.View.Public.class)
  @RolesAllowed("GUEST")
  public Collection<Actie> retreiveActive() {
    return service.getActive();
  }

  @GET
  @Path("/{id}")
  @ApiOperation("Get actie by id")
  @JsonView(_View.View.Public.class)
  @RolesAllowed("GUEST")
  public Actie retreive(@PathParam("id") int id) {
    return service.get(id);
  }


  @GET
  @JsonView(_View.View.Public.class)
  @RolesAllowed("GUEST")
  @ApiOperation("Get all acties")
  public Collection<Actie> retrieveAll() {
    return service.getAll();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @JsonView(_View.View.Public.class)
  @RolesAllowed("LID")
  @ApiOperation("Create actie")
  public void create(Actie actie) {
    service.add(actie);
  }

  @PUT
  @ApiOperation("Update actie")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("LID")
  @JsonView(_View.View.Public.class)
  public void update(@PathParam("id") int id, Actie actie) {
    service.update(actie);
  }

  @POST
  @Path("/{id}/aanmeldingen")
  @ApiOperation("Klanten aanmelden")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("GUEST")
  @JsonView(_View.View.Public.class)
  public void aanmelden(@PathParam("id") int id ,@Auth Klant authenticator, Klant klant){
    service.aanmelden(id, authenticator, klant);
  }

  @GET
  @Path("/{id}/aanmeldingen")
  @ApiOperation("Get all inschrijvingen")
  @RolesAllowed("LID")
  @JsonView(_View.View.Public.class)
  public Collection<Inschrijving> getAll(@PathParam("id")int id) {
    return service.getInschrijvingen(id);
  }
}
