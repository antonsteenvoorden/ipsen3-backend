//package resource;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import io.dropwizard.auth.Auth;
//import model.Klant;
//import petersinspiratiepakket.actorius.View.View;
//import petersinspiratiepakket.actorius.model.User;
//import petersinspiratiepakket.actorius.service.UserService;
//
//import javax.annotation.security.RolesAllowed;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import java.util.Collection;
//
///**
// * Meer informatie over resources:
// *  https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources
// *
// * @author Peter van Vliet
// */
//@Path("/klanten")
//@Produces(MediaType.APPLICATION_JSON)
//public class KlantResource {
//    private final UserService service;
//
//    public KlantResource(UserService service)
//    {
//        this.service = service;
//    }
//
//    @GET
//    @JsonView(View.View.Public.class)
//    @RolesAllowed("GUEST")
//    public Collection<User> retrieveAll() {
//        return service.getAll();
//    }
//
//    @GET
//    @Path("/{email}")
//    @JsonView(View.View.Public.class)
//    @RolesAllowed("GUEST")
//    public User retrieve(@PathParam("email") String email) {
//        return service.get(email);
//    }
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @JsonView(View.View.Protected.class)
//    public void create(User user) {
//        service.add(user);
//    }
//
//    @PUT
//    @Path("/{email}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @JsonView(View.View.Protected.class)
//    @RolesAllowed("GUEST")
//    public void update(@PathParam("email") String email, @Auth Klant authenticator, Klant klant) {
//        service.update(authenticator, email, klant);
//    }
//
//    @DELETE
//    @Path("/{email}")
//    @RolesAllowed("ADMIN")
//    public void delete(@PathParam("email") String email) {
//        service.delete(email);
//    }
//
//    @GET
//    @Path("/me")
//    @JsonView(View.View.Private.class)
//    public User authenticate(@Auth User authenticator) {
//        return authenticator;
//    }
//}
