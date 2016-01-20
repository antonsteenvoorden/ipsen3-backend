package resource;

import com.fasterxml.jackson.annotation.JsonView;
import model.Actie;
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
  public void update(Actie actie) {
    service.update(actie);
  }
}
