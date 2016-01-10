package resource;

import model.Wijn;
import service.WijnService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/*********************************************
 * Created by roger and KAASANTON on 10-1-2016.
 * ik ben ook een comment
 *********************************************/
@Path("/wijnen")
@Produces(MediaType.APPLICATION_JSON)
public class WijnResource {

    private final WijnService service;

    public WijnResource(WijnService service) {
        this.service = service;
    }

    @GET
    public Collection<Wijn> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Wijn retrieve(@PathParam("id") int id) {
        return service.get(id);
    }
}
