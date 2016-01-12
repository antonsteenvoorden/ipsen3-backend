package resource;

import service.KlantService;
import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import model.Klant;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Meer informatie over resources:
 *  https://jersey.java.net/documentation/latest/Klant-guide.html#jaxrs-resources
 *
 * @author Peter van Vliet
 */
@Path("/klanten")
@Produces(MediaType.APPLICATION_JSON)
public class KlantResource {
    private final KlantService service;

    public KlantResource(KlantService service)
    {
        this.service = service;
    }

    @GET
    @JsonView(_View.View.Public.class)
    @RolesAllowed("GUEST")
    public Collection<Klant> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{email}")
    @JsonView(_View.View.Public.class)
    @RolesAllowed("GUEST")
    public Klant retrieve(@PathParam("email") String email) {
        return service.get(email);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(_View.View.Protected.class)
    public void create(Klant Klant) {
        service.add(Klant);
    }

    @PUT
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(_View.View.Protected.class)
    @RolesAllowed("GUEST")
    public void update(@PathParam("email") String email, @Auth Klant authenticator, Klant klant) {
        service.update(authenticator, email, klant);
    }

    @DELETE
    @Path("/{email}")
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("email") String email) {
        service.delete(email);
    }

    @GET
    @Path("/me")
    @JsonView(_View.View.Private.class)
    public Klant authenticate(@Auth Klant authenticator) {
        return authenticator;
    }
}
