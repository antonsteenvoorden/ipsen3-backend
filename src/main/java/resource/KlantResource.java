package resource;

import io.swagger.annotations.Api;
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
@Api("Klanten")
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
    public Klant retrieve(@PathParam("email") String email, @Auth Klant authenticator) {
        return service.get(email, authenticator);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(_View.View.Protected.class)
    @RolesAllowed("LID")
    public void create(Klant klant) {
        System.out.println("KlantResource.create" + klant.toString());
        service.add(klant);
    }

    @PUT
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(_View.View.Protected.class)
    @RolesAllowed("GUEST")
    public void update(@PathParam("email") String email, @Auth Klant authenticator, Klant klant) {
        service.update(email, authenticator, klant);
    }

//    @DELETE
//    @Path("/{email}")
//    @RolesAllowed("ADMIN")
//    public void delete(@PathParam("email") String email) {
//        service.delete(email);
//    }

}
