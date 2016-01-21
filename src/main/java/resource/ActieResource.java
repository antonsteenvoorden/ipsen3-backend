package resource;

import _View.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import model.Actie;
import model.Inschrijving;
import model.Klant;
import service.ActieService;
import service.KlantService;

import javax.annotation.security.RolesAllowed;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Anton on 20/01/2016.
 */
@Path("/actie")
@Produces(MediaType.APPLICATION_JSON)
public class ActieResource {
  private final ActieService service;

  public ActieResource(ActieService service)
  {
    this.service = service;
  }

  @GET
  @Path("/actief")
  @JsonView(_View.View.Public.class)
  @RolesAllowed("GUEST")
  public Collection<Actie> retreiveActive() {
    return service.getActive();
  }

  @GET
  @Path("/{id}")
  @JsonView(_View.View.Public.class)
  @RolesAllowed("GUEST")
  public Actie retreive(@PathParam("id") int id) {
    return service.get(id);
  }


  @GET
  @JsonView(_View.View.Public.class)
  @RolesAllowed("GUEST")
  public Collection<Actie> retrieveAll() {
    return service.getAll();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @JsonView(_View.View.Public.class)
  @RolesAllowed("LID")
  public void create(Actie actie) {
    service.add(actie);
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("LID")
  @JsonView(_View.View.Public.class)
  public void update(@PathParam("id") int id, Actie actie) {
    service.update(actie);
  }

  @POST
  @Path("/{id}/aanmeldingen")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("GUEST")
  @JsonView(_View.View.Public.class)
  public void aanmelden(@PathParam("id") int id ,@Auth Klant authenticator, Klant klant){
    service.aanmelden(id, authenticator, klant);
  }

  @GET
  @Path("/{id}/aanmeldingen")
  @RolesAllowed("LID")
  @JsonView(_View.View.Public.class)
  public Collection<Inschrijving> getAll(@PathParam("id")int id) {
    return service.getInschrijvingen(id);
  }
}
