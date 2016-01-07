package petersinspiratiepakket.actorius.resource;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import petersinspiratiepakket.actorius.View;
import petersinspiratiepakket.actorius.model.User;
import petersinspiratiepakket.actorius.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Meer informatie over resources:
 *  https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources
 * 
 * @author Peter van Vliet
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource
{
    private final UserService service;
    
    public UserResource(UserService service)
    {
        this.service = service;
    }
    
    @GET
    @JsonView(View.Public.class)
    @RolesAllowed("GUEST")
    public Collection<User> retrieveAll()
    {
        return service.getAll();
    }
    
    @GET
    @Path("/{id}")
    @JsonView(View.Public.class)
    @RolesAllowed("GUEST")
    public User retrieve(@PathParam("id") int id)
    {
        return service.get(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    public void create(User user)
    {
        service.add(user);
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(View.Protected.class)
    @RolesAllowed("GUEST")
    public void update(@PathParam("id") int id, @Auth User authenticator, User user)
    {
        service.update(authenticator, id, user);
    }
    
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("id") int id)
    {
        service.delete(id);
    }
    
    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User authenticate(@Auth User authenticator)
    {
        return authenticator;
    }
}
