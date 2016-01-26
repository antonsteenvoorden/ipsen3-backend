package resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.ApiOperation;
import io.dropwizard.auth.Auth;
import com.wordnik.swagger.annotations.Api;
import model.Klant;
import service.KlantService;

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
    @ApiOperation("Get all klanten")
    @JsonView(_View.View.Public.class)
    @RolesAllowed("GUEST")
    public Collection<Klant> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{email}")
    @ApiOperation("Get klant by email")
    @JsonView(_View.View.Public.class)
    @RolesAllowed("GUEST")
    public Klant retrieve(@PathParam("email") String email, @Auth Klant authenticator) {
        return service.get(email, authenticator);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(_View.View.Protected.class)
    @ApiOperation("Create klant")
    @RolesAllowed("LID")
    public void create(Klant klant) {
        System.out.println("KlantResource.create" + klant.toString());
        service.add(klant);
    }

    @PUT
    @ApiOperation("Update klant")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(_View.View.Protected.class)
    @RolesAllowed("GUEST")
    public void update(@Auth Klant authenticator, Klant klant) {
        service.update(authenticator, klant);
    }

    @PUT
    @Path("/wachtwoord")
    @ApiOperation("Update wachtwoord")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(_View.View.Protected.class)
    @RolesAllowed("GUEST")
    public void updateWachtwoord(@Auth Klant authenticator, Klant klant) {
        service.updateWachtwoord(authenticator, klant);
    }

//    @DELETE
//    @Path("/{email}")
//    @RolesAllowed("ADMIN")
//    public void delete(@PathParam("email") String email) {
//        service.delete(email);
//    }

}
